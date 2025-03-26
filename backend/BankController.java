import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class BankController {
    private static BankService bankService = new BankService();

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(5000), 0);
            server.createContext("/register", new RegisterHandler());
            server.createContext("/deposit", new DepositHandler());
            server.createContext("/withdraw", new WithdrawHandler());
            server.createContext("/balance", new BalanceHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 5000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Handling /register request");
            handleCors(exchange); 
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> request = parseRequest(exchange);
                System.out.println("Parsed request: " + request);
                boolean success = bankService.registerUser(request.get("name"), request.get("accountNumber"), Double.parseDouble(request.get("initialDeposit")));
                String response = "{\"success\":" + success + "}";
                sendResponse(exchange, response);
                System.out.println("Response sent: " + response);
            } else {
                System.out.println("Invalid request method for /register");
            }
        }
    }

    static class DepositHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Handling /deposit request");
            handleCors(exchange); 
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> request = parseRequest(exchange);
                System.out.println("Parsed request: " + request);
                String message = bankService.deposit(request.get("accountNumber"), Double.parseDouble(request.get("amount")));
                String response = "{\"message\":\"" + message + "\"}";
                sendResponse(exchange, response);
                System.out.println("Response sent: " + response);
            } else {
                System.out.println("Invalid request method for /deposit");
            }
        }
    }

    static class WithdrawHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Handling /withdraw request");
            handleCors(exchange); 
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> request = parseRequest(exchange);
                System.out.println("Parsed request: " + request);
                String message = bankService.withdraw(request.get("accountNumber"), Double.parseDouble(request.get("amount")));
                String response = "{\"message\":\"" + message + "\"}";
                sendResponse(exchange, response);
                System.out.println("Response sent: " + response);
            } else {
                System.out.println("Invalid request method for /withdraw");
            }
        }
    }

    static class BalanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Handling /balance request");
            handleCors(exchange); 
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> request = parseRequest(exchange);
                System.out.println("Parsed request: " + request);
                String message = bankService.checkBalance(request.get("accountNumber"));
                String response = "{\"message\":\"" + message + "\"}";
                sendResponse(exchange, response);
                System.out.println("Response sent: " + response);
            } else {
                System.out.println("Invalid request method for /balance");
            }
        }
    }

    private static void handleCors(HttpExchange exchange) throws IOException {
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            System.out.println("Handling CORS preflight request");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            exchange.sendResponseHeaders(204, -1);
        } else {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        }
    }

    private static Map<String, String> parseRequest(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        System.out.println("Request body: " + body);
        Map<String, String> requestMap = new HashMap<>();
        body = body.trim().replace("{", "").replace("}", "").replace("\"", ""); // Remove JSON braces and quotes
        String[] pairs = body.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                requestMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return requestMap;
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
