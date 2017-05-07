package com.sjsu.cmpe275zhang.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sjsu.cmpe275zhang.airlinereservation.*;

@Repository
public class PassengerDao {

	@PersistenceContext
	private EntityManagerFactory factory;

	public Passenger getPassenger(String pid) throws Exception {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT p FROM Passenger p where p.id=\"" + pid + "\"");
		List<Passenger> userList = q.getResultList();
		for (Passenger user : userList) {
			System.out.println(user.getFirstname());
		}
		if(userList.size()==0)
		{
			throw new Exception();
		}
		em.close();
		return userList.get(0);
	}

	public Passenger addPassenger(String firstname, String lastname, int age, String gender, String phone) throws DefaultException {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		
		Query q1 = em.createQuery("SELECT p.phone FROM Passenger p ");
		List<Passenger> phone_nums = q1.getResultList();
		System.out.println(phone_nums);
		
		if(phone_nums.contains(phone))
		{
			
				throw new DefaultException(404, "Another passenger with same phone number already exists");
			
		}
		else
		{

		Query q = em.createQuery("SELECT p FROM Passenger p");
		List<Passenger> userList = q.getResultList();
		String id = "";
		int pid = 1;
		for (Passenger user : userList) 
		{
			System.out.println(user.getFirstname());
			id = user.getId();
			
		}
		if (!(id == "")) {
			pid = Integer.parseInt(id);
			
			pid++;
		}
		
		
		em.getTransaction().begin();
		Passenger p = new Passenger();
		p.setId("" + pid);
		p.setFirstname(firstname);
		p.setLastname(lastname);
		p.setAge(age);
		p.setGender(gender);
		p.setPhone(phone);

		em.persist(p);
		em.getTransaction().commit();

		Query q2 = em.createQuery("SELECT p FROM Passenger p where p.id=\"" + pid + "\"");
		List<Passenger> userList2 = q2.getResultList();
		em.close();
		return userList2.get(0);
		}
	}

	public Passenger updatePassenger(String pid, String firstname, String lastname, int age, String gender,
			String phone) {
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("SELECT p FROM Passenger p where p.id=\"" + pid + "\"");
		List<Passenger> userList = q.getResultList();

		for (Passenger p : userList) {
			em.getTransaction().begin();
			System.out.println(p.getFirstname());
			p.setFirstname(firstname);
			p.setLastname(lastname);
			p.setAge(age);
			p.setGender(gender);
			p.setPhone(phone);
			em.getTransaction().commit();
		}

		Query q2 = em.createQuery("SELECT p FROM Passenger p where p.id=\"" + pid + "\"");
		List<Passenger> userList2 = q2.getResultList();
		em.close();
		return userList2.get(0);

	}

	public void deletePassenger(String id) 
	{
		factory = Persistence.createEntityManagerFactory("ARS");
		EntityManager em = factory.createEntityManager();
		
		Passenger p = em.find(Passenger.class, id);
		FlightDao flightDao=new FlightDao();
		List<Flight> fl=flightDao.addFlightCapacity(p.getId());
		
		flightDao.getFlightByPassenger(id,p);
		List<Reservation> r=p.getReservation();
		em.getTransaction().begin();
		p.setReservation(null);
		em.getTransaction().commit();
		
		for(Reservation rs:r)
		{
			em.getTransaction().begin();
		em.remove(rs);
		em.getTransaction().commit();
		}
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();
		em.close();
	}
}