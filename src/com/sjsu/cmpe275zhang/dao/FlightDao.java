package com.sjsu.cmpe275zhang.dao;



import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275zhang.airlinereservation.*;

@Repository
public class FlightDao 
{
	
	@PersistenceContext
	private EntityManagerFactory factory;

	public Flight getFlight( String fid) throws Exception
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f where f.number=\"" + fid+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			System.out.println(user.getPrice());
		}
		System.out.println("Size: " + userList.size());
		em.close();
		return userList.get(0);

	}
	
	public Flight addFlight(String number, int price, String toport, String fromport, String dtime, String atime, String desc, int capacity, String model1, String manu, int years) throws DefaultException 
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		Date date1=null;
		Date date2 = null;
		
		try {
			 date1 = new SimpleDateFormat("yyyy-MM-dd-hh").parse(atime);
			 date2 = new SimpleDateFormat("yyyy-MM-dd-hh").parse(dtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Query q1 = em.createQuery("SELECT f.number FROM Flight f ");
		List<Flight> flight_ids = q1.getResultList();
		
		System.out.println("from flightDaooooo" +flight_ids);
		
		
			if(flight_ids.contains(number))
			{
				Query q_update = em.createQuery("SELECT f FROM Flight f where f.number=\"" + number+"\"");
				List<Flight> userList = q_update.getResultList();
				
				for (Flight f : userList) 
				{
					em.getTransaction().begin();
					System.out.println(f.getPrice());
					f.setPrice(price);
					f.setFrom(fromport);
					f.setTo(toport);
					f.setDepartureTime(date2);
					f.setArrivalTime(date1);
					f.setSeatsLeft(capacity);
					f.setDescription(desc);
					Plane p= new Plane();
					p.setCapacity(capacity);
					p.setManufacturer(manu);
					p.setModel(model1);
					p.setYearOfManufacture(years);
					f.setPlane(p);
					em.getTransaction().commit();
				}
			}
			else
			{
				Query q_create = em.createQuery("SELECT f FROM Flight f");
				List<Flight> userList = q_create.getResultList();
				
				em.getTransaction().begin();
				
				Flight f = new Flight();
				f.setNumber(number);
				f.setPrice(price);
				f.setFrom(fromport);
				f.setTo(toport);
				f.setDepartureTime(date2);
				f.setArrivalTime(date1);
				f.setSeatsLeft(capacity);
				f.setDescription(desc);
				Plane p= new Plane();
				p.setCapacity(capacity);
				p.setManufacturer(manu);
				p.setModel(model1);
				p.setYearOfManufacture(years);
				f.setPlane(p);
				

				em.persist(f);
				em.getTransaction().commit();
			}
		
			Query q2 = em.createQuery("SELECT f FROM Flight f where f.number=\"" + number+"\"");
			List<Flight> userList2 = q2.getResultList();
			em.close();
			return userList2.get(0);
		
	//	for (Flight user : userList) {
	//		System.out.println(user.getPrice());
			
//		}
		
		
	}
	
	
	
	public void deleteFlight(String id) throws DefaultException {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		
		
		FlightDao ff = new FlightDao();
		List<Passenger> flightsize = ff.getPassengerByFlight(id);
		System.out.println(flightsize.size());
		
		
		Query q1 = em.createQuery("SELECT f.number FROM Flight f ");
		List<Flight> flight_id = q1.getResultList();
		System.out.println(flight_id);
		
		if(flight_id.contains(id))
		{
			
			if(flightsize.size()>0)
			{
				throw new DefaultException(400, "You can not delete a flight " +id+  " that has one or more reservation");
			}
			else if(flightsize.size()==0)
			{
				Flight f=em.find(Flight.class, id);
				em.remove(f);
				em.getTransaction().commit();
				em.close();
			}
			
			
			
		}
		else
		{
			throw new DefaultException(404,"Flight with id "+id+" does not exist");
		}
		
		}
		
		
		
	
	
	public List<Flight> addFlightCapacity(String id)
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f,Passenger p "+
				"WHERE p MEMBER OF f.passengers and p.id=\""+id+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			System.out.println(user.getPrice());
			
				em.getTransaction().begin();
				user.setSeatsLeft(user.getSeatsLeft()+1);
				em.getTransaction().commit();
			
		}
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
	
	
	
	
	public List<Flight> reduceFlightCapacityOnReservation(String id)
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f,Reservation p "+
				"WHERE f MEMBER OF p.flights and p.orderNumber=\""+id+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			System.out.println(user.getPrice());
			
				em.getTransaction().begin();
				user.setSeatsLeft(user.getSeatsLeft()-1);
				em.getTransaction().commit();
			
		}
		
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
	public List<Flight> addFlightCapacityOnReservation(String id)
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f,Reservation p "+
				"WHERE f MEMBER OF p.flights and p.orderNumber=\""+id+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			System.out.println(user.getPrice());
			
				em.getTransaction().begin();
				user.setSeatsLeft(user.getSeatsLeft()+1);
				em.getTransaction().commit();
			
		}
		
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
	public List<Passenger> getPassengerByFlight(String fid) {

		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT p FROM Flight f,Passenger p where p MEMBER OF f.passengers and f.number=\"" + fid+"\"");
		List<Passenger> userList = q.getResultList();
		for (Passenger user : userList) 
		{
			System.out.println(user.getFirstname());
		}
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
	
	
	
	
	
	public List<Flight> getFlightByPassenger(String fid,Passenger p) {

		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f,Passenger p where p MEMBER OF f.passengers and p.id=\"" + fid+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			em.getTransaction().begin();
			List<Passenger> pass=user.getPassengers();
			pass.remove(p);
			user.setPassengers(pass);
			em.getTransaction().commit();
		}
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
	public List<Flight> getFlightByReservation(String fid,Reservation r) {

		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT f FROM Flight f,Reservation r where f MEMBER OF r.flights and r.orderNumber=\"" + fid+"\"");
		List<Flight> userList = q.getResultList();
		for (Flight user : userList) 
		{
			em.getTransaction().begin();
			List<Passenger> pass=user.getPassengers();
			pass.remove(r.getPassenger());
			user.setPassengers(pass);
			em.getTransaction().commit();
		}
		System.out.println("Size: " + userList.size());
		em.close();
		return userList;
	}
}
