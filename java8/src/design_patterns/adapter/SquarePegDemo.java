package design_patterns.adapter;


/*
Square Peg – Round Hole
❓Problem:
You have two incompatible classes:
class RoundHole {
    private double radius;
    public RoundHole(double radius) { this.radius = radius; }
    public boolean fits(RoundPeg peg) {
        return peg.getRadius() <= radius;
    }
}

class RoundPeg {
    private double radius;
    public RoundPeg(double radius) { this.radius = radius; }
    public double getRadius() { return radius; }
}
Now you have a SquarePeg class:
class SquarePeg {
    private double width;
    public SquarePeg(double width) { this.width = width; }
    public double getWidth() { return width; }
}

Task:
    Create an adapter SquarePegAdapter that allows a SquarePeg to fit into a RoundHole.
    The fits() method should still work correctly using geometry logic:
    A square peg fits if: radius >= width * √2 / 2

Concepts Involved:
    - Adapter Pattern (object adapter)
    - Geometry logic
    - Composition
* */

public class SquarePegDemo {

    public static void main(String[] args) {

        RoundHole hole = new RoundHole(510);

        SquarePeg smallSquarePeg = new SquarePeg(50);

        SquarePeg bigSquarePeg = new SquarePeg(5110);

        SquarePegAdapter smallSquarePegAdapter = new SquarePegAdapter(smallSquarePeg);
        SquarePegAdapter bigSquarePegAdapter = new SquarePegAdapter(bigSquarePeg);

        System.out.println( "Can bigSquarePeg go into Hole: "+ hole.fits(bigSquarePegAdapter));
        System.out.println( "Can smallSquarePeg go into Hole: "+ hole.fits(smallSquarePegAdapter));
    }
}


class RoundHole {
    private final double radius;
    public RoundHole(double radius) { this.radius = radius; }
    public boolean fits(RoundPeg peg) {
        return peg.getRadius() <= radius;
    }
}

class RoundPeg {
    private final double radius;
    public RoundPeg(double radius) { this.radius = radius; }
    public double getRadius() { return radius; }
}

class SquarePeg {
    private final double width;
    public SquarePeg(double width) { this.width = width; }
    public double getWidth() { return width; }
}


// SquarePegAdapter that allows a SquarePeg to fit into a RoundHole
class SquarePegAdapter  extends RoundPeg{
    private final SquarePeg squarePeg;
    public SquarePegAdapter(SquarePeg squarePeg) {
        super(0); // useless
     this.squarePeg = squarePeg;
    }

    @Override
    public double getRadius() {
        return  squarePeg.getWidth() * Math.sqrt(2)/2;
    }
}