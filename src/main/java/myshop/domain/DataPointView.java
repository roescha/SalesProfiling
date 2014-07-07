package myshop.domain;

public class DataPointView  implements Comparable<DataPointView> {
	private int sum;
	private double average;
	private int pointInTime;
	private String label;
	
	
	public DataPointView(int pointInTime, int sum, double average, String label) {
		this.sum = sum;
		this.average = average;
		this.label = label;
		this.pointInTime = pointInTime;
	}
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}

	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public int getHour() {
		return pointInTime;
	}
	public void setHour(int hour) {
		this.pointInTime = hour;
	}
	
	public int getPointInTime() {
		return pointInTime;
	}
	
	public String getLabel(){
		return  label;
	}
	
	@Override
	public int compareTo(DataPointView other) {
		if (other.getPointInTime() == this.getPointInTime())
			return 0;
		if (this.getPointInTime() > other.getPointInTime())
				return 1;
		return -1;
	}

}
