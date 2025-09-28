package design_patterns.singleton;

import java.util.Random;

public class SingletonDemo {

    public static void main(String[] args) {

        Thread user1 = new Thread(() -> {
            Account account = Account.getInstance("Alice");
            Random random = new Random();
            account.setBalance(random.nextInt(10000));
            System.out.println("User1: " + account.getHolderName() + ", Balance: " + account.getBalance());
        });
        Thread user2 = new Thread(() -> {
            Account account = Account.getInstance("Rahul");
            Random random = new Random();
            account.setBalance(random.nextInt(10000));
            System.out.println("User1: " + account.getHolderName() + ", Balance: " + account.getBalance());
        });

        user1.start();
        user2.start();
    }

}

final class Account {
    private static Account _instance;

    static synchronized Account getInstance(String holderName) {
        if (_instance == null) _instance = new Account(holderName);
        return _instance;
    }

    private Account(String holderName) {
        this.holderName = holderName;
        this.balance = 0.0;

    }


    private final String holderName;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if (balance < 0)
            throw new IllegalArgumentException("Balance cannot be negative");

        this.balance = balance;
    }

    public String getHolderName() {
        return holderName;
    }
}