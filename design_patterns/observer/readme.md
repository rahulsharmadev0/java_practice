# 👀 Observer Design Pattern

**Observer** is a **behavioral design pattern** where an object (subject) maintains a list of dependents (observers) and **notifies them automatically** of any state changes — usually by calling one of their methods.

\> “The **Observer Pattern** is a **core building block** of **event-driven programming**.”

### Key Idea

* You have one Subject (aka Publisher) and multiple Observers (aka Subscribers).  
* When the subject’s state changes, it notifies all registered observers.  
* Observers can subscribe/unsubscribe at runtime.

### Problem It Solves

* You want to decouple objects so that when one object changes, many others can react without being tightly coupled.  
* You want a clean way to implement event-based systems or push-style updates.

### Real-World Example

YouTube Channel:

* Subject \= YouTube Channel  
* Observers \= Subscribers  
* When the channel uploads a new video → it notifies all subscribers