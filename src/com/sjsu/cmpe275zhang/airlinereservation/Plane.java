package com.sjsu.cmpe275zhang.airlinereservation;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable

public class Plane implements Serializable{
	private int capacity;
	private String model;
	private String manufacturer;
	private int yearOfManufacture;

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

}
