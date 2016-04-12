package elisa.devtest.endtoend.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import elisa.devtest.endtoend.dao.DBConnection;
import elisa.devtest.endtoend.model.Customer;

public class OrderService {
	  	    
	    public List<Customer> findCustomers(long customerId) {
	        JdbcTemplate template = new JdbcTemplate(DBConnection.getDataSource());
	        return template.query("select * from customer where customer_id = ?", new Object[] {customerId}, new CustomerDtoMapper());
	    }

	    private class CustomerDtoMapper implements RowMapper<Customer> {
	        @Override
	        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
	            return new Customer(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6));
	        }
	    }

}
