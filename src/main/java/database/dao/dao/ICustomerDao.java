package database.dao.dao;

import database.dao.entities.Customer;

import java.util.List;

public interface ICustomerDao {
    Customer getById(int id);

    List<Customer> getAll();

    void add(Customer customer);

    void update(Customer customer);

    void delete(Customer customer);
}