package com.sjsu.cmpe275zhang.services;

import java.util.List;

import com.sjsu.cmpe275zhang.airlinereservation.DefaultException;
import com.sjsu.cmpe275zhang.airlinereservation.Reservation;
import com.sjsu.cmpe275zhang.dao.ReservationDao;

public class ReservationService {
public Reservation getReservation(String ordernumber) throws Exception
{
ReservationDao p=new ReservationDao();

return p.getReservation(ordernumber);
}
public Reservation addReservation(String passengerid, String flights) throws DefaultException
{
ReservationDao p=new ReservationDao();

return p.addReservation(passengerid,flights);
}
public Reservation updateReservation(String ordernumber,String flightsAdded, String flightsRemoved)
{
ReservationDao p=new ReservationDao();

return p.updateReservation(ordernumber,flightsAdded,flightsRemoved);
}
public void deleteReservation(String id) {
	ReservationDao r=new ReservationDao();
	r.deleteReservation(id);
	return ;
}

public List<Reservation> searchReservation(String pid, String toport, String fromport, String fid)
{
	ReservationDao p=new ReservationDao();
	return p.searchReservation(pid, toport, fromport, fid);

}

}
