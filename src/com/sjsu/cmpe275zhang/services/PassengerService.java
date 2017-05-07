package com.sjsu.cmpe275zhang.services;

import com.sjsu.cmpe275zhang.airlinereservation.DefaultException;
import com.sjsu.cmpe275zhang.airlinereservation.Passenger;
import com.sjsu.cmpe275zhang.dao.PassengerDao;

public class PassengerService {
public Passenger getPassenger(String pid) throws Exception
{
PassengerDao p=new PassengerDao();

return p.getPassenger(pid);
}
public Passenger addPassenger(String firstname, String lastname, int age, String gender, String phone) throws DefaultException
{
PassengerDao p=new PassengerDao();

return p.addPassenger(firstname,lastname,age,gender,phone);
}
public Passenger updatePassenger(String id,String firstname, String lastname, int age, String gender, String phone)
{
PassengerDao p=new PassengerDao();

return p.updatePassenger(id,firstname,lastname,age,gender,phone);
}
public void deletePassenger(String id) {
	PassengerDao p=new PassengerDao();
	p.deletePassenger(id);
	return ;
}
}
