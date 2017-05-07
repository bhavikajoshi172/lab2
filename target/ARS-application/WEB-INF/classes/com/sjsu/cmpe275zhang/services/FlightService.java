package com.sjsu.cmpe275zhang.services;

import java.sql.Date;
import java.util.List;

import com.sjsu.cmpe275zhang.airlinereservation.DefaultException;
import com.sjsu.cmpe275zhang.airlinereservation.Flight;
import com.sjsu.cmpe275zhang.airlinereservation.Passenger;
import com.sjsu.cmpe275zhang.dao.FlightDao;
import com.sjsu.cmpe275zhang.dao.PassengerDao;;

public class FlightService 
{

	public Flight getFlight(String flight_number) throws Exception
	{
		FlightDao f=new FlightDao();
		return f.getFlight(flight_number);
	}
	
	public List<Passenger> getPassengerByFlight(String flight_number) throws Exception
	{
		FlightDao f=new FlightDao();
		return f.getPassengerByFlight(flight_number);
	}
	public Flight addFlight(String number, int price, String toport, String fromport, String dtime, String atime, String desc, int cap, String model, String manu, int years) throws Exception
	{
		FlightDao f=new FlightDao();

		return f.addFlight(number, price, toport, fromport, dtime, atime, desc, cap, model, manu, years);
	}
	

	public void deleteFlight(String fid) throws DefaultException
	{
		FlightDao f=new FlightDao();
		f.deleteFlight(fid);
		return ;
	}
	
}
