package database.dao.dao;

import database.dao.entities.Customer;

import java.util.List;

public class DAOExample {

    public static void main(String[] args) {
        CustomerDao customerDao = new CustomerDao();

        prettyPrint("1. All customers (initial state):", customerDao.getAll());

        Customer newCustomer = new Customer("Александра",
                "+7(81153)37060", "roamer@yahoo.ca");
        customerDao.add(newCustomer);

        prettyPrint("2. All customers (+ new):", customerDao.getAll());

        newCustomer.setPhone("+7(3513)885877");
        newCustomer.setEmail("jfinke@live.com");
        customerDao.update(newCustomer);

        prettyPrint("3. All customers (updated):", customerDao.getAll());

        customerDao.delete(newCustomer);

        prettyPrint("4. All customers (removed):", customerDao.getAll());
    }

    public static void prettyPrint(String msg, List<Customer> customers) {
        System.out.println("\n" + msg);
        for (Customer customer : customers) {
            System.out.println("\t" + customer);
        }
    }
}