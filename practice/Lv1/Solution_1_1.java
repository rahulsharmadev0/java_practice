package Lv1;

import java.awt.Color;
import java.util.EnumSet;

public class Solution_1_1 {
    public static void main(String[] args) {
        Source.main(args);
    }

}

enum Colour {
    RED(Color.RED),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    YELLOW(Color.YELLOW),
    ORANGE(Color.ORANGE),
    PURPLE(new Color(128, 0, 128));

    final Color color;

    private Colour(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

class ColorPalette {
    EnumSet<Colour> colors;

    public ColorPalette() {
        colors = EnumSet.noneOf(Colour.class);

    }

    public void addColor(Colour color) {
        colors.add(color);
    }

    public EnumSet<Colour> generateTriadicScheme(Colour baseColor) {
        Colour[] array = findTriadicColors(baseColor);
        return EnumSet.of(array[0], array[1], array[2]);

    }

    public EnumSet<Colour> generateAnalogousScheme(Colour baseColor) {
        Colour[] array = findAnalogousColors(baseColor);
        return EnumSet.of(array[0], array[1], array[2]);
    }

    public Colour[] findTriadicColors(Colour baseColor) {
        Colour[] array = Colour.values();
        int baseIdx = baseColor.ordinal();
        int length = array.length;

        // 2 and 4 positions away.
        Colour fast = array[(baseIdx + 2) % length];
        Colour forth = array[(baseIdx + 4) % length];
        return new Colour[] { baseColor, fast, forth };

    }

    private Colour[] findAnalogousColors(Colour baseColor) {
        Colour[] array = Colour.values();
        int baseIdx = baseColor.ordinal();
        int length = array.length;

        // 1 and 5 positions away
        Colour fast = array[(baseIdx + 1) % length];
        Colour forth = array[(baseIdx + 5) % length];
        return new Colour[] { baseColor, fast, forth };
    }

}

class Source {
    public static void main(String args[]) {

        ColorPalette palette = new ColorPalette();
        Colour baseColor = Colour.RED;
        System.out.println(palette.generateTriadicScheme(baseColor));
        System.out.println(palette.generateAnalogousScheme(baseColor));
    }
}