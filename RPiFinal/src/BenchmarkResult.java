public class BenchmarkResult {
    private final long integerCount;
    private final long doubleCount;
    private final long stringCount;
    private final long exponentCount;
    private final double maxCpuTemp;

    public BenchmarkResult(
            long integerCount,
            long doubleCount,
            long stringCount,
            long exponentCount,
            double maxCpuTemp
    ) {
        this.integerCount  = integerCount;
        this.doubleCount   = doubleCount;
        this.stringCount   = stringCount;
        this.exponentCount = exponentCount;
        this.maxCpuTemp    = maxCpuTemp;
    }

    public long getIntegerCount()   { return integerCount;   }
    public long getDoubleCount()    { return doubleCount;    }
    public long getStringCount()    { return stringCount;    }
    public long getExponentCount()  { return exponentCount;  }
    public double getMaxCpuTemp()   { return maxCpuTemp;     }

    @Override
    public String toString() {
        return String.format(
                "Integers: %,d   Doubles: %,d   Strings: %,d   Exponents: %,d   MaxTemp: %.2f°C",
                integerCount, doubleCount, stringCount, exponentCount, maxCpuTemp
        );
    }
}