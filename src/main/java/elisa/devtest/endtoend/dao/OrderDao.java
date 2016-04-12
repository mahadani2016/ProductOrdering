package elisa.devtest.endtoend.dao;

import elisa.devtest.endtoend.model.Customer;
import elisa.devtest.endtoend.model.Order;
import elisa.devtest.endtoend.model.OrderLine;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.eclipse.persistence.exceptions.i18n.DatabaseExceptionResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.text.DefaultEditorKit.CutAction;

public class OrderDao {

	public List<Order> findOrders() {
		try {
			return createJdbcTemplate().query(
					"select * from orders",
					(resultSet, rowNumber) -> new Order(resultSet
							.getLong("order_id"), findCustomer(resultSet
							.getLong("customer_id")), findOrderLines(resultSet
							.getLong("order_id"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}

	private List<OrderLine> findOrderLines(long orderId) {
		try {
			return createJdbcTemplate().query(
					"select * from order_line where order_id = ?",
					new Object[] { orderId },
					(resultSet, rowNumber) -> new OrderLine(resultSet
							.getLong("order_line_id"), resultSet
							.getString("product_id"), resultSet
							.getString("product_name"), resultSet
							.getInt("quantity")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.EMPTY_LIST;
	}

	public Customer findCustomer(final long customerId) {
		try {
			return createJdbcTemplate()
					.queryForObject(
							"select * from customer where customer_id = ?",
							new Object[] { customerId },
							(resultSet, rowNumber) -> new Customer(resultSet
									.getLong("customer_id"), resultSet
									.getString("company_name"), resultSet
									.getString("street"), resultSet
									.getString("postal_code"), resultSet
									.getString("city"), resultSet
									.getString("country")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Customer();
	}

	private JdbcTemplate createJdbcTemplate() {
		return new JdbcTemplate(DBConnection.getDataSource());
	}
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new  NamedParameterJdbcTemplate(createJdbcTemplate());
	}

	/**
	 * This method submit the order
	 * 
	 * @param orderdetails
	 *            , customer details
	 * @return
	 */

	public Long submitOrder(Order order) throws DataRetrievalFailureException,DataAccessException,Exception {
		long orderId=0;
		try {
		long custId = saveCustomer(order.getCustomer());
		orderId=saveOrder(custId);
		saveOrderLine(orderId, order.getOrderLines());
		}
		catch (DataRetrievalFailureException drfe) {
            throw  drfe;
        } 
		catch (DataAccessException dae) {
			throw dae;
		}
		catch (Exception e) {
			throw e;
		}
		
		return orderId;
	}

	/**
	 * This method saves the Customer details
	 * 
	 * @param customer
	 *            details
	 * @return
	 */

	private long saveCustomer(Customer customer) throws DataRetrievalFailureException,DataAccessException {
		
		MapSqlParameterSource param = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumn = { "company_id" };
		try {
			param.addValue("companyName", customer.getCompanyName());
			param.addValue("street", customer.getStreet());
			param.addValue("postCode", customer.getPostalCode());
			param.addValue("city", customer.getCity());
			param.addValue("country", customer.getCountry());
			namedParameterJdbcTemplate()
					.update("INSERT INTO customer(company_name, street, postal_code, city, country) values (:companyName, :street, :postCode, :city, :country)",
							param, keyHolder, keyColumn);
		}
		catch (DataRetrievalFailureException e) {
            throw new DataRetrievalFailureException("Invalid data" + e);
        } 
		catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
		return keyHolder.getKey().intValue(); 
	}

	private long  saveOrder(long customerId) throws DataRetrievalFailureException,DataAccessException {

		MapSqlParameterSource param = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumn = { "order_id" };
		
		try {
			param.addValue("custId", customerId);
			namedParameterJdbcTemplate().update("INSERT INTO orders(customer_id) VALUES(:custId)",param,keyHolder,keyColumn);

		} 
		catch (DataRetrievalFailureException e) {
			e.printStackTrace();
            throw new DataRetrievalFailureException("Invalid data" + e);
        } 
		catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}
		return keyHolder.getKey().intValue();
	}

	/**
	 * This method save order lines
	 * 
	 * @param order
	 *            Id, order lines
	 * @return
	 */
	private String saveOrderLine(long orderId, List<OrderLine> orderLines) throws DataRetrievalFailureException,DataAccessException {

		for (OrderLine orderLine : orderLines) {
			try {
				createJdbcTemplate().update(
						"INSERT INTO order_line(order_id, product_id, product_name, quantity) VALUES(?,?,?,?)",
						orderId, orderLine.getProductId(),
						orderLine.getProductName(), orderLine.getQuantity());
			} 
			catch (DataRetrievalFailureException e) {
				e.printStackTrace();
	            throw new DataRetrievalFailureException("Invalid data" + e);
	        } 
			catch (DataAccessException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return "Success";
	}

}
