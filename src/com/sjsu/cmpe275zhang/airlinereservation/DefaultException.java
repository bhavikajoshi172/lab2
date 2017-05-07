package com.sjsu.cmpe275zhang.airlinereservation;

public class DefaultException extends Exception
{
	
	public DefaultException(int code, String message) 
	{
		this.code=code;
		this.message=message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	private String message;
	private int code;
	

}
