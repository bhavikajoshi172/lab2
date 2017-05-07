package com.sjsu.cmpe275zhang.airlinereservation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "flight")
public class Flight implements Serializable{
	@Id
	private String number; // Each flight has a unique flight number.
	private int price;
	@Column(name="fromport")
    private String from;
    @Column(name="toport")
    private String to;
	/*
	 * Date format: yy-mm-dd-hh, do not include minutes and sceonds. Example:
	 * 2017-03-22-19
	 */
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date departureTime;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date arrivalTime;
	private int seatsLeft;
	private String description;
	@Embedded
	private Plane plane; // Embedded
	@OneToMany
	@JsonBackReference
	private List<Passenger> passengers;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	 public String getFrom() {
	        return from;
	    }
	 
	    public void setFrom(String from) {
	        this.from = from;
	    }
	 
	    public String getTo() {
	        return to;
	    }
	 
	    public void setTo(String to) {
	        this.to = to;
	    }

	public Date getDepartureTime() {
		return departureTime;
	}
	@JsonFormat(locale = "hu", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "PST")
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	public Date getArrivalTime() {
		return arrivalTime;
	}
	
	@JsonFormat(locale = "hu", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "PST")
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	@Override
	public boolean equals(Object object)
	{
		if(object instanceof Flight && ((Flight)object).getNumber()==this.getNumber())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
