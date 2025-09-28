# 🧬 Prototype Design Pattern

Prototype is a creational design pattern that lets you create objects by copying (cloning) an existing object (called a prototype), instead of creating a new one from scratch.
![alt text](https://refactoring.guru/images/patterns/content/prototype/prototype-comic-3-en-2x.png)
### Key Idea

* Instead of using new, you copy an existing object.  
* Used when object creation is expensive or complex.  
* Object must support cloning — usually by implementing Cloneable.

### What Problem Does It Solve?

* You want to create many similar objects, but object creation is slow or complex.  
* You want to avoid reinitializing the same values repeatedly.  
* You want to decouple the object creation logic from classes.

### How It Works

* Create a base class or interface that defines a clone() method.  
* Implement clone() in concrete classes.  
* Instead of using new, you clone existing objects.

### When to Use

* Object creation is costly (e.g., database, network, file system).  
* You need many similar objects.  
* You want to avoid new and initialize fields from scratch every time.  
* You want to keep copies of default/pre-filled objects.

Eg. Ui Templates, Game Dev. , Object pooling

### ✅ Pros

* Faster object creation (no need to reinitialize)  
* Helps avoid long constructor chains  
* Reduces dependency on new

### ❌ Cons

* Cloning complex objects (deep copy) can be tricky  
* Java’s Cloneable is considered broken (no checked exception, poorly designed)  
* May require manual copy logic if object has references