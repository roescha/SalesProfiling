package com.bjss.waitrose.domain;

public class OffSaleDisplayTO {
	private final long id;
	private final String name;
	private final double latitude;
	private final double longitude;
	private final int noOffsales;
	
	public OffSaleDisplayTO(long id, String name, double latitude, double longitude, int noOffsales) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.noOffsales = noOffsales;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getNoOffsales() {
		return noOffsales;
	}
	
}
