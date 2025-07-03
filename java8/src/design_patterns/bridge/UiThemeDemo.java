package design_patterns.bridge;

/*
Theme-Based UI Renderer
🧩 Problem:
You are building a UI framework that renders different widgets like Button, TextBox, etc.
Each widget should render in a light or dark theme.
You don’t want to create classes like LightButton, DarkButton, LightTextBox, etc.
🔧 Task:
    - Use Bridge Pattern to avoid class explosion.
    - Widget is the abstraction
    - Theme (light/dark) is the implementation
    - A Button or TextBox should render using its associated theme dynamically.
🧠 Concepts:
    - Composition
    - Interface abstraction
    - Clean UI design
*/
public class UiThemeDemo {
    public static void main(String[] args) {
        DarkTheme darkTheme = new DarkTheme();
        LightTheme lightTheme = new LightTheme();
        var button = new Button(darkTheme);
        button.render();
    }

}

record ThemeData(String primaryColor, String secondaryColor) {
}

interface ThemeMode {
    ThemeData getThemeData();
}

record DarkTheme() implements ThemeMode {
    @Override
    public ThemeData getThemeData() {
        return new ThemeData("blue", "black");
    }
}

record LightTheme() implements ThemeMode {
    @Override
    public ThemeData getThemeData() {
        return new ThemeData("red", "white");
    }
}


interface Widget {
    public void render();
}

record Button(ThemeMode themeMode) implements Widget {

    @Override
    public void render() {
        var themeData = themeMode.getThemeData();
        String content = "Rendering Button with " +
                "Primary Color: " + themeData.primaryColor() +
                ", Secondary Color: " + themeData.secondaryColor();
        System.out.println(content);
    }
}