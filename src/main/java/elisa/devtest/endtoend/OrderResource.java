package elisa.devtest.endtoend;

import elisa.devtest.endtoend.dao.OrderDao;
import elisa.devtest.endtoend.model.Customer;
import elisa.devtest.endtoend.model.Order;
import elisa.devtest.endtoend.model.OrderLine;
import elisa.devtest.endtoend.ConstantData;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/")
public class OrderResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("orders")
	public Collection<Order> getOrders() {
		return new OrderDao().findOrders();
	}

	/**
	 * This method submit the order
	 * 
	 * @param order
	 *            details
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("submit")
	public Response submitOrder(String orderDetails) throws ParseException,
			IOException {
		JSONParser parser = new JSONParser();
		List<OrderLine> orderLn = new ArrayList<OrderLine>();
		Customer customer = new Customer();
		try {

			JSONObject orderObjectTemp = (JSONObject) parser
					.parse(orderDetails);
			ObjectMapper mapper = new ObjectMapper();

			customer = mapper.readValue(
					orderObjectTemp.get(new ConstantData().getCustomerVar())
							.toString(), Customer.class);

			JSONArray jsonArray = (JSONArray) orderObjectTemp
					.get(new ConstantData().getOrderlineCustomerVar());

			for (int i = 0; i < jsonArray.size(); i++) {

				JSONObject tempOrderLine = (JSONObject) jsonArray.get(i);
				OrderLine orderLine = mapper.readValue(
						tempOrderLine.toString(), OrderLine.class);
				orderLn.add(orderLine);

			}
			
			long ordId = new OrderDao().submitOrder(new Order(0, customer, orderLn));
			return Response.status(200).entity(ordId).build();
			
		} catch (JsonGenerationException e) {
			 e.printStackTrace();
			return Response.status(400).build();
		} catch (JsonMappingException e) {
			 e.printStackTrace();
			return Response.status(400).build(); 
		} catch (Exception e) {
			 e.printStackTrace();
			return Response.status(500).build();
		}
		
	}
}
