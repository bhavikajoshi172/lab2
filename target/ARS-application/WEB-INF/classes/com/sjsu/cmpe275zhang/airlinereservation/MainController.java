package com.sjsu.cmpe275zhang.airlinereservation;

import javax.json.JsonObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.cmpe275zhang.services.*;

@RestController
public class MainController {
	public @RequestMapping(value = "/") String home(ModelAndView model) {
		System.out.println("running");

		return "abc";
	}

	@RequestMapping(value = "/passenger/{id}", method = RequestMethod.GET, produces = "application/json")
	public JSONObject getPassenger(@PathVariable("id") String id, 
			Model model) throws Exception {
	try
	{
		System.out.println("running" + id + "   " );
		PassengerService p = new PassengerService();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(p.getPassenger(id));
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		
		JSONObject reservationjson=new JSONObject();
		JSONArray ja=(JSONArray) json.get("reservation");
		for(int i=0;i<ja.size();i++)
		{
			((JSONObject)ja.get(i)).remove("passenger");
			
			JSONObject flightjson=new JSONObject();
			JSONArray fja=(JSONArray) ((JSONObject)ja.get(i)).get("flights");
			for(int m=0;m<fja.size();m++)
			{
		//		((JSONObject)fja.get(m)).remove("passengers");
			}
			flightjson.put("flight", ((JSONObject)ja.get(i)).get("flights"));
			
			((JSONObject)ja.get(i)).remove("flights");
			((JSONObject)ja.get(i)).put("flights", flightjson);
		}
		reservationjson.put("reservation", json.get("reservation"));
		
		json.remove("reservation");
		json.put("reservations", reservationjson);
		JSONObject jo=new JSONObject();
		jo.put("passenger", json);

		return jo;
	}
	catch(Exception e)
	{
		
		throw new DefaultException(404,"Sorry, the requested passenger with id "+id+" does not exist");
	}
		
		}

	@RequestMapping(value = "/passenger/{id}", params = {
			"xml" }, method = RequestMethod.GET, produces = "application/xml")
	public JSONObject getPassenger2(@PathVariable("id") String id, @RequestParam("xml") String xml,
			ModelAndView model) throws Exception {
		try
		{
	
		PassengerService p = new PassengerService();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(p.getPassenger(id));
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		
		JSONObject reservationjson=new JSONObject();
		JSONArray ja=(JSONArray) json.get("reservation");
		for(int i=0;i<ja.size();i++)
		{
			((JSONObject)ja.get(i)).remove("passenger");
			JSONObject flightjson=new JSONObject();
			JSONArray fja=(JSONArray) ((JSONObject)ja.get(i)).get("flights");
			for(int m=0;m<fja.size();m++)
			{
		//		((JSONObject)fja.get(m)).remove("passengers");
			}
			flightjson.put("flight", ((JSONObject)ja.get(i)).get("flights"));
			
			((JSONObject)ja.get(i)).remove("flights");
			((JSONObject)ja.get(i)).put("flights", flightjson);
		}
		reservationjson.put("reservation", json.get("reservation"));
		
		json.remove("reservation");
		json.put("reservations", reservationjson);
		JSONObject jo=new JSONObject();
		jo.put("passenger", json);

		return jo;
		}
		catch(Exception e)
		{
			
			throw new DefaultException(404,"Sorry, the requested passenger with id "+id+" does not exist");
		}
			
	}

	@RequestMapping(value = "passenger", method = RequestMethod.POST, produces = "application/json")
	public JSONObject addPassenger(@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,@RequestParam("age") int age,
			@RequestParam("gender") String gender,@RequestParam("phone") String phone, ModelAndView model) throws DefaultException {
		try
		{
		PassengerService p = new PassengerService();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(p.addPassenger(firstname,lastname,age,gender,phone));
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		
		JSONObject reservationjson=new JSONObject();
		JSONArray ja=(JSONArray) json.get("reservation");
		for(int i=0;i<ja.size();i++)
		{
			((JSONObject)ja.get(i)).remove("passenger");
			JSONObject flightjson=new JSONObject();
			JSONArray fja=(JSONArray) ((JSONObject)ja.get(i)).get("flights");
			for(int m=0;m<fja.size();m++)
			{
		//		((JSONObject)fja.get(m)).remove("passengers");
			}
			flightjson.put("flight", ((JSONObject)ja.get(i)).get("flights"));
			
			((JSONObject)ja.get(i)).remove("flights");
			((JSONObject)ja.get(i)).put("flights", flightjson);
		}
		reservationjson.put("reservation", json.get("reservation"));
		
		json.remove("reservation");
		json.put("reservations", reservationjson);
		
		JSONObject jo=new JSONObject();
		jo.put("passenger", json);

		return jo;
		}
		catch(Exception e)
		{
			
			throw new DefaultException(404,firstname+ " here is the failure reason "+ e.getMessage());
		}
			
	}
	@RequestMapping(value = "passenger/{id}", params = {"firstname","lastname","age","gender","phone" },
			method = RequestMethod.PUT, produces = "application/json")
	public JSONObject updatePassenger(@PathVariable("id") String id,@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,@RequestParam("age") int age,
			@RequestParam("gender") String gender,@RequestParam("phone") String phone, ModelAndView model) throws DefaultException {
		try
		{
		PassengerService p = new PassengerService();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(p.updatePassenger(id,firstname,lastname,age,gender,phone));
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		
		JSONObject reservationjson=new JSONObject();
		JSONArray ja=(JSONArray) json.get("reservation");
		for(int i=0;i<ja.size();i++)
		{
			((JSONObject)ja.get(i)).remove("passenger");
			JSONObject flightjson=new JSONObject();
			JSONArray fja=(JSONArray) ((JSONObject)ja.get(i)).get("flights");
			for(int m=0;m<fja.size();m++)
			{
		//		((JSONObject)fja.get(m)).remove("passengers");
			}
			flightjson.put("flight", ((JSONObject)ja.get(i)).get("flights"));
			
			((JSONObject)ja.get(i)).remove("flights");
			((JSONObject)ja.get(i)).put("flights", flightjson);
		}
		reservationjson.put("reservation", json.get("reservation"));
		
		json.remove("reservation");
		json.put("reservations", reservationjson);
		JSONObject jo=new JSONObject();
		jo.put("passenger", json);

		return jo;
		}
		catch(Exception e)
		{
			
			throw new DefaultException(404,"Passenger with id "+id+" does not exist");
		}
	}
	@RequestMapping(value = "passenger/{id}", method = RequestMethod.DELETE, produces = "application/xml")
	public Response deletePassenger(@PathVariable("id") String id, ModelAndView model) throws DefaultException {
		try
		{

		PassengerService p = new PassengerService();
		p.deletePassenger(id);
		Response r=new Response();
		r.setCode(200);
		r.setMessage("Passenger with id"+id+" is deleted successfully");
		return r;
		}
		catch(Exception e)
		{
			
			throw new DefaultException(404,"Passenger with id "+id+" does not exist");
		}
	}
	@ExceptionHandler(DefaultException.class)
	public BadRequest conflict(DefaultException ex) {
	return new BadRequest(""+ex.getCode(),ex.getMessage());
	}
}
	
	 

