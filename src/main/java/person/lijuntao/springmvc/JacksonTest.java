package person.lijuntao.springmvc;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {
	@Test
	public void testJsonParse() throws Exception{
		String carJson =
		        "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

		JsonFactory factory = new JsonFactory();
		JsonParser  parser  = factory.createParser(carJson);

		while(!parser.isClosed()){
		    JsonToken jsonToken = parser.nextToken();

		    System.out.println("jsonToken = " + jsonToken);
		}
	}
	@Test
	public void testJsonParse2() throws Exception{
		String carJson =
		        "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";

		JsonFactory factory = new JsonFactory();
		JsonParser  parser  = factory.createParser(carJson);

		Car car = new Car();
		while(!parser.isClosed()){
		    JsonToken jsonToken = parser.nextToken();

		    if(JsonToken.FIELD_NAME.equals(jsonToken)){
		        String fieldName = parser.getCurrentName();
		        System.out.println(fieldName);

		        jsonToken = parser.nextToken();

		        if("brand".equals(fieldName)){
		            car.brand = parser.getValueAsString();
		        } else if ("doors".equals(fieldName)){
		            car.doors = parser.getValueAsInt();
		        }
		    }
		}

		System.out.println("car.brand = " + car.brand);
		System.out.println("car.doors = " + car.doors);
	}
	@Test
	public void testJsonParse3() throws Exception{
		String carJson =
		        "{ \"brand\" : \"Mercedes\", \"doors\" : 5," +
		        "  \"owners\" : [\"John\", \"Jack\", \"Jill\"]," +
		        "  \"nestedObject\" : { \"field\" : \"value\" } }";

		ObjectMapper objectMapper = new ObjectMapper();


		try {
			
			
		    JsonNode node = objectMapper.readValue(carJson, JsonNode.class);
		   // node.
		    
		    
		    JsonNode copy = node.deepCopy();
		    //node.
		    
		    
		   System.out.println( node.get("brand").isValueNode());
		   System.out.println( node.get("owners").isValueNode());
		    	
		    	
		    Iterator<JsonNode> iterator = node.iterator();
		    JsonNode jsonNode = iterator.next();

		    /*JsonNode brandNode = node.get("brand");
		    String brand = brandNode.asText();
		    System.out.println("brand = " + brand);

		    JsonNode doorsNode = node.get("doors");
		    int doors = doorsNode.asInt();
		    System.out.println("doors = " + doors);

		    JsonNode array = node.get("owners");
		    JsonNode jsonNode = array.get(0);
		    String john = jsonNode.asText();
		    System.out.println("john  = " + john);

		    JsonNode child = node.get("nestedObject");
		    JsonNode childField = child.get("field");
		    String field = childField.asText();
		    System.out.println("field = " + field);*/

		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
}

class Car{
	String brand;
	int doors;
}

