import utils.IO;

public class Basics2 {
    public static void main(String[] args) {
        Programmer programmer = new Programmer();
        Programmer2 programmer2;

        IO.printf(Programmer2.a());

    }
}


class Programmer {
    int id;
    String name;

    static {
        System.out.println("Programmer class loaded");
    }

    public Programmer() {
    }

    void writeCode() {
        System.out.println("Maa Chala...");
    }
}

class Programmer2 {
    static int a(){
        IO.printf("Programmer a() method executed");
        return 0;
    }

    static {
        System.out.println("Programmer2 class loaded");
    }
}