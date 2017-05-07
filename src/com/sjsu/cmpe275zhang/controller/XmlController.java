package com.sjsu.cmpe275zhang.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sjsu.cmpe275zhang.airlinereservation.Passenger;
import com.sjsu.cmpe275zhang.services.PassengerService;

@Controller
public class XmlController {
	@ResponseBody
	@RequestMapping(value = "/passengerw/{id}",params = {"xml"}, method = RequestMethod.GET, produces="application/xml")
	public Passenger getPassenger(@PathVariable("id") String id,@RequestParam("xml") String xml,ModelAndView model,HttpServletResponse response) throws Exception
	{
		    response.setContentType("application/xml");
		    PassengerService ps=new PassengerService();
		    Passenger p=ps.getPassenger(id);
		    return p;
	}
}
