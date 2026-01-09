# Coding Challenge: Custom Color Palette Generator

### **Problem Statement**

You are tasked with creating a custom palette generator in Java using `EnumSet` to manage colors. The palette should be used to create various color schemes (Triadic and Analogous) based on user preferences and the positions of colors within the enum.

#### **1. Color Enum (`Colour`)**

- Define an enum named `Colour` that includes: `RED`, `GREEN`, `BLUE`, `YELLOW`, `ORANGE`, and `PURPLE`.
- Each constant should store an associated `java.awt.Color` object.
- **Methods to implement:**
- A constructor to initialize the `Color` field.
- `public Color getColor()`: Returns the associated `java.awt.Color` object.

#### **2. ColorPalette Class**

- Create a class named `ColorPalette` that utilizes an `EnumSet<Colour>` to store a collection of colors.
- **Methods to implement:**
- `void addColor(Colour color)`: Adds the specified color to the internal `EnumSet`.
- `EnumSet<Colour> generateTriadicScheme(Colour baseColor)`: Generates a scheme containing the base color and two colors evenly spaced (2 and 4 positions away).
- `EnumSet<Colour> generateAnalogousScheme(Colour baseColor)`: Generates a scheme containing the base color and adjacent colors (1 and 5 positions away).
- **Private Helpers:** Implement `findTriadicColors` and `findAnalogousColors` to handle the index calculation logic.

---

### **Color Wheel Logic Reference**

To calculate the indices for the schemes, treat the enum constants as a circular array.

- **Triadic:**
- **Analogous:**

---

### **Sample Input/Output**

**Input:**

```java
import java.awt.Color;

enum Colour {
    RED(Color.RED), GREEN(Color.GREEN), BLUE(Color.BLUE),
    YELLOW(Color.YELLOW), ORANGE(Color.ORANGE), PURPLE(new Color(128, 0, 128));

    //Code Here..
}

class ColorPalette {
    //Code Here..
}

public class Source {
    public static void main(String args[]) throws Exception {
       /* Enter your code here. Read input from STDIN. Print output to STDOUT */
    }
}
```
