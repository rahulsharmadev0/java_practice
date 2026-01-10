# Coding Challenge: Mobile App Analyzer

### **Problem Statement**

You are required to implement a mobile application analysis tool in Java. This task involves modeling an `App` class and creating a logic class, `MobileAppAnalyzer`, that leverages the **Stream API** to filter and analyze a collection of application data.

---

### **Class Specifications**

#### 1. Class: `App`

- **Data Members (Private):**
- `String name`
- `int downloadCount`
- `double rating`

- **Constructor:**
- `App(String name, int downloadCount, double rating)`: A public constructor to initialize all data members.

- **Methods:**
- Public getters for all data members.
- A `toString()` method (provided in the stub) for easy identification of objects.

#### 2. Class: `MobileAppAnalyzer`

Implement the following methods using **Java Streams**:

- **`List<App> filterRatings(List<App> apps)`**
- **Logic:** Filter out (keep) only the apps that meet **both** criteria: a rating of 4.0 or higher **and** at least 1000 downloads.
- **Return:** A list of the filtered `App` objects.

- **`Optional<App> getHighestDownloadApp(List<App> apps)`**
- **Logic:** Search the list to find the application with the maximum `downloadCount`.
- **Return:** An `Optional` containing the app with the most downloads, or an empty `Optional` if the list is empty.

---

### **Sample Execution**

**Input:**

```java
List<App> apps = new ArrayList<>();
apps.add(new App("App1", 1500, 4.5));
apps.add(new App("App2", 800, 3.9));
apps.add(new App("App3", 5000, 4.8));
apps.add(new App("App4", 300, 3.7));
apps.add(new App("App5", 20000, 4.9));

```

**Output:**

```text
[App{name='App1', downloads=1500, rating=4.5}, App{name='App3', downloads=5000, rating=4.8}, App{name='App5', downloads=20000, rating=4.9}]
Optional[App{name='App5', downloads=20000, rating=4.9}]

```

---

### **Starter Code Stub**

```java
import java.util.*;
import java.util.stream.Collectors;

class App {
    // Define private data members

    // Define public constructor

    // Define public getters

    @Override
    public String toString() {
        return name; // Simplified for output matching
    }
}

class MobileAppAnalyzer {

    public List<App> filterRatings(List<App> apps) {
        // TODO: Use Stream API to filter rating >= 4.0 and downloads >= 1000
        return null;
    }

    public Optional<App> getHighestDownloadApp(List<App> apps) {
        // TODO: Use Stream API to find the app with the max download count
        return null;
    }
}

public class Source {
    public static void main(String[] args) {
        /* Use this to test your implementation */
    }
}

```
