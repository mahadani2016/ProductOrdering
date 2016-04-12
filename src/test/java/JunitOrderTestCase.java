import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import elisa.devtest.endtoend.DbBootstrap;
import elisa.devtest.endtoend.dao.OrderDao;
import elisa.devtest.endtoend.model.Customer;
import elisa.devtest.endtoend.model.Order;
import elisa.devtest.endtoend.model.OrderLine;


public class JunitOrderTestCase extends Assert {

	private Logger log = Logger.getLogger(JunitOrderTestCase.class);
	
    static {
 	   PropertyConfigurator.configure("src/main/resources/logging.properties");
 	   new DbBootstrap().bootstratp();
    }
    
    @Test
    public void testSaveCustomer() {
    	Customer theCustomer = new Customer(2,"Putka oy","Vankilakatu 1","00100","Helsinki","Finland");
    	OrderLine orderLine = new OrderLine(3,"1","Nokia Lumia 1020",1);
    	List<OrderLine> OrderLines = new ArrayList<OrderLine>();
    	OrderLines.add(orderLine);
    	Order order = new Order(3,theCustomer,OrderLines);
        OrderDao orderDao = new OrderDao();
    	try {
			long value = orderDao.submitOrder(order);
			log.debug("Success ->testSaveCustomer"+value);
			assertTrue(true);
		} catch (DataRetrievalFailureException e) {
			e.printStackTrace();
			log.debug("Failure ->testSaveCustomer");
			assertTrue(false);
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.debug("Failure ->testSaveCustomer");
			assertTrue(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Failure ->testSaveCustomer");
			assertTrue(false);
		}
    }

}
