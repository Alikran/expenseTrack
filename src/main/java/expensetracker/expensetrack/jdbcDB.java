package expensetracker.expensetrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sqlitetutorial.net
 */
public class jdbcDB{
    /**
     * Connect to a sample database
     */
    public static void connect() throws ClassNotFoundException {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:\\Users\\j0per\\Desktop\\Exp_tracker\\expenseTrack\\src\\database\\expensedb.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        // Load the JDBC driver
        Class.forName("org.sqlite.JDBC");
        connect();
    }
}