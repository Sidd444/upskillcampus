import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bank {
    // Register User
    public static boolean registerUser(String name, String accountNumber, double initialBalance) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (name, account_number, balance) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, accountNumber);
            stmt.setDouble(3, initialBalance);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Deposit Money
    public static boolean deposit(String accountNumber, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Withdraw Money
    public static boolean withdraw(String accountNumber, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            // Check balance
            String checkSql = "SELECT balance FROM users WHERE account_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getDouble("balance") >= amount) {
                // Proceed with withdrawal
                String sql = "UPDATE users SET balance = balance - ? WHERE account_number = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setDouble(1, amount);
                stmt.setString(2, accountNumber);
                stmt.executeUpdate();
                return true;
            } else {
                System.out.println("Insufficient balance!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // Check Balance
    public static double checkBalance(String accountNumber) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }
}
