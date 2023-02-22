package database.dao.dao;

import database.dao.entities.Customer;
import database.dao.core.PostgreSQLProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends PostgreSQLProvider implements ICustomerDao {

    public CustomerDao() {
        tableName = "customer";
    }

    @Override
    public Customer getById(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE \"id\" = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during getting data for customer with id '" + id + "': " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT * FROM " + tableName;
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (!rs.isClosed()) {
                while (rs.next()) {
                    customers.add(new Customer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"))
                    );
                }
                return customers;
            }
        } catch (SQLException e) {
            System.out.println("Error during getting customers data: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void add(Customer customer) {
        String sql = "INSERT INTO " + tableName + " (\"name\", \"phone\", \"email\") VALUES(?,?,?) RETURNING id";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                customer.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Error during adding customer '" + customer + "': " + e.getMessage());
        }
    }

    @Override
    public void update(Customer customer) {
        String sql = String.format("UPDATE %s SET \"name\" = ?, \"phone\" = ?, \"email\" = ? WHERE id = ?", tableName);
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setLong(4, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during updating customer '" + customer + "': " + e.getMessage());
        }
    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM " + tableName + " WHERE \"id\" = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during deleting customer '" + customer + "': " + e.getMessage());
        }
    }

}