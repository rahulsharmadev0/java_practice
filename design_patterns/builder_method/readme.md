# 🧱 Builder Design Pattern

Builder is a creational design pattern that lets you build complex objects step-by-step.  
It separates the construction process from the final object’s representation, so the same building process can create different objects.

### Key Idea

* Break object creation into multiple steps (set part-by-part).  
* Useful when object has many fields, optional parts, or nested structures.  
* Final object is created using a build() method.  
* Keeps your code clean, especially for objects with many parameters

### What Problem Does It Solve?

* You want to create an object with many optional or required fields.  
* Using a constructor with many arguments becomes confusing and hard to read.  
* You want to build different representations of the same object in a controlled way.

### How It Solves

* You create a builder class that has methods to set each property (e.g., .setName(), .setAge()).  
* Once all properties are set, you call .build() to create the final object.  
* The builder internally uses a private constructor or static nested class.

### When to Use

* A class has many constructor parameters (especially optional ones).  
* Object creation needs to be step-by-step or customizable.  
* You want immutable objects with clear setup code.

### ✅ Pros

* Makes code more readable, especially for many parameters.  
* Avoids telescoping constructors (many overloaded constructors).  
* Supports immutable objects.  
* Makes object creation more controlled and step-by-step.

### ❌ Cons

* Requires more code/classes than regular constructors.