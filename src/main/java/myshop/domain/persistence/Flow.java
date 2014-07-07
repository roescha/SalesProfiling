package myshop.domain.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import myshop.domain.DataPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "linedata")
public class Flow{
	private static final Logger Log = LoggerFactory.getLogger(Flow.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "line_num")
	protected int lineNumber;

	@Column(name = "store")
	protected int storeNumber;

	@Column(name = "date")
	protected Date date;

	@Column(name = "total")
	protected int total;

	@Column(name = "day")
	protected String day;

 @Column(name = "hour0")
	private Integer zero_0;
	@Column(name = "hour1")
	private Integer one_1;
	@Column(name = "hour2")
	private Integer two_2;
	@Column(name = "hour3")
	private Integer three_3;
	@Column(name = "hour4")
	private Integer four_4;
	@Column(name = "hour5")
	private Integer five_5;
	@Column(name = "hour6")
	private Integer six_6;
	@Column(name = "hour7")
	private Integer seven_7;
	@Column(name = "hour8")
	private Integer eight_8;
	@Column(name = "hour9")
	private Integer nine_9;
	@Column(name = "hour10")
	private Integer ten_10;
	@Column(name = "hour11")
	private Integer eleven_11;
	@Column(name = "hour12")
	private Integer twelve_12;
	@Column(name = "hour13")
	private Integer thirteen_13;
	@Column(name = "hour14")
	private Integer fourteen_14;
	@Column(name = "hour15")
	private Integer fifteen_15;
	@Column(name = "hour16")
	private Integer sixteen_16;
	@Column(name = "hour17")
	private Integer seventeen_17;
	@Column(name = "hour18")
	private Integer eighteen_18;
	@Column(name = "hour19")
	private Integer nineteen_19;
	@Column(name = "hour20")
	private Integer twenty_20;
	@Column(name = "hour21")
	private Integer twentyone_21;
	@Column(name = "hour22")
	private Integer twentytwo_22;
	@Column(name = "hour23")
	private Integer twentythree_23;
	
	public List<DataPoint> getHourflyFlow() {
		List<DataPoint> hourlyflows = new ArrayList<>(24);
		
		if (hourlyflows.isEmpty()) {

			java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();

			for (java.lang.reflect.Field field : fields) {

				if (field.getName().contains("_")) {

					try {
						int hour = Integer.parseInt(field.getName().substring(field.getName().indexOf("_") + 1));
						Integer value = (Integer) field.get(this);
						if (hour == 0) {
							hour = 24;
						}

						DataPoint point = new DataPoint(hour);
						point.addValue(value == null ? 0 : value);
						hourlyflows.add(point);

					} catch (IllegalArgumentException | IllegalAccessException e) {
						Log.error("Error getting hour values", e);
					}
				}
			}
		}
		return hourlyflows;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getStoreNumber() {
		return storeNumber;
	}
	
	public void setStoreNumber(int storeNumber){
		this.storeNumber = storeNumber;
	}

	public Date getDate() {
		return date;
	}

	public int getTotal() {
		return total;
	}

	public String getDay() {
		return day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + storeNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flow other = (Flow) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (storeNumber != other.storeNumber)
			return false;
		return true;
	}
	
	
}
