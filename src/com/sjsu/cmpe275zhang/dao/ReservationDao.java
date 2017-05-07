package com.sjsu.cmpe275zhang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275zhang.airlinereservation.*;

@Repository
public class ReservationDao {

	@PersistenceContext
	private EntityManagerFactory factory;

	public Reservation getReservation( String pid) {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT p FROM Reservation p where p.orderNumber=\"" + pid+"\"");
		List<Reservation> userList = q.getResultList();
		for (Reservation user : userList) {
			System.out.println(user.getOrderNumber());
		}
		System.out.println("Size: " + userList.size());

		// Create new user
		// em.getTransaction().begin();
		// Reservation user = new Reservation();
		// user.setName("Tom Johnson");
		// user.setLogin("tomj");
		// user.setPassword("pass");
		// em.persist(user);
		// em.getTransaction().commit();

		em.close();
		return userList.get(0);
	}

	public Reservation addReservation(String passengerid, String flights) throws DefaultException {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		
		
		try
		{

		Query q = em.createQuery("SELECT r FROM Reservation r");
		List<Reservation> reservationList = q.getResultList();
		String id = "";
		int pid = 1;
		for (Reservation reservation : reservationList) {
			
			id = reservation.getOrderNumber();
			
		}
		
		if (!(id == "")) 
		{
			
			pid = Integer.parseInt(id);
			pid++;
		}
		int price=0;
		
		Query q2 = em.createQuery("SELECT p FROM Passenger p where p.id=\"" + passengerid+"\"");
		List<Passenger> userList2 = q2.getResultList();
		if(userList2.size()==0)
		{
			throw new DefaultException(404, "No such passenger exists");
		}
		String[] arr=flights.split(",");
		flights="";
		int count=1;
	
		
		for(String s:arr)
		{
			System.out.println("s"+s);
			flights=flights+"'"+s+"'";
			Query q5 = em.createQuery("SELECT f.seatsLeft FROM Flight f where f.number=\"" + s+"\"");
			List<Passenger> userSeats = q5.getResultList();
			System.out.println("with left seats" +userSeats);
			
			if(userSeats.contains(0))
			{
				throw new DefaultException(404,"The total amount of passengers can not exceed the capacity of the reserved plane");
			}
			
			if(count<arr.length)
			{		
				flights=flights+",";
				count++;
			}		
		}
			
		
		Query q3 = em.createQuery("SELECT f FROM Flight f where f.number in (" + flights+")");
		List<Flight> flightList = q3.getResultList();
		if(flightList.size()==0)
		{
			throw new DefaultException(404,"");
		}
		for(Flight f:flightList)
		{
			em.getTransaction().begin();
			List<Passenger> lp=f.getPassengers();
			lp.add(userList2.get(0));
			f.setPassengers(lp);
			em.getTransaction().commit();
			price+=f.getPrice();
			
		}
		em.getTransaction().begin();
		
		
		
		Reservation p = new Reservation();
		p.setOrderNumber(""+pid);
		
		
		
		
		
		p.setPassenger(userList2.get(0));
		p.setPrice(price);
		p.setFlights(flightList);
	
		em.persist(p);
		em.getTransaction().commit();

		Query q4 = em.createQuery("SELECT r FROM Reservation r where r.orderNumber=\"" + pid+"\"");
		List<Reservation> rList = q4.getResultList();
		System.out.println("I in int am pid" + Integer.toString(pid));
		
		
		em.getTransaction().begin();
		FlightDao fd = new FlightDao();
		System.out.println("Id from res dao" +Integer.toString(pid));
		
		fd.reduceFlightCapacityOnReservation(Integer.toString(pid));
		em.persist(p);
		em.getTransaction().commit();
	
		
		em.close();
		return rList.get(0);
		}
		catch(Exception e)
		{
			throw new DefaultException(404,"Couldnot add reservation");
		}
		
		
	}
	
	public Reservation updateReservation(String ordernumber,String flightsAdded, String flightsRemoved) {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		String[] arr=flightsAdded.split(",");
		flightsAdded="";
		int count=1;
		for(String s:arr)
		{
			flightsAdded=flightsAdded+"'"+s+"'";
			if(count<arr.length)
			{
				flightsAdded=flightsAdded+",";
				count++;
			}
		}
		String[] arr2=flightsRemoved.split(",");
		flightsRemoved="";
		int count2=1;
		for(String s:arr2)
		{
			flightsRemoved=flightsRemoved+"'"+s+"'";
			if(count2<arr2.length)
			{
				flightsRemoved=flightsRemoved+",";
				count2++;
			}
		}
		Query q = em.createQuery("SELECT p FROM Reservation p where p.orderNumber=\"" + ordernumber+"\"");
		List<Reservation> rList = q.getResultList();
		Query q2 = em.createQuery("SELECT f FROM Flight f where f.number in (" + flightsRemoved+")");
		List<Flight> flightsRemovedList = q2.getResultList();
		Query q3 = em.createQuery("SELECT f FROM Flight f where f.number in (" + flightsAdded+")");
		List<Flight> flightsAddedList = q3.getResultList();
		

		
		for (Reservation r : rList) {
			em.getTransaction().begin();
			List<Flight> flights=r.getFlights();
			flights.removeAll(flightsRemovedList);
			flights.addAll(flightsAddedList);
			em.getTransaction().commit();
		}

		Query q4 = em.createQuery("SELECT r FROM Reservation r where r.orderNumber=\"" + ordernumber+"\"");
		List<Reservation> rList2 = q4.getResultList();
		em.close();
		return rList2.get(0);

	}
	public void deleteReservation(String id) 
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		FlightDao fd = new FlightDao();
		System.out.println("Id from res dao" +id);
		fd.addFlightCapacityOnReservation(id);
		
		Reservation r=em.find(Reservation.class, id);
		FlightDao flightDao=new FlightDao();
		flightDao.getFlightByReservation(r.getOrderNumber(), r);
		
		 em.remove(r);
		 em.getTransaction().commit();
		 em.close();
		 
	}
	public List<Reservation> searchReservation(String pid, String toport, String fromport, String fid)
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		String query="SELECT p FROM Reservation p, Flight f "+
		"WHERE f MEMBER OF p.flights";
	
		if(pid!=null)
		{
			query += " and p.passenger.id=\"" + pid+"\"";
			
		}
		if(toport!=null)
		{
			
			query+=" and f.toport=\"" + toport+"\"";
		
		}
		if(fromport!=null)
		{
			
			query+=" and f.fromport=\"" + fromport+"\"";
		}
		if(fid!=null)
		{
			
			query+="and f.number=\"" + fid+"\"";
		}
		System.out.println(query);
		Query q = em.createQuery(query);
		List<Reservation> userList = q.getResultList();
		
		

		em.close();
		return userList;
	}
	

}