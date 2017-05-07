package com.sjsu.cmpe275zhang.airlinereservation;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.cmpe275zhang.dao.PassengerDao;
import com.sjsu.cmpe275zhang.services.*;

public class SoapController {
 
	
	@RequestMapping(value = "/passenger/{id}",params = {"xml"}, method = RequestMethod.GET, produces="application/xml")
	public 	Passenger getPassenger(@PathVariable("id") String id,@ModelAttribute("xml") String xml) throws Exception
	{
		PassengerService ps=new PassengerService();
	    Passenger p = ps.getPassenger(id);	      
	    return  p;
	}
}
