package com.rslock.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @Loggable annotation for automatic method logging
 * Usage: @Loggable(level = LogLevel.INFO, includePerformanceMetrics = true)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    LogLevel level() default LogLevel.INFO;
    boolean includePerformanceMetrics() default false;
}

/**
 * Optimized LoggerAspect with reduced overhead:
 * - Uses java.util.logging.Logger instead of System.out
 * - Lazy evaluation: only formats strings if log level is enabled
 * - String.format() instead of concatenation to reduce object creation
 * - Proper Arrays.toString() for args formatting
 * - Conditional performance metric calculation
 */
class LoggerAspect implements InvocationHandler {
    private static final Logger LOGGER = Logger.getLogger(LoggerAspect.class.getName());
    
    private final Object target;

    public LoggerAspect(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        Loggable loggable = targetMethod.getAnnotation(Loggable.class);

        // If no @Loggable annotation, just invoke the method
        if (loggable == null) {
            return targetMethod.invoke(target, args);
        }

        LogLevel logLevel = loggable.level();
        
        // Lazy evaluation: only proceed with logging if this level is enabled
        if (!logLevel.isEnabled(LOGGER)) {
            return targetMethod.invoke(target, args);
        }

        String methodName = method.getName();
        long startTime = loggable.includePerformanceMetrics() ? System.nanoTime() : 0L;

        // Log entry with args (using lazy evaluation with lambda)
        LOGGER.log(logLevel.getJavaLevel(),
                () -> String.format("Entering method '%s' with args: %s", 
                    methodName, 
                    formatArgs(args)));

        try {
            Object result = targetMethod.invoke(target, args);

            // Log exit with performance metrics if enabled
            if (loggable.includePerformanceMetrics()) {
                long durationMs = (System.nanoTime() - startTime) / 1_000_000;
                LOGGER.log(logLevel.getJavaLevel(),
                        () -> String.format("Method '%s' executed in %d ms", 
                            methodName, 
                            durationMs));
            }

            return result;

        } catch (Throwable ex) {
            Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
            LOGGER.log(logLevel.getJavaLevel(),
                    () -> String.format("Exception in method '%s': %s", 
                        methodName, 
                        cause.getMessage()));
            throw cause;
        }
    }

    /**
     * Format method arguments for cleaner log output
     * Handles null args and uses Arrays.toString for better readability
     */
    private static String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        return Arrays.toString(args);
    }

    /**
     * Create a dynamic proxy for a class with logging enabled
     * Usage: LoggerAspect.createProxy(new MyClass(), MyInterface.class)
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new LoggerAspect(target));
    }
}
