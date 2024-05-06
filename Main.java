import java.util.Scanner;

abstract class BankCard {
    protected double balance;

    public BankCard(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract boolean pay(double amount);

    public double getBalance() {
        return balance;
    }

    public abstract String getAvailableFunds();
}

class DebitCard extends BankCard {
    public DebitCard(double balance) {
        super(balance);
    }

    public boolean pay(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAvailableFunds() {
        return "Available funds: " + balance;
    }

    public void topUp(double amount) {
        deposit(amount);
    }
}

class CreditCard extends BankCard {
    private double creditLimit;

    public CreditCard(double balance, double creditLimit) {
        super(balance);
        this.creditLimit = creditLimit;
    }

    public boolean pay(double amount) {
        if (amount <= balance + creditLimit) {
            if (amount <= balance) {
                balance -= amount;
            } else {
                creditLimit -= (amount - balance);
                balance = 0;
            }
            return true;
        }
        return false;
    }

    public String getAvailableFunds() {
        return "Available funds: " + balance + ", Credit limit: " + creditLimit;
    }

    public void topUpCreditLimit(double amount) {
        creditLimit += amount;
    }

    public void topUp(double amount) {
        balance += amount;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем кредитную карту с лимитом 10000
        CreditCard creditCard = new CreditCard(0, 10000);

        // Интерактивное взаимодействие с пользователем
        System.out.println("Credit card balance: " + creditCard.getAvailableFunds());
        System.out.print("Enter amount to top up: ");
        double topUpAmount = scanner.nextDouble();
        creditCard.topUp(topUpAmount);
        System.out.println("After topping up: " + creditCard.getAvailableFunds());

        System.out.print("Enter amount to pay: ");
        double paymentAmount = scanner.nextDouble();
        boolean paymentResult = creditCard.pay(paymentAmount);
        if (paymentResult) {
            System.out.println("Payment successful. Updated balance: " + creditCard.getAvailableFunds());
        } else {
            System.out.println("Insufficient funds.");
        }

        scanner.close();
    }
}
