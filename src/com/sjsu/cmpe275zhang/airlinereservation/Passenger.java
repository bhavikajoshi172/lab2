package com.sjsu.cmpe275zhang.airlinereservation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "passenger")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Passenger implements Serializable {
	@Id
	private String id;
	 @JsonView(Views.Public.class)
	private String firstname;
	 @JsonView(Views.Public.class)
	private String lastname;
	 @JsonView(Views.Public.class)
	private int age;
	 @JsonView(Views.Public.class)
	private String gender;
	 @JsonView(Views.Public.class)
	private String phone; // Phone numbers must be unique
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "passenger")
	@JsonView(Views.Internal.class)
	private List<Reservation> reservation;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}
	 @Override
	    public boolean equals(Object o) {
	 
	        // If the object is compared with itself then return true  
	        if (o == this) {
	            return true;
	        }
	 
	        /* Check if o is an instance of Complex or not
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof Passenger)) {
	            return false;
	        }
	        else{
	        	if(((Passenger)o).getId()==this.getId())
	        	{
	        		return true;
	        	}
	        }
			return false;
	         
	      
	    }
	
	 
}
