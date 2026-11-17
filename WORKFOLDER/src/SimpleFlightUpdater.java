
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Updates the delay status of flight LX354 in FlightDB.
 *
 * Security improvement:
 * The previous implementation used Statement and
 * concatenated user input directly into an SQL query.
 * This allowed SQL Injection attacks that could modify
 * all records by manipulating the WHERE clause.
 *
 * This implementation uses PreparedStatement with
 * parameterized queries. User input is treated as data
 * rather than executable SQL, preventing SQL Injection.
 */
public class SimpleFlightUpdater {

    /**
     * Connects to MySQL and securely updates one flight.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/FlightDB?"
                + "allowLoadLocalInfile=true"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=Asia/Jerusalem"
                + "&useSSL=false";

        String user = "root";

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter MySQL root password: ");
            String password = scanner.nextLine();

            String sql = "UPDATE Arrivals SET delay_status = ? "
                    + "WHERE flight_number = ?";

            try (Connection conn =
                         DriverManager.getConnection(url, user, password);
                 PreparedStatement pstmt =
                         conn.prepareStatement(sql)) {

                System.out.print(
                        "Enter the new delay status for flight 'LX354': "
                );

                String userInput = scanner.nextLine();

                pstmt.setString(1, userInput);
                pstmt.setString(2, "LX354");

                int rowsAffected = pstmt.executeUpdate();

                System.out.println(
                        rowsAffected + " row(s) affected."
                );

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
