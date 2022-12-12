package fr.arnaud.cleanarchitecture;

import java.io.File;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Test {

	public static void main(String[] args) throws Exception {
		
		//
		ArrayNode arrayResult = new ObjectMapper().createArrayNode();
		ArrayNode extractResult = new ObjectMapper().createArrayNode();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("PRIVATE-TOKEN", "ssCzfuUE4gvXaVsiyJX6");

		HttpEntity<String> entity = new HttpEntity<>(headers);
		int sizeResult = 0;
		int page = 1;
		do {
			
			ResponseEntity<ArrayNode> retun = restTemplate.exchange("http://gitlab.oetl.fr/api/v4/users/abarbe/events?per_page=20&page=" + page, HttpMethod.GET, entity, ArrayNode.class);
			ArrayNode node = retun.getBody();
			sizeResult = node.size();
			arrayResult.addAll(node);
			
			page++;
			System.out.println(page);
		} while(sizeResult!=0);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File("D:/dev/gitlab.json"), arrayResult);
		
	
		for (JsonNode jsonNode : arrayResult) {
            String createFieldNode = jsonNode.get("created_at").asText();    

            ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(createFieldNode, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            if (zdtWithZoneOffset.getDayOfWeek().equals(DayOfWeek.SATURDAY) || zdtWithZoneOffset.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            	System.out.println(zdtWithZoneOffset);
            	extractResult.add(jsonNode);
            } else if(zdtWithZoneOffset.getHour() > 19) {
            	System.out.println(zdtWithZoneOffset);
            	extractResult.add(jsonNode);
            }
            
        }
		writer.writeValue(new File("D:/dev/gitlabextract.json"), extractResult);
		
		System.out.println("Done");
	}
}
