import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {
    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        Scanner scanner = new Scanner(System.in);

        String username = "root";
        String password = "Niraj@123";
        String url = "jdbc:mysql://localhost:3306/NirajHomeData";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use "com.mysql.jdbc.Driver" for older versions

            // Establish the connection to the database
            con = DriverManager.getConnection(url, username, password);

            // Create a statement object to execute queries
            st = con.createStatement();

            while (true) {
                // Display menu options
                System.out.println("Select an action:");
                System.out.println("1. INSERT");
                System.out.println("2. UPDATE");
                System.out.println("3. DELETE");
                System.out.println("4. SELECT");
                System.out.println("5. EXIT");
                System.out.print("Enter your choice (1-5): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline character

                switch (choice) {
                    case 1:
                        // INSERT operation
                        System.out.print("Enter userId: ");
                        int insertId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline character
                        System.out.print("Enter userName: ");
                        String insertName = scanner.nextLine();
                        
                        String insertSQL = "INSERT INTO studenttable (userId, userName) VALUES (?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
                            pstmt.setInt(1, insertId);
                            pstmt.setString(2, insertName);
                            pstmt.executeUpdate();
                            System.out.println("Record inserted successfully.");
                        }
                        break;

                    case 2:
                        // UPDATE operation
                        System.out.print("Enter userId to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline character
                        System.out.print("Enter new userName: ");
                        String updateName = scanner.nextLine();
                        
                        String updateSQL = "UPDATE studenttable SET userName = ? WHERE userId = ?";
                        try (PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
                            pstmt.setString(1, updateName);
                            pstmt.setInt(2, updateId);
                            int rowsAffected = pstmt.executeUpdate();
                            System.out.println("Number of rows updated: " + rowsAffected);
                        }
                        break;

                    case 3:
                        // DELETE operation
                        System.out.print("Enter userId to delete: ");
                        int deleteId = scanner.nextInt();
                        
                        String deleteSQL = "DELETE FROM studenttable WHERE userId = ?";
                        try (PreparedStatement pstmt = con.prepareStatement(deleteSQL)) {
                            pstmt.setInt(1, deleteId);
                            int rowsAffected = pstmt.executeUpdate();
                            System.out.println("Number of rows deleted: " + rowsAffected);
                        }
                        break;

                    case 4:
                        // SELECT operation
                        String selectSQL = "SELECT * FROM studenttable";
                        rs = st.executeQuery(selectSQL);

                        // Process the result set
                        System.out.println("userId | userName");
                        System.out.println("------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt("userId") + " | " + rs.getString("userName"));
                        }
                        break;

                    case 5:
                        // EXIT
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Make sure the JDBC driver is in your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}
