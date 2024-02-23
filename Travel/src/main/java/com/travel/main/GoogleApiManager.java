package com.travel.main;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

public class GoogleApiManager {
	
	public String[] fetch(String cityName) throws UnsupportedEncodingException, ParseException {
		
		// cityName을 받아서 travel guide로 검색해줄 것.
		String baseURL = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&order=relevance";//order=viewChout / order=relevance
		String apiKey = "&key=AIzaSyAfq7o_43XZjyNTG--H6F9sqeNYjXamaJA&q=";
		String query = cityName+"-travel-guide";
		String url = baseURL+apiKey+query;
		
		// RestTemplate와 JsonString을 사용해서 최종 url을 넣고. JSON Parsing
		RestTemplate restTemplate = new RestTemplate();
	    String jsonString = restTemplate.getForObject(url, String.class);
	    
	    // JSON Parser를 열어 위에서 JSONString으로 받은 데이터를 JSONObject로 받고, 거기서 내가 필요한 것(items)를 지정
	    JSONParser jsonParser = new JSONParser();
	    JSONObject jsonO = (JSONObject) jsonParser.parse(jsonString);
	    JSONArray items = (JSONArray) jsonO.get("items");
	    
	    String[] ids = new String[5];
	        
	    for(int i=0;i<5;i++) {
	        	
	    	// items > 첫번째 > id > videoId 순으로 들어가며 받을 것.
	     	JSONObject jAll = (JSONObject) items.get(i);
	      	JSONObject jId = (JSONObject) jAll.get("id");
	       	String jVideoId = (String) jId.get("videoId");
	       	ids[i] = jVideoId;
	        	
	    }
	    
	    return ids;

	}
	


}
