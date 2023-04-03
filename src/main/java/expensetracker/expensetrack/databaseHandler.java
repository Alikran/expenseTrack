package expensetracker.expensetrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class databaseHandler {
    private Connection conn;

    public databaseHandler() {
        try {
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Create a connection to the database
            String url = "jdbc:sqlite:C:\\Users\\j0per\\Desktop\\Exp_tracker\\expenseTrack\\src\\database\\expensedb.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean createUser(String username, String password) {
        try {
            // Check if the username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                return false; // User already exists
            }
            // Create a new user
            PreparedStatement createStmt = conn.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");
            createStmt.setString(1, username);
            createStmt.setString(2, password);
            int rowsInserted = createStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean validateUser(String username, String password) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next(); // Returns true if the user exists with the given username and password
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addIncome(String username, double amount, String source, String month) {
        try {
            // Create a new income entry
            PreparedStatement createStmt = conn.prepareStatement("INSERT INTO income(username, amount, source, month) VALUES (?, ?, ?, ?)");
            createStmt.setString(1, username);
            createStmt.setDouble(2, amount);
            createStmt.setString(3, source);
            createStmt.setString(4, month);
            int rowsInserted = createStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public HashMap<String, Double> getCurrentUserSourcesByMonth(String currentUsername, String month) {
        HashMap<String, Double> sourcesMap = new HashMap<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT source, SUM(amount) as total FROM income WHERE username = ? AND month = ? GROUP BY source");
            stmt.setString(1, currentUsername);
            stmt.setString(2, month);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String source = resultSet.getString("source");
                double amount = resultSet.getDouble("total");
                sourcesMap.put(source, amount);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sourcesMap;
    }

}
