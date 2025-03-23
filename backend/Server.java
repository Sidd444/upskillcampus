import java.io.*;
import java.net.*;
import java.sql.*;
import org.json.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server running on port 5000...");

            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleRequest(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

            String request = in.readLine();
            JSONObject response = new JSONObject();

            if (request.startsWith("REGISTER")) {
                String[] parts = request.split(" ");
                boolean success = Bank.registerUser(parts[1], parts[2], Double.parseDouble(parts[3]));
                response.put("status", success ? "success" : "failed");
            } else if (request.startsWith("DEPOSIT")) {
                String[] parts = request.split(" ");
                boolean success = Bank.deposit(parts[1], Double.parseDouble(parts[2]));
                response.put("status", success ? "success" : "failed");
            } else if (request.startsWith("WITHDRAW")) {
                String[] parts = request.split(" ");
                boolean success = Bank.withdraw(parts[1], Double.parseDouble(parts[2]));
                response.put("status", success ? "success" : "failed");
            } else if (request.startsWith("BALANCE")) {
                String[] parts = request.split(" ");
                double balance = Bank.checkBalance(parts[1]);
                response.put("balance", balance);
            }

            out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
