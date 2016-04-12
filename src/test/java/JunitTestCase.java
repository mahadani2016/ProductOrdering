import java.net.URI;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;
import org.apache.log4j.Logger;

public class JunitTestCase extends Assert {

	private WebTarget webTarget;
	private static final URI BASEURI = URI.create("http://localhost:8085/api/");
	private Logger log = Logger.getLogger(JunitTestCase.class);

	/**
	 * Test for checking the result.
	 */

	@Test
	public void testGetOrders() {
		Client client = ClientBuilder.newClient();

		try {
			webTarget = client.target(BASEURI);
			String orderResponse = webTarget.path("orders").request().get(String.class);

			if ("[".equalsIgnoreCase(orderResponse.substring(0, 1))
					&& "]".equalsIgnoreCase(orderResponse.substring(
							(orderResponse.length() - 1),
							orderResponse.length())))
				assertTrue(true);
			else
				assertTrue(false);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetPrices() {
		Client client = ClientBuilder.newClient();

		try {
			webTarget = client.target(BASEURI);
			String priceResponse = webTarget.path("prices").request().get(String.class);

			if ("[".equalsIgnoreCase(priceResponse.substring(0, 1))
					&& "]".equalsIgnoreCase(priceResponse.substring(
							(priceResponse.length() - 1),
							priceResponse.length())))
				assertTrue(true);
			else
				assertTrue(false);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetProducts() {
		Client client = ClientBuilder.newClient();

		try {
			webTarget = client.target(BASEURI);
			String productResponse = webTarget.path("products").request().get(String.class);

			if ("[".equalsIgnoreCase(productResponse.substring(0, 1))
					&& "]".equalsIgnoreCase(productResponse.substring(
							(productResponse.length() - 1),
							productResponse.length())))
				assertTrue(true);
			else
				assertTrue(false);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testSubmitOrder() {
		Client client = ClientBuilder.newClient();

		try {
			String t="{\"orderId\":222265402,\"customer\":{\"customerId\":111185274,\"companyName\":\"ppp\",\"street\":\"ttt\",\"postalCode\":\"mmm\",\"city\":\"kkk\",\"country\":\"iiiii\",},\"orderLines\":[{\"orderLineId\":333393830,\"productId\":\"m873LDFkDF%#DSd\",\"productName\":\"Nokia Lumia 1020 - White\",\"quantity\":1,},{\"orderLineId\":333344665,\"productId\":\"s345664lkdLDSDf\",\"productName\":\"Samsung Galaxy 4\",\"quantity\":1,}]}";
					
					webTarget = client.target(BASEURI);
			Response productResponse = webTarget.path("submit").request().post(Entity.json(t));
			if(productResponse.getStatus()==200)
			assertTrue(true);
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
}
