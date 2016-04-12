import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import elisa.devtest.endtoend.DbBootstrap;
import elisa.devtest.endtoend.dao.ProductDao;
import elisa.devtest.endtoend.model.ProductDto;


public class JunitProductTestCase extends Assert{

	private Logger log = Logger.getLogger(JunitProductTestCase.class);
    static {
 	   PropertyConfigurator.configure("src/main/resources/logging.properties");
 	   new DbBootstrap().bootstratp();
    }
	
    @Test
	public void testFindProduct() {
    	ProductDao productDao = new ProductDao();
		List<ProductDto>  products= productDao.findProducts();
		log.debug(products);
		if(products==null){
			assertTrue(false);
			log.debug("Failure ->testFindProduct()");
		}else if(products.size()>0){
			log.debug("Success ->testFindProduct()");
			assertTrue(true);
		}else{
			log.debug("Failure ->testFindProduct()");
			assertTrue(false);
		}
	}
}
