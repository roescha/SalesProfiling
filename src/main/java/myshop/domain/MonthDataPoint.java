package myshop.domain;

import java.util.Set;

public class MonthDataPoint extends DataPoint {
	Set<Integer> storeIds;

	public MonthDataPoint(int pointInTime) {
		super(pointInTime);
	}
	
	public void setStoreIds(Set<Integer> ids){
		this.storeIds = ids;
	}
	
	public Set<Integer> getStoreIds(){
		return storeIds;
	}
	

}
