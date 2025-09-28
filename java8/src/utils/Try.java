package utils;


public interface TryCatch {
    public static void run(ExceptionRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(ExceptionRunnable r, Runnable r2) {
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            r2.run();
        }
    }

    @FunctionalInterface
    public static interface ExceptionRunnable {
        /**
         * Runs this operation.
         */
        void run() throws Exception;
    }

}
