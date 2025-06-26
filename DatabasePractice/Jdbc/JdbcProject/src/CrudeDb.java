import java.sql.*;
import java.util.Scanner;

public class CrudeDb {
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
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the database
            con = DriverManager.getConnection(url, username, password);

            // Create a statement object to execute queries
            st = con.createStatement();

            while (true) {
                // Display menu options
                System.out.println("Select an action:");
                System.out.println("1. INSERT INTO studenttable");
                System.out.println("2. UPDATE studenttable");
                System.out.println("3. DELETE FROM studenttable");
                System.out.println("4. SELECT FROM studenttable");
                System.out.println("5. CREATE TABLE");
                System.out.println("6. MERGE TABLES");
                System.out.println("7. SHOW DATA FROM TABLE");
                System.out.println("8. INSERT INTO ANY TABLE");
                System.out.println("9. EXIT");
                System.out.println("10. DELETE TABLE");
                System.out.println("12. UNION");
                System.out.println("11. CREATE TWO TABLES with User-Defined Columns (with Primary and Foreign Keys)");
                System.out.println("13. INNER JOIN");
                System.out.println("14. LEFT OUTER JOIN");
                System.out.println("15. RIGHT OUTER JOIN");
                System.out.println("16. FULL OUTER JOIN");
                System.out.print("Enter your choice (1-9): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        // INSERT operation into studenttable
                        System.out.print("Enter userId: ");
                        int insertId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
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
                        // UPDATE operation in studenttable
                        System.out.print("Enter userId to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
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
                        // DELETE operation from studenttable
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
                        // SELECT operation from studenttable
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
                        // CREATE TABLE operation
                        System.out.print("Enter new table name: ");
                        String tableName = scanner.nextLine();
                        System.out.print("Enter column details (e.g., id INT PRIMARY KEY, name VARCHAR(100)): ");
                        String columns = scanner.nextLine();

                        String createTableSQL = "CREATE TABLE " + tableName + " (" + columns + ")";
                        try {
                            st.executeUpdate(createTableSQL);
                            System.out.println("Table " + tableName + " created successfully.");
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 6:
                        // MERGE TABLES operation
                        System.out.print("Enter the name of the first table to merge: ");
                        String table1 = scanner.nextLine();
                        System.out.print("Enter the name of the second table to merge: ");
                        String table2 = scanner.nextLine();
                        System.out.print("Enter the name of the new merged table: ");
                        String mergedTable = scanner.nextLine();

                        String mergeSQL = "CREATE TABLE " + mergedTable + " AS SELECT * FROM " + table1
                                + " UNION ALL SELECT * FROM " + table2;
                        try {
                            st.executeUpdate(mergeSQL);
                            System.out.println(
                                    "Tables " + table1 + " and " + table2 + " merged into " + mergedTable + ".");
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 7:
                        // SHOW DATA FROM TABLE operation
                        System.out.print("Enter the table name to show data from: ");
                        String showTable = scanner.nextLine();
                        String showSQL = "SELECT * FROM " + showTable;

                        try (ResultSet showRs = st.executeQuery(showSQL)) {
                            ResultSetMetaData metaData = showRs.getMetaData();
                            int columnCount = metaData.getColumnCount();

                            // Print column names
                            System.out.print("Columns: ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i));
                                if (i < columnCount) {
                                    System.out.print(" | ");
                                }
                            }
                            System.out.println();
                            System.out.println(new String(new char[80]).replace("\0", "-")); // Print a separator line

                            // Print rows
                            while (showRs.next()) {
                                for (int i = 1; i <= columnCount; i++) {
                                    System.out.print(showRs.getString(i));
                                    if (i < columnCount) {
                                        System.out.print(" | ");
                                    }
                                }
                                System.out.println();
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 8:
                        // INSERT INTO ANY TABLE operation
                        System.out.print("Enter the table name to insert data into: ");
                        String anyTable = scanner.nextLine();

                        // Retrieve column names for the table
                        String columnQuery = "DESCRIBE " + anyTable;
                        try (ResultSet columnsRs = st.executeQuery(columnQuery)) {
                            StringBuilder columnNamesBuilder = new StringBuilder();
                            StringBuilder placeholdersBuilder = new StringBuilder();
                            int columnCount = 0;

                            while (columnsRs.next()) {
                                if (columnCount > 0) {
                                    columnNamesBuilder.append(",");
                                    placeholdersBuilder.append(",");
                                }
                                columnNamesBuilder.append(columnsRs.getString("Field"));
                                placeholdersBuilder.append("?");
                                columnCount++;
                            }

                            // If no columns were found, exit the loop
                            if (columnCount == 0) {
                                System.out.println("No columns found for table " + anyTable);
                                break;
                            }

                            String columnNames = columnNamesBuilder.toString();
                            String placeholders = placeholdersBuilder.toString();

                            // Prepare insertion query
                            String insertQuery = "INSERT INTO " + anyTable + " (" + columnNames + ") VALUES ("
                                    + placeholders + ")";
                            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                                for (int i = 0; i < columnCount; i++) {
                                    System.out.print("Enter value for " + columnNames.split(",")[i] + ": ");
                                    String value = scanner.nextLine();
                                    pstmt.setString(i + 1, value);
                                }
                                pstmt.executeUpdate();
                                System.out.println("Record inserted into " + anyTable + " successfully.");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 9:
                        // EXIT
                        System.out.println("Exiting...");
                        return;

                    case 10:
                        // DELETE TABLE operation
                        System.out.print("Enter the name of the table to delete: ");
                        String tableToDelete = scanner.nextLine();

                        String deleteTableSQL = "DROP TABLE " + tableToDelete;
                        try {
                            st.executeUpdate(deleteTableSQL);
                            System.out.println("Table " + tableToDelete + " deleted successfully.");
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;
                        
                        case 11: // CREATE TWO TABLES with User-Defined Columns
                        System.out.print("Enter first table name: ");
                        String firstTableName = scanner.nextLine();
                        
                        System.out.print("How many columns for " + firstTableName + "? ");
                        int firstTableColumnCount = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                    
                        StringBuilder firstTableColumns = new StringBuilder();
                        
                        for (int i = 0; i < firstTableColumnCount; i++) {
                            System.out.print("Enter column " + (i + 1) + " name: ");
                            String columnName = scanner.nextLine();
                            
                            System.out.print("Enter data type for " + columnName + " (e.g., INT, VARCHAR(100)): ");
                            String dataType = scanner.nextLine();
                            
                            System.out.print("Enter any constraints for " + columnName + " (PRIMARY KEY, NOT NULL, or leave blank): ");
                            String constraints = scanner.nextLine();
                            
                            firstTableColumns.append(columnName).append(" ").append(dataType);
                            if (!constraints.isEmpty()) {
                                firstTableColumns.append(" ").append(constraints);
                            }
                            if (i < firstTableColumnCount - 1) {
                                firstTableColumns.append(", ");
                            }
                        }
                        
                        // Create the first table
                        String createFirstTableSQL = "CREATE TABLE " + firstTableName + " (" + firstTableColumns.toString() + ")";
                        try {
                            st.executeUpdate(createFirstTableSQL);
                            System.out.println("Table " + firstTableName + " created successfully.");
                        } catch (SQLException e) {
                            System.err.println("SQL Error while creating " + firstTableName + ": " + e.getMessage());
                        }
                    
                        // For the second table
                        System.out.print("Enter second table name: ");
                        String secondTableName = scanner.nextLine();
                        
                        System.out.print("How many columns for " + secondTableName + "? ");
                        int secondTableColumnCount = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                    
                        StringBuilder secondTableColumns = new StringBuilder();
                        
                        for (int i = 0; i < secondTableColumnCount; i++) {
                            System.out.print("Enter column " + (i + 1) + " name: ");
                            String columnName = scanner.nextLine();
                            
                            System.out.print("Enter data type for " + columnName + " (e.g., INT, VARCHAR(100)): ");
                            String dataType = scanner.nextLine();
                            
                            // For the first column, ask for foreign key if needed
                            if (i == 0) {
                                System.out.print("Is this a foreign key? (yes/no): ");
                                String isForeignKey = scanner.nextLine();
                                if (isForeignKey.equalsIgnoreCase("yes")) {
                                    System.out.print("Enter the referenced table name: ");
                                    String referencedTable = scanner.nextLine();
                                    System.out.print("Enter the referenced column name: ");
                                    String referencedColumn = scanner.nextLine();
                                    secondTableColumns.append(columnName).append(" ").append(dataType)
                                        .append(", FOREIGN KEY (").append(columnName).append(") REFERENCES ")
                                        .append(referencedTable).append("(").append(referencedColumn).append(")");
                                } else {
                                    secondTableColumns.append(columnName).append(" ").append(dataType);
                                }
                            } else {
                                secondTableColumns.append(columnName).append(" ").append(dataType);
                            }
                            
                            if (i < secondTableColumnCount - 1) {
                                secondTableColumns.append(", ");
                            }
                        }
                    
                        // Create the second table
                        String createSecondTableSQL = "CREATE TABLE " + secondTableName + " (" + secondTableColumns.toString() + ")";
                        try {
                            st.executeUpdate(createSecondTableSQL);
                            System.out.println("Table " + secondTableName + " created successfully.");
                        } catch (SQLException e) {
                            System.err.println("SQL Error while creating " + secondTableName + ": " + e.getMessage());
                        }
                        break;
                    
                   
                        case 12: // UNION
                        System.out.print("Enter the first SELECT statement (e.g., SELECT * FROM table1): ");
                        String unionQuery1 = scanner.nextLine();
                        System.out.print("Enter the second SELECT statement (e.g., SELECT * FROM table2): ");
                        String unionQuery2 = scanner.nextLine();

                        String unionSQL = unionQuery1 + " UNION " + unionQuery2;
                        try (ResultSet unionResult = st.executeQuery(unionSQL)) {
                            while (unionResult.next()) {
                                // Process result
                                System.out.println(unionResult.getString(1)); // Adjust based on your SELECT query
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 13: // INNER JOIN
                        System.out.print("Enter the first table for INNER JOIN: ");
                        String innerTable1 = scanner.nextLine();
                        System.out.print("Enter the second table for INNER JOIN: ");
                        String innerTable2 = scanner.nextLine();
                        System.out.print("Enter the ON condition (e.g., innerTable1.id = innerTable2.foreignId): ");
                        String innerCondition = scanner.nextLine();

                        String innerJoinSQL = "SELECT * FROM " + innerTable1 + " INNER JOIN " + innerTable2 + " ON "
                                + innerCondition;
                        try (ResultSet innerResult = st.executeQuery(innerJoinSQL)) {
                            while (innerResult.next()) {
                                // Process result
                                System.out.println(innerResult.getString(1)); // Adjust based on your SELECT query
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 14: // LEFT OUTER JOIN
                        System.out.print("Enter the first table for LEFT JOIN: ");
                        String leftTable1 = scanner.nextLine();
                        System.out.print("Enter the second table for LEFT JOIN: ");
                        String leftTable2 = scanner.nextLine();
                        System.out.print("Enter the ON condition: ");
                        String leftCondition = scanner.nextLine();

                        String leftJoinSQL = "SELECT * FROM " + leftTable1 + " LEFT OUTER JOIN " + leftTable2 + " ON "
                                + leftCondition;
                        try (ResultSet leftResult = st.executeQuery(leftJoinSQL)) {
                            while (leftResult.next()) {
                                // Process result
                                System.out.println(leftResult.getString(1)); // Adjust based on your SELECT query
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 15: // RIGHT OUTER JOIN
                        System.out.print("Enter the first table for RIGHT JOIN: ");
                        String rightTable1 = scanner.nextLine();
                        System.out.print("Enter the second table for RIGHT JOIN: ");
                        String rightTable2 = scanner.nextLine();
                        System.out.print("Enter the ON condition: ");
                        String rightCondition = scanner.nextLine();

                        String rightJoinSQL = "SELECT * FROM " + rightTable1 + " RIGHT OUTER JOIN " + rightTable2
                                + " ON " + rightCondition;
                        try (ResultSet rightResult = st.executeQuery(rightJoinSQL)) {
                            while (rightResult.next()) {
                                // Process result
                                System.out.println(rightResult.getString(1)); // Adjust based on your SELECT query
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    case 16: // FULL OUTER JOIN
                        System.out.print("Enter the first table for FULL JOIN: ");
                        String fullTable1 = scanner.nextLine();
                        System.out.print("Enter the second table for FULL JOIN: ");
                        String fullTable2 = scanner.nextLine();
                        System.out.print("Enter the ON condition: ");
                        String fullCondition = scanner.nextLine();

                        String fullJoinSQL = "SELECT * FROM " + fullTable1 + " FULL OUTER JOIN " + fullTable2 + " ON "
                                + fullCondition;
                        try (ResultSet fullResult = st.executeQuery(fullJoinSQL)) {
                            while (fullResult.next()) {
                                // Process result
                                System.out.println(fullResult.getString(1)); // Adjust based on your SELECT query
                            }
                        } catch (SQLException e) {
                            System.err.println("SQL Error: " + e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 9.");
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
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }
}
