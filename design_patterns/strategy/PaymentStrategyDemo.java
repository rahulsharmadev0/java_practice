package design_patterns.strategy;

import java.util.Random;

public class PaymentStrategyDemo {
    public static void main(String[] args) {
        int[] shoppingCart = {3430, 343, 400, 300, 100};
        Random random = new Random();
        PaymentGateway[] paymentMethods = {
                new CreditCardPayment(),
                new DebitCardPayment(),
                new DebitCardPayment(),
                new UPIPayment(),
        };
        for (int price : shoppingCart) {
            int idx = random.nextInt(0, paymentMethods.length);
            paymentMethods[idx].pay(price);
        }

    }
}


interface PaymentGateway {
    public void pay(int amount);
}

class CreditCardPayment implements PaymentGateway {
    @Override
    public void pay(int amount) {
        System.out.println("Credit Card Payment of $" + amount);
    }
}

class DebitCardPayment implements PaymentGateway {
    @Override
    public void pay(int amount) {
        System.out.println("Debit Card Payment of $" + amount);
    }
}

class UPIPayment implements PaymentGateway {
    @Override
    public void pay(int amount) {
        System.out.println("UPI Payment of $" + amount);
    }
}
