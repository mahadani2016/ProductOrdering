import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import elisa.devtest.endtoend.DbBootstrap;
import elisa.devtest.endtoend.dao.PricingDao;
import elisa.devtest.endtoend.model.PriceDto;


public class JunitPricingTestCase extends Assert {

	private Logger log = Logger.getLogger(JunitPricingTestCase.class);
    static {
 	   PropertyConfigurator.configure("src/main/resources/logging.properties");
 	   new DbBootstrap().bootstratp();
    }
	
    @Test
	public void testFindPrices() {
		PricingDao pricingDao = new PricingDao();
		List<PriceDto>  prices= pricingDao.findPrices();
		if(prices==null){
			assertTrue(false);
			log.debug("Failure->testFindPrices()");
		}else if(prices.size()>0){
			log.debug("Success->testFindPrices()");
			assertTrue(true);
		}else{
			log.debug("Failure->testFindPrices()");
			assertTrue(false);
		}
	}

}
