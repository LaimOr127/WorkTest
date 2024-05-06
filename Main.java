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


// Производный класс от DebitCard с бонусной программой
class DebitCardWithBonus extends DebitCard {
    private int bonusPoints;

    public DebitCardWithBonus(double balance) {
        super(balance);
        this.bonusPoints = 0;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void addBonusPoints(int points) {
        bonusPoints += points;
    }
}

// Производный класс от CreditCard с бонусной программой
class CreditCardWithRewards extends CreditCard {
    private String rewardsProgram;

    public CreditCardWithRewards(double balance, double creditLimit, String rewardsProgram) {
        super(balance, creditLimit);
        this.rewardsProgram = rewardsProgram;
    }

    public String getRewardsProgram() {
        return rewardsProgram;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose card type:");
        System.out.println("1. Debit Card");
        System.out.println("2. Credit Card");
        int choice = scanner.nextInt();

        BankCard selectedCard;
        if (choice == 1) {
            selectedCard = new DebitCard(0);
        } else {
            selectedCard = new CreditCard(0, 10000);
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an action:");
            System.out.println("1. Top up balance");
            System.out.println("2. View balance");
            if (selectedCard instanceof CreditCard) {
                System.out.println("3. View credit limit");
            }
            System.out.println("0. Exit");

            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    System.out.print("Enter amount to top up: ");
                    double topUpAmount = scanner.nextDouble();
                    selectedCard.deposit(topUpAmount);
                    System.out.println("Balance topped up successfully.");
                    break;
                case 2:
                    System.out.println("Current balance: " + selectedCard.getAvailableFunds());
                    break;
                case 3:
                    if (selectedCard instanceof CreditCard) {
                        CreditCard creditCard = (CreditCard) selectedCard;
                        System.out.println("Current credit limit: " + creditCard.getAvailableFunds());
                    }
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
