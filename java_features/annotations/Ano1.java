package annotations;

import java.lang.annotation.*;

//!  What is Annotations? (Java 5+)
/*
 * Annotations are special markers/matadata, It's give extra info. to your to the
 * compiler, frameworks, tools or even the JVM.
 *
 * =======
 * Annotations are labels or tags in code that tell the compiler or framework
 * “how to treat this code” — but they don’t change the code’s core behavior by themselves.
 * ======
 *
 * eg.
 * ```
 *  @override
 *  public String toString(){
 *      return "Helo";
 * }
 * ```
 * @override => telling the compiler like below method is defined in superclass
 *              and here i am override it.
 *
 */

//! What's the need of Annotations
/*
 * Basically with Annotations we provide more info. about our code or intend to Compiler, Frameworks, tool or JVM.
 * Common Use For:
 *     1. Compiler Instructions
 *         ↳ Eg. @override
 *     2. Runtime Processing
 *         ↳ Eg. Commenly found on Frameworks like Spring -> @Autowired
 *     3. Code Generation
 *         ↳ Eg. Lombak (pkg/lib) for generate boilerplate for data class
 */

//! Where Java Annotations are placed?
/*
 * Annotations can be placed on top of the following elements in Java code:
 *     1. Classes, Interfaces, Enums
 *     2. Methods, Fields, Constructors
 *     3. Parameters
 *     5. Local Variables
 *     6. Packages
 *     7. Types (Java 8+)
 */

//! Built-in Java Annotations
/*
 *
 * java.lang.annotation
 *      - @Deprecated : It indicates that a declaration is obsolete and has been replaced by a newer form
 *      - @Override : Must override a method from a superclass. If it doesn’t, a compile-time error
 *      - @SuppressWarnings: Tells tha java compiler to ignore specific warnings.
 *      - @SafeVarargs: Used to suppress warnings about unsafe use of varargs.
 *      - @Documented: User to make documentation just like java(https://docs.oracle.com/javase) have
 *                     use javaDoc tool to include custom annotations in the generated documentation.
 *      - @Inherited: It indicates that an annotation type is automatically inherited by subclasses.
 *      - @FunctionalInterface: It indicates that an interface is intended to be a functional interface.
 *      - @Repeatable: use the same annotation multiple times on one element.
 */




import java.lang.annotation.*;

// Custom annotation

/**
 * This annotation is used to document author information for a class.
 * It includes details like the author's name, date, and version.
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @Info(author = "Rahul", date = "2025-06-28", version = 2)
 * public class MyClass { ... }
 * }
 * </pre>
 */
@Documented
@Inherited //
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Info {
    String author();

    String date();

    String[] tags();

    int version() default 1;
}

// Apply custom annotation
@Info(author = "Rahul", date = "2023-10-01", version = 2, tags = {"java", "annotations"})
class Animal {
    void name() {
        System.out.println("NULL");
    }
}

public class Ano1 extends Animal {

    @Deprecated
        // Marks this method as obsolete
    void showMe() {
        System.out.print("Hello");
    }

    @SuppressWarnings("unused")
        // Suppresses unused warning for method
    void fullName() {
        showMe();
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused") // Suppresses unused warning for variabl

        // Create an instance of Ano1 but annotation is applied
        // to Animal class (Need of Inherited annotation)
        Animal obj = new Ano1();

        Info info = obj.getClass().getAnnotation(Info.class);
        System.out.println(info);
    }
}




