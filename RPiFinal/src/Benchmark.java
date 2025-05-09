
import java.util.List;
import java.util.function.Supplier;

public class Benchmark {
    public static void main(String[] args) throws InterruptedException {
        final long durationMs   = 5000; // run each op for 5 seconds
        final int  numThreads   = 3;
        final long samplePeriod = 500;  // ms between temp samples

        System.out.println("Starting temperature monitor...");
        TemperatureMonitor monitor = new TemperatureMonitor(samplePeriod);
        monitor.start();

        System.out.println("\n=== Single-Threaded (5s each) ===");
        runTimeTest("Int Add",  durationMs, () -> Operations.integerAddOpsTime(durationMs));
        runTimeTest("Int Mul",  durationMs, () -> Operations.integerMulOpsTime(durationMs));
        runTimeTest("Int Div",  durationMs, () -> Operations.integerDivOpsTime(durationMs));
        runTimeTest("Dbl Add",  durationMs, () -> Operations.doubleAddOpsTime(durationMs));
        runTimeTest("Dbl Mul",  durationMs, () -> Operations.doubleMulOpsTime(durationMs));
        runTimeTest("Dbl Div",  durationMs, () -> Operations.doubleDivOpsTime(durationMs));
        runTimeTest("String",   durationMs, () -> Operations.stringOpsTime(durationMs));

        System.out.println("\n=== " + numThreads + "-Threaded (5s each) ===");
        runMultiTimeTest("Int Add",  durationMs, numThreads, () -> Operations.integerAddOpsTime(durationMs));
        runMultiTimeTest("Int Mul",  durationMs, numThreads, () -> Operations.integerMulOpsTime(durationMs));
        runMultiTimeTest("Int Div",  durationMs, numThreads, () -> Operations.integerDivOpsTime(durationMs));
        runMultiTimeTest("Dbl Add",  durationMs, numThreads, () -> Operations.doubleAddOpsTime(durationMs));
        runMultiTimeTest("Dbl Mul",  durationMs, numThreads, () -> Operations.doubleMulOpsTime(durationMs));
        runMultiTimeTest("Dbl Div",  durationMs, numThreads, () -> Operations.doubleDivOpsTime(durationMs));
        runMultiTimeTest("String",   durationMs, numThreads, () -> Operations.stringOpsTime(durationMs));

        monitor.stop();
        System.out.println("\nTemperature monitoring stopped.");

        List<Double> temps = monitor.getTempHistory();
        System.out.println("CPU temps (" + temps.size() + " samples):");
        System.out.println(temps);
    }

    private static void runTimeTest(String label, long durationMs, Supplier<Long> task) {
        long count = task.get();
        double secs = durationMs / 1000.0;
        double opsPerSec = count / secs;
        System.out.printf("%-12s → %, .0f ops/sec (%,d total)%n", label, opsPerSec, count);
    }

    private static void runMultiTimeTest(String label, long durationMs, int threads, Supplier<Long> task) throws InterruptedException {
        Thread[] workers = new Thread[threads];
        final long[] counts = new long[threads];

        for (int i = 0; i < threads; i++) {
            final int idx = i;
            workers[i] = new Thread(() -> counts[idx] = task.get(), label + "-w" + (idx+1));
            workers[i].start();
        }
        for (Thread t : workers) t.join();

        long totalCount = 0;
        for (long c : counts) totalCount += c;

        double secs = durationMs / 1000.0;
        double opsPerSec = totalCount / secs;
        System.out.printf("%-12s → %, .0f ops/sec (%,d total)%n", label + "(multi)", opsPerSec, totalCount);
    }
}
