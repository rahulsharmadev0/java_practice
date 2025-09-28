# Strategy Design Pattern

Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

### Key Idea

* You extract similar behaviors (algorithms/strategies) into separate classes.  
* These classes implement a common interface.  
* The client (context) can switch between strategies at runtime.

### What Problem It Solves

* You have multiple algorithms doing similar work differently (e.g., payment types, sorting, compression).  
* You don’t want to hardcode logic with if-else or switch.  
* You want to follow Open/Closed Principle — support new algorithms without changing client code.

### How It Works

1. Define a Strategy Interface (common behavior).  
2. Implement concrete strategy classes for each variant.  
3. Create a Context class that uses a Strategy.  
4. Inject the strategy at runtime (via constructor or setter).

### Real-Life Example

Navigation App:

* Strategy \= route calculation method (car, bike, walk)  
* Context \= app UI  
* User selects a strategy → app uses it dynamically
