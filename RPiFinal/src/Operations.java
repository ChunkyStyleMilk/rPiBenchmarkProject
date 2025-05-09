public class Operations {

    public static long integerAddOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            int benchy = 5 + 5;
            count++;
        }
        return count;
    }

    public static long integerMulOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            int benchy = 5 * 5;
            count++;
        }
        return count;
    }

    public static long integerDivOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            int benchy = 10 / 2;
            count++;
        }
        return count;
    }

    public static long doubleAddOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            double benchy = 5.023958 + 5.54902;
            count++;
        }
        return count;
    }

    public static long doubleMulOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            double benchy = 5.023958 * 5.54902;
            count++;
        }
        return count;
    }

    public static long doubleDivOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            double benchy = 10.0 / 2.0;
            count++;
        }
        return count;
    }

    public static long stringOpsTime(long durationMs) {
        long end = System.currentTimeMillis() + durationMs;
        long count = 0;
        while (System.currentTimeMillis() < end) {
            String benchy = "Hello".concat("World!");
            count++;
        }
        return count;
    }
}