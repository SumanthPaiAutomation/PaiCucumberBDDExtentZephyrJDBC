package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

    private static Connection connection;
    private static Statement statement;

    // Establish the database connection
    public static void connectToDatabase() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Execute SQL query and return result set
    public static ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Close the database connection
    public static void closeDatabaseConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//usage

//Feature: E-commerce Stock Management
//
//  Scenario: Purchase and verify stock reduction
//    Given I am connected to the e-commerce database
//    And there are 20 units of product with ID 1 in stock
//    When I purchase 5 units of product with ID 1
//    Then the stock of product with ID 1 should be reduced by 5
//    And I close the e-commerce database connection

//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.When;
//import io.cucumber.java.en.Then;
//import static org.junit.Assert.assertEquals;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ECommerceSteps {
//
//    private int initialStock;
//    private int purchasedStock;
//
//    @Given("I am connected to the e-commerce database")
//    public void connectToDatabase() {
//        JDBCUtils.connectToDatabase();
//    }
//
//    @Given("there are {int} units of product with ID {int} in stock")
//    public void getProductStock(int stock, int productId) {
//        ResultSet resultSet = JDBCUtils.executeQuery("SELECT stock FROM products WHERE id=" + productId);
//        try {
//            if (resultSet.next()) {
//                initialStock = resultSet.getInt("stock");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @When("I purchase {int} units of product with ID {int}")
//    public void purchaseProduct(int quantity, int productId) {
//        // Assume some code for making a purchase and updating the database
//        // This could involve a Selenium script interacting with a web application
//        purchasedStock = quantity;
//    }
//
//    @Then("the stock of product with ID {int} should be reduced by {int}")
//    public void verifyStockReduction(int productId, int expectedReduction) {
//        ResultSet resultSet = JDBCUtils.executeQuery("SELECT stock FROM products WHERE id=" + productId);
//        try {
//            if (resultSet.next()) {
//                int currentStock = resultSet.getInt("stock");
//                int actualReduction = initialStock - currentStock;
//                assertEquals(expectedReduction, actualReduction);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Then("I close the e-commerce database connection")
//    public void closeDatabaseConnection() {
//        JDBCUtils.closeDatabaseConnection();
//    }
//}
