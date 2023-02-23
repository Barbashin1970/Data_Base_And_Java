package database.simple;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleJdbc {
    static String url = "jdbc:postgresql://localhost:5432/practicum";
    static String userName = "postgres";
    static String password = "postgres";

    public static void select() {  // печать отформатированного списка покупателей
        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnName + ": " + columnValue + "\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> selectCustomers() { //просто возвращает список покупателей
        List<Customer> customerList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                customerList.add(new Customer(
                          resultSet.getInt(1)
                        , resultSet.getString(2)
                        , resultSet.getString(3)
                        , resultSet.getString(4)));
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            String sql = "INSERT INTO customer (name, email, phone) VALUES (?,?,?) RETURNING id;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Josh");
            preparedStatement.setString(2, "Josh@yande.ru");
            preparedStatement.setString(3, "0123123123123");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        select();
        System.out.println("Список покупателей без форматирования:  " + selectCustomers());
    }
}
