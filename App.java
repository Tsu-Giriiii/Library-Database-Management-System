import java.sql.*;

public class App {
        private static final String URL = "jdbc:mysql://localhost:3306/random";
        private static final String USER = "root";
        private static final String PASSWORD = "Jaihanuman@10";
        private Connection conn;
    
        public App() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load the driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    
        public ResultSet executeQuery(String query) throws SQLException {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }
    
        public int executeUpdate(String query) throws SQLException {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        }
    
        public void close() throws SQLException {
            conn.close();
        }
    

    public static void main(String[] args) throws SQLException,ClassNotFoundException {
        new App();
    }
}


