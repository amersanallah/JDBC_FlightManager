
import java.sql.*;
import java.util.Scanner;

public class FlightViewer {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/FlightDB";
        String username = "root";

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter MySQL root password: ");
            String password = scanner.nextLine();

            System.out.print("Enter a terminal number (2, 3, 4, or 5): ");
            String terminal = scanner.nextLine();

            String sql = """
                    SELECT a.flight_number,
                           r.from_city,
                           a.scheduled,
                           a.delay_status,
                           a.terminal
                    FROM Arrivals a
                    INNER JOIN FlightRoutes r
                        ON a.flight_number = r.flight_number
                    WHERE a.terminal = ?
                    ORDER BY a.scheduled ASC
                    """;

            try (Connection conn =
                         DriverManager.getConnection(url, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, terminal);

                try (ResultSet rs = pstmt.executeQuery()) {

                    System.out.printf(
                            "%-15s %-25s %-20s %-20s %-10s%n",
                            "Flight Number",
                            "Departure City",
                            "Scheduled",
                            "Delay Status",
                            "Terminal"
                    );

                    System.out.println("-".repeat(95));

                    while (rs.next()) {

                        String flightNumber = rs.getString("flight_number");
                        String departureCity = rs.getString("from_city");
                        String scheduled = rs.getString("scheduled");
                        String delayStatus = rs.getString("delay_status");
                        String terminalNum = rs.getString("terminal");

                        System.out.printf(
                                "%-15s %-25s %-20s %-20s %-10s%n",
                                flightNumber,
                                departureCity,
                                scheduled,
                                delayStatus,
                                terminalNum
                        );
                    }
                }

            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
            }
        }
    }
}
