import java.util.Scanner;

public class BankingInformationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nBANKING SYSTEM");
            System.out.println("1. Register User");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Account Number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter Initial Deposit: ");
                    double deposit = scanner.nextDouble();
                    Bank.registerUser(name, accNum, deposit);
                    break;
                case 2:
                    System.out.print("Enter Account Number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    Bank.deposit(accNum, amount);
                    break;
                case 3:
                    System.out.print("Enter Account Number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    amount = scanner.nextDouble();
                    Bank.withdraw(accNum, amount);
                    break;
                case 4:
                    System.out.print("Enter Account Number: ");
                    accNum = scanner.nextLine();
                    System.out.println("Balance: " + Bank.checkBalance(accNum));
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
