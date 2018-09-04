package it.my.test;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonTester {

	
	public static void main(String[] args) throws JsonParseException, IOException {
		
		String theJson = "[{\r\n" + 
				"  \"id\": 1,\r\n" + 
				"  \"first_name\": \"Vale\",\r\n" + 
				"  \"last_name\": \"Findley\",\r\n" + 
				"  \"email\": [\r\n" + 
				"    \"vfindley0@1688.com\",\r\n" + 
				"    \"vfindley0@huffingtonpost.com\",\r\n" + 
				"    \"vfindley0@latimes.com\"\r\n" + 
				"  ]\r\n" + 
				"}, {\r\n" + 
				"  \"id\": 2,\r\n" + 
				"  \"first_name\": \"Valeda\",\r\n" + 
				"  \"last_name\": \"Clarkson\",\r\n" + 
				"  \"email\": [\r\n" + 
				"    \"vclarkson1@unicef.org\",\r\n" + 
				"    \"vclarkson1@ycombinator.com\",\r\n" + 
				"    \"vclarkson1@google.fr\"\r\n" + 
				"  ]\r\n" + 
				"}, {\r\n" + 
				"  \"id\": 3,\r\n" + 
				"  \"first_name\": \"Ross\",\r\n" + 
				"  \"last_name\": \"Wemm\",\r\n" + 
				"  \"email\": [\r\n" + 
				"    \"rwemm2@answers.com\",\r\n" + 
				"    \"rwemm2@jigsy.com\",\r\n" + 
				"    \"rwemm2@sun.com\"\r\n" + 
				"  ]\r\n" + 
				"}]";
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = factory.createParser(theJson);
		
		ArrayNode tree =parser.readValueAsTree();
		for (JsonNode jsonNode : tree) {
			ObjectNode node = (ObjectNode) jsonNode;
			ArrayNode emails = (ArrayNode) node.get("email");
			Iterator<JsonNode> it = emails.iterator();
			while(it.hasNext()){
				String sub = it.next().asText();
				System.out.println(sub);
				if(sub.endsWith(".com")){
					it.remove();
				}
			}
		}
		
		System.out.println(mapper.writeValueAsString(tree));
		
	}
}
