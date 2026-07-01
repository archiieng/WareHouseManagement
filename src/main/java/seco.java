import java.sql.*;
import java.util.Scanner;

public class seco {
    private static final String URL = "jdbc:mysql://localhost:3306/warehouse_db";
    private static final String USER = "root";
    private static final String PASSWORD = "DB_password";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Connection connection = connectToDatabase();
        int choice;
        boolean flag = true;

        while (flag) {
            System.out.println("-----------------");
            System.out.println("WareHouse Program");
            System.out.println("-----------------");
            System.out.println("1. Show products");
            System.out.println("2. Add product");
            System.out.println("3. Exit");
            System.out.println("-----------------");

            System.out.print("Enter your choice(1-3): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> dataShown(connection);
                case 2 -> addProduct(scanner, connection);
                case 3 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database connection.");
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    static Connection connectToDatabase() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL successfully.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    static void dataShown(Connection connection) {
        String sql = "SELECT * FROM products";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println();
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.println(
                        "ID: " + rs.getInt("product_id") +
                                ", Name: " + rs.getString("name") +
                                ", Category: " + rs.getString("category") +
                                ", Price: " + rs.getDouble("price") +
                                ", Quantity: " + rs.getInt("quantity")
                );
            }
            if (!any) {
                System.out.println("No products found.");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    static void addProduct(Scanner scanner, Connection connection) {
        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter product name: ");
        String prod = scanner.nextLine();

        System.out.print("Enter category: ");
        String type = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Enter minimum stocks: ");
        int stocks = scanner.nextInt();

        System.out.print("Enter supplier id: ");
        int supplierId = scanner.nextInt();

        String sql = "INSERT INTO products (name, category, price, quantity, minimum_stock, supplier_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, prod);
            ps.setString(2, type);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            ps.setInt(5, stocks);
            ps.setInt(6, supplierId);

            ps.executeUpdate();

            System.out.println();
            System.out.println("Product added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
