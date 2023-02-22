import org.junit.Test;
import database.dao.dao.CustomerDao;
import database.dao.entities.Customer;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class SimpleJdbcTest {
    @Test
    public void createCustomer() {
        //CustomerApi api;
        //Customer customer = api.createCustomer

        //Arrange
        CustomerDao customerDao = new CustomerDao();
        Customer customer = new Customer("Jack", "123213", "jack@gmail.com");

        //Act
        customerDao.add(customer);

        //Act
        Customer customerActual = customerDao.getById(customer.getId());
        assertThat("", customerActual.getId(), notNullValue());
        assertTrue(customer.getName().equals(customerActual.getName()));
    }
}
