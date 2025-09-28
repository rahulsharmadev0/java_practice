package design_patterns.prototype;

/*
Each shape has attributes like color, x, y, radius/width/height.
Implement a clone() method in each class so you can copy shapes without using constructors.
Task: Demonstrate creating a list of shapes and then making exact copies.
*/

public class ShapeSystemDemo {
    public static void main(String[] args) {
        // Original circle
        Circle circle = new Circle(10, 10, "red", 5);
        System.out.println("Circle Area: " + circle.getArea());

        // Clone circle
        Circle clonedCircle = (Circle) circle.clone();
        System.out.println("Cloned Circle Area: " + clonedCircle.getArea());

        System.out.println(circle == clonedCircle);

        // Rectangle
        Rectangle rect = new Rectangle(20, 20, "blue", 4, 6);
        System.out.println("Rectangle Area: " + rect.getArea());

        // Clone rectangle
        Rectangle clonedRect = (Rectangle) rect.clone();
        System.out.println("Cloned Rectangle Area: " + clonedRect.getArea());

        System.out.println(rect == clonedRect);
    }
}

abstract class Shape implements Cloneable {
    final String color;
    final int x, y;

    Shape(final int x, final int y, final String color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    abstract int getArea();

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone(); // shallow copy
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // should never happen
        }
    }
}

class Circle extends Shape {
    final int radius;

    Circle(int x, int y, String color, int radius) {
        super(x, y, color);
        this.radius = radius;
    }

    int getArea() {
        return radius * radius;
    }
}

class Rectangle extends Shape {
    int width, height;

    Rectangle(int x, int y, String color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    int getArea() {
        return width * height;
    }
}