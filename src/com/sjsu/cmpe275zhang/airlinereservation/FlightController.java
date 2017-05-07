package com.sjsu.cmpe275zhang.airlinereservation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.json.JsonObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.cmpe275zhang.dao.PassengerDao;
import com.sjsu.cmpe275zhang.services.*;
@RestController
public class FlightController 
{
	
	
	@RequestMapping(value = "/flight/{number}",method = RequestMethod.GET, produces = "application/json")
	public JSONObject getFlight(@PathVariable("number") String fid, ModelAndView model) throws Exception
	{
	try
	
	{
		System.out.println("running" + fid + "   " );
		FlightService f = new FlightService();
		 
        Flight flight=f.getFlight(fid);
        List<Passenger> passengers=f.getPassengerByFlight(fid);
        ObjectMapper mapper = new ObjectMapper();
        
        String jsonInString = mapper.writeValueAsString(flight);
        JSONParser parser=new JSONParser(); 
        JSONObject json = (JSONObject) parser.parse(jsonInString);
        String passInString = mapper.writeValueAsString(passengers);
        JSONArray passjson = (JSONArray) parser.parse(passInString);
        for(int m=0;m<passjson.size();m++)
        {
            ((JSONObject)passjson.get(m)).remove("reservation");
        }
 
        JSONObject passengersJson=new JSONObject();
        passengersJson.put("passenger", passjson);
        json.put("passengers", passengersJson);
        JSONObject jo=new JSONObject();
        jo.put("flight", json);
 
        return jo;
	}
	
	catch(Exception e)
	{
		
		throw new DefaultException(404,"Sorry, the requested passenger with id "+fid+" does not exist");
	}
	
	
	}
	
	@RequestMapping(value = "/flight/{number}", params = {"xml" }, method = RequestMethod.GET, produces = "application/xml")
	public JSONObject getFlight2(@PathVariable("number") String fid, @RequestParam("xml") String xml, ModelAndView model) throws Exception
	{
		try
	
	{
	
		System.out.println("running" + fid + "   " + xml);
		FlightService f = new FlightService();
		 
        Flight flight=f.getFlight(fid);
        List<Passenger> passengers=f.getPassengerByFlight(fid);
        ObjectMapper mapper = new ObjectMapper();
        
        String jsonInString = mapper.writeValueAsString(flight);
        JSONParser parser=new JSONParser(); 
        JSONObject json = (JSONObject) parser.parse(jsonInString);
        String passInString = mapper.writeValueAsString(passengers);
        JSONArray passjson = (JSONArray) parser.parse(passInString);
        for(int m=0;m<passjson.size();m++)
        {
            ((JSONObject)passjson.get(m)).remove("reservation");
        }
 
        JSONObject passengersJson=new JSONObject();
        passengersJson.put("passenger", passjson);
        json.put("passengers", passengersJson);
        JSONObject jo=new JSONObject();
        jo.put("flight", json);
 
        return jo;
	
	}
	catch(Exception e)
	{
		
		throw new DefaultException(404,"Sorry, the requested passenger with id "+fid+" does not exist");
	}
	}
	
	

	
	
	@RequestMapping(value = "flight/{number}", method = RequestMethod.POST, produces = "application/xml", params = {"price","to","from","departureTime","arrivalTime","description", "capacity", "model", "manufacturer", "yearOfManufacture"})
	public JSONObject addFlight(@PathVariable("number") String number, @RequestParam("price") int price,
			@RequestParam("to") String toport,@RequestParam("from") String fromport,
			@RequestParam("departureTime") String dtime,@RequestParam("arrivalTime") String atime,@RequestParam("description") String desc, @RequestParam("capacity") int cap, @RequestParam("manufacturer") String manu, @RequestParam("model") String model1, @RequestParam("yearOfManufacture") int years, ModelAndView model) throws DefaultException 
	{
		try
		{
			FlightService f = new FlightService();
			
	        Flight flight=f.addFlight(number,price,toport,fromport,dtime,atime,desc,cap,manu,model1,years);
	        List<Passenger> passengers=f.getPassengerByFlight(flight.getNumber());
	        ObjectMapper mapper = new ObjectMapper();
	        
	        String jsonInString = mapper.writeValueAsString(flight);
	        JSONParser parser=new JSONParser(); 
	        JSONObject json = (JSONObject) parser.parse(jsonInString);
	        String passInString = mapper.writeValueAsString(passengers);
	        JSONArray passjson = (JSONArray) parser.parse(passInString);
	        for(int m=0;m<passjson.size();m++)
	        {
	            ((JSONObject)passjson.get(m)).remove("reservation");
	        }
	 
	        JSONObject passengersJson=new JSONObject();
	        passengersJson.put("passenger", passjson);
	        json.put("passengers", passengersJson);
	        JSONObject jo=new JSONObject();
	        jo.put("flight", json);
	 
	        return jo;
		}
		catch(Exception e)
		{
			throw new DefaultException(404," here is the failure reason "+ e.getMessage());
		}
	}
	
	@RequestMapping(value = "flight/{number}", method = RequestMethod.DELETE, produces = "application/xml")
	public Response deleteFlight(@PathVariable("number") String fid, ModelAndView model) throws DefaultException 
	{
		
	
		FlightService f = new FlightService();
		f.deleteFlight(fid);
		Response r=new Response();
		r.setCode(200);
		r.setMessage("Flight with id "+fid+ " is deleted successfully");
		return r;
		}
		
	
	
	
	
	@ExceptionHandler(DefaultException.class)
	public BadRequest conflict(DefaultException ex) {
	return new BadRequest(""+ex.getCode(),ex.getMessage());
	}
	
	

}
