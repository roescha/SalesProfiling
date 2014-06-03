package com.bjss.waitrose.domain;

public class DataPoint  {

	private int pointInTime;
	private int sum;
	private int pointsCount;

	public DataPoint(int pointInTime) {
		this.pointInTime = pointInTime;
	}

	public void addValue(int value) {
		if (pointsCount == 0) {
			sum = value;
		} else {
			sum += value;
		}
		++pointsCount;
	}
	
	public void addValueAndCount(int value, int count) {
		if (pointsCount == 0) {
			sum = value;
		} else {
			sum += value;
		}
		pointsCount += count;
	}
	
	public void addValueAndFixCount(int value, int count) {
		if (pointsCount == 0) {
			sum = value;
		} else {
			sum += value;
		}
		pointsCount = count;
	}

	public int getSum() {
		return sum;
	}


	public double getAverage() {
		double avg = pointsCount > 0 ? sum / (pointsCount) : 0;
		return Math.floor(avg * 100) / 100;
	}

	public int getPointInTime() {
		return pointInTime;
	}
	
	public void setPointsCount(int pointsCount){
		this.pointsCount = pointsCount;
	}
	
	public int getPointsCount(){
return this.pointsCount;
	}

}
