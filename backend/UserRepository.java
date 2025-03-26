import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public boolean addUser(User user) {
        if (users.containsKey(user.getAccountNumber())) {
            return false;
        }
        users.put(user.getAccountNumber(), user);
        return true;
    }

    public User getUser(String accountNumber) {
        return users.get(accountNumber);
    }

    public boolean updateUser(User user) {
        if (!users.containsKey(user.getAccountNumber())) {
            return false;
        }
        users.put(user.getAccountNumber(), user);
        return true;
    }
}
