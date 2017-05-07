package com.sjsu.cmpe275zhang.airlinereservation;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjsu.cmpe275zhang.dao.PassengerDao;
import com.sjsu.cmpe275zhang.services.*;

@RestController
public class ReservationController {

	@RequestMapping(value = "/reservation/{number}", method = RequestMethod.GET, produces = "application/json")
	public JSONObject getReservation(@PathVariable("number") String number, 
			ModelAndView model) throws Exception {
		System.out.println("running" + number );
		ReservationService p = new ReservationService();
		ObjectMapper mapper = new ObjectMapper();
		Reservation r=p.getReservation(number);
		PassengerDao passengerDao=new PassengerDao();
		
		String jsonInString = mapper.writeValueAsString(r);
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		JSONObject passJson=(JSONObject) json.get("passenger");
		passJson.remove("id");
		passJson.put("id",r.getPassenger().getId());
		passJson.remove("reservation");
		
		json.remove("passenger");
		
		json.put("passenger", passJson);
		JSONObject flightjson=new JSONObject();
		JSONArray fja= (JSONArray) json.get("flights");
		for(int m=0;m<fja.size();m++)
		{
	//		((JSONObject)fja.get(m)).remove("passengers");
		}
		flightjson.put("flight",  json.get("flights"));
		
		json.remove("flights");
		json.put("flights", flightjson);
		JSONObject jo=new JSONObject();
		jo.put("reservation", json);

		return jo;
	}


	@RequestMapping(value = "reservation", method = RequestMethod.POST, produces = "application/xml")
	public JSONObject addReservation(@RequestParam("passengerId") String passengerId,
			@RequestParam("flightLists") String flights, ModelAndView model) throws JsonProcessingException, ParseException, DefaultException {
		
		ReservationService p = new ReservationService();
		ObjectMapper mapper = new ObjectMapper();
		Reservation r= p.addReservation(passengerId,flights);
		PassengerDao passengerDao=new PassengerDao();
		
		String jsonInString = mapper.writeValueAsString(r);
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		JSONObject passJson=(JSONObject) json.get("passenger");
		passJson.remove("id");
		passJson.put("id",r.getPassenger().getId());
		passJson.remove("reservation");
		
		json.remove("passenger");
		
		json.put("passenger", passJson);
		JSONObject flightjson=new JSONObject();
		JSONArray fja= (JSONArray) json.get("flights");
		for(int m=0;m<fja.size();m++)
		{
	//		((JSONObject)fja.get(m)).remove("passengers");
		}
		flightjson.put("flight",  json.get("flights"));
		
		json.remove("flights");
		json.put("flights", flightjson);
		JSONObject jo=new JSONObject();
		jo.put("reservation", json);

		return jo;
	}
	@RequestMapping(value = "reservation/{number}", params = {"flightsAdded","flightsRemoved" },
			method = RequestMethod.POST, produces = "application/json")
	public JSONObject updateReservation(@PathVariable("number") String number,@RequestParam(value="flightsRemoved", required = false) String flightsRemoved,
			@RequestParam(value="flightsAdded", required = false) String flightsAdded, ModelAndView model) throws JsonProcessingException, ParseException {
		
		ReservationService p = new ReservationService();
		
		ObjectMapper mapper = new ObjectMapper();
		Reservation r=p.updateReservation(number,flightsAdded,flightsRemoved);
		String jsonInString = mapper.writeValueAsString(r);
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		JSONObject passJson=(JSONObject) json.get("passenger");
		passJson.remove("id");
		passJson.put("id",r.getPassenger().getId());
		passJson.remove("reservation");
		
		json.remove("passenger");
		
		json.put("passenger", passJson);
		JSONObject flightjson=new JSONObject();
		JSONArray fja= (JSONArray) json.get("flights");
		for(int m=0;m<fja.size();m++)
		{
	//		((JSONObject)fja.get(m)).remove("passengers");
		}
		flightjson.put("flight",  json.get("flights"));
		
		json.remove("flights");
		json.put("flights", flightjson);
		JSONObject jo=new JSONObject();
		jo.put("reservation", json);

		return jo;
		
	}
	
	@RequestMapping(value = "reservation/{orderNumber}", method = RequestMethod.DELETE, produces = "application/xml")
	public Response deleteReservation(@PathVariable("orderNumber") String id, ModelAndView model) throws DefaultException  
	{
		try
		{
		ReservationService re = new ReservationService();
		re.deleteReservation(id);
		Response r=new Response();
		r.setCode(200);
		r.setMessage("Reservation with id"+id+" is deleted successfully");
		return r;
		}
		catch(Exception e)
		{
			
			throw new DefaultException(404,"Reservation with id "+id+" does not exist");
		}
	}
	
	@RequestMapping(value = "reservation", method = RequestMethod.GET, produces = "application/xml")
	public JSONObject searchReservation(@RequestParam(value = "passenger_id", required = false) String pid, @RequestParam(value = "to", required = false) String departing_city, @RequestParam(value = "from", required = false) String arrival_city, @RequestParam(value = "number", required = false) String fid, ModelAndView model) throws JsonProcessingException, ParseException 
	{
		System.out.println("running" + pid );
		ReservationService re = new ReservationService();
		ObjectMapper mapper = new ObjectMapper();
		List<Reservation> rl=re.searchReservation(pid, departing_city, arrival_city, fid);
		JSONArray arr=new JSONArray();
		for(Reservation r:rl)
		{
		String jsonInString = mapper.writeValueAsString(r);
		JSONParser parser=new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonInString);
		JSONObject passJson=(JSONObject) json.get("passenger");
		passJson.remove("id");
		passJson.put("id",r.getPassenger().getId());
		passJson.remove("reservation");
		
		json.remove("passenger");
		
		json.put("passenger", passJson);
		JSONObject flightjson=new JSONObject();
		JSONArray fja= (JSONArray) json.get("flights");
		for(int m=0;m<fja.size();m++)
		{
	//		((JSONObject)fja.get(m)).remove("passengers");
		}
		flightjson.put("flight",  json.get("flights"));
		
		json.remove("flights");
		json.put("flights", flightjson);
		JSONObject jo1=new JSONObject();
		jo1.put("reservation", json);
		arr.add(jo1);
		

		
		}
		JSONObject jo=new JSONObject();
		jo.put("reservations", arr);

		return jo;
		
	}
	@ExceptionHandler(DefaultException.class)
	public BadRequest conflict(DefaultException ex) {
	return new BadRequest(""+ex.getCode(),ex.getMessage());
	}
	
}
