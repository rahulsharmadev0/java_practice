# [Proxy Design Pattern](https://refactoring.guru/design-patterns/proxy)

Proxy is a structural design pattern that lets you control access to another object (called the real subject) by placing a proxy object in front of it.  
The proxy acts like a gatekeeper — it can control, delay, restrict, or enhance access to the real object.

![proxy img](https://refactoring.guru/images/patterns/diagrams/proxy/solution-en-2x.png)

### Key Idea

* You don’t want clients to access the real object directly.  
* The proxy looks like the real object, but adds extra logic (like security, caching, logging, etc.)  
* The client doesn’t know whether it’s calling the real object or the proxy.

### What Problem Does It Solve?

* You want to control access to a sensitive or heavy object.  
* You want to add extra behavior before or after the real object is accessed (like lazy loading or access control).  
* You want to defer the creation of a heavy object until needed.

### How It Works

1. Define a common interface (e.g., Image)  
2. Implement the real class (e.g., RealImage)  
3. Create a Proxy class that implements the same interface  
4. Proxy internally holds or manages the real object

### When to Use

* You want to add extra control (security, logging, caching) to an object  
* The object is heavy or slow to create (like database or network)  
* You want lazy initialization or remote access

Use Case:  Virtual proxy, Protection, Remote proxy, Smart proxys