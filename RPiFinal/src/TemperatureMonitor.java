import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class TemperatureMonitor implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final List<Double> tempHistory = Collections.synchronizedList(new ArrayList<>());
    private final long sampleIntervalMs;

    public TemperatureMonitor(long sampleIntervalMs) {
        this.sampleIntervalMs = sampleIntervalMs;
    }

    public void start() {
        running.set(true);
        new Thread(this, "TempMonitor").start();
    }

    public void stop() {
        running.set(false);
    }

    public List<Double> getTempHistory() {
        synchronized (tempHistory) {
            return new ArrayList<>(tempHistory);
        }
    }

    @Override
    public void run() {
        while (running.get()) {
            try {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", "vcgencmd measure_temp");
                Process p = pb.start();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line = br.readLine();
                    if (line != null && line.startsWith("temp=") && line.endsWith("'C")) {
                        double t = Double.parseDouble(line.substring(5, line.length() - 2));
                        tempHistory.add(t);
                    }
                }
                p.waitFor();
            } catch (Exception e) {
                // ignore
            }
            try {
                Thread.sleep(sampleIntervalMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}