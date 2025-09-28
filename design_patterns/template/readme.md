# Template Method Pattern

Template Method is a behavioral design pattern that defines the skeleton of an algorithm in a superclass and lets subclasses override specific steps of the algorithm without changing its structure.

### Key Idea

* You put fixed parts of an algorithm in a base class.  
* Let the variable/custom parts be overridden in subclasses.  
* Ensures code reuse \+ control of overall algorithm structure.

### Problem It Solve

* Reusing the common parts of an algorithm while allowing subclasses to define only the parts that vary — without changing the overall algorithm structure.

### Pros:

* Promotes code reuse (base logic once)  
* Enforces consistent algorithm structure  
* Lets subclasses customize specific behavior only

### Cons:

* Inheritance makes it less flexible than composition  
* Can lead to inversion of control (subclasses must behave correctly)  
* Overuse may lead to deep inheritance hierarchy

### Real-Life Example

Document Generator:

* Steps: open file → write header → write body → write footer → close file.  
* Only writeBody() changes for PDF vs. HTML vs. Markdown.  
* Template method keeps the process fixed, body is customized.