import java.sql.Connection;
import java.sql.Statement;

public class DBSetup {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                         "name TEXT, " +
                         "account_number TEXT UNIQUE, " +
                         "balance REAL)";
            stmt.executeUpdate(sql);
            System.out.println("Database initialized!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
