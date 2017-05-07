package com.sjsu.cmpe275zhang.airlinereservation;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight_reservation")
public class FlightReservation {
@Id
private int id;
private String reservation_number;
private String flight_number;
public String getReservation_number() {
	return reservation_number;
}
public void setReservation_number(String reservation_number) {
	this.reservation_number = reservation_number;
}
public String getFlight_number() {
	return flight_number;
}
public void setFlight_number(String flight_number) {
	this.flight_number = flight_number;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
}
