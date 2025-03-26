public class BankService {
    private UserRepository userRepository = new UserRepository();

    public boolean registerUser(String name, String accountNumber, double initialBalance) {
        return userRepository.addUser(new User(name, accountNumber, initialBalance));
    }

    public String deposit(String accountNumber, double amount) {
        User user = userRepository.getUser(accountNumber);
        if (user == null) {
            return "Account does not exist.";
        }
        user.setBalance(user.getBalance() + amount);
        userRepository.updateUser(user);
        return "Deposit successful for " + user.getName() + ".";
    }

    public String withdraw(String accountNumber, double amount) {
        User user = userRepository.getUser(accountNumber);
        if (user == null) {
            return "Account does not exist.";
        }
        if (user.getBalance() < amount) {
            return "Insufficient balance for " + user.getName() + ".";
        }
        user.setBalance(user.getBalance() - amount);
        userRepository.updateUser(user);
        return "Withdrawal successful for " + user.getName() + ".";
    }

    public String checkBalance(String accountNumber) {
        User user = userRepository.getUser(accountNumber);
        if (user == null) {
            return "Account does not exist.";
        }
        return user.getName() + "'s balance: " + user.getBalance();
    }
}
