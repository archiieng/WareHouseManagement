package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public  class Main {
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
            System.out.println("3.Exit");
            System.out.println("-----------------");

            System.out.print("Enter your choice(1-3): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> dataShown();
                case 2 -> addProduct(scanner);
                case 3 -> flag = false;
                default -> System.out.println("Invalid choice");
            }
        }try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database connection.");
        }

        scanner.close();
        System.out.println("Goodbye!");

    }
    static Connection connectToDatabase() {
        try {
            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connected to MySQL successfully.");
            return connection;

        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            System.out.println(e.getMessage());
            return null;
        }
    }
    static void dataShown(){
        System.out.println("Show products");
    }
    static void addProduct(Scanner scanner){
        scanner.nextLine();

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

        System.out.println();
        System.out.println("Product added successfully.");
        System.out.println("Name: " + prod);
        System.out.println("Category: " + type);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
        System.out.println("Supplier ID: " + supplierId);

    }
}