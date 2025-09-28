package utils;


public interface Try {
    public static void run(ExceptionRunnable fn) {
        try {
            fn.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void run(ExceptionRunnable fu, Runnable finallyFn) {
        try {
            fu.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finallyFn.run();
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
