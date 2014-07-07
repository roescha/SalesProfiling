package myshop.domain;

public class OffSaleDetailTo {
	
	private final int storeId;
	private final int lineNum;
	private final String lineName;
	private final String date;
	private final String day;
	private final String storeName;
	private final String rdcName;
	
	public OffSaleDetailTo(int storeId, int lineNum, String lineName, String date, String day, String storeName, String rdcName) {
		this.storeId = storeId;
		this.lineNum = lineNum;
		this.lineName = lineName;
		this.date = date;
		this.day = day;
		this.storeName = storeName;
		this.rdcName = rdcName;
	}

	public int getStoreId() {
		return storeId;
	}

	public int getLineNum() {
		return lineNum;
	}

	public String getLineName() {
		return lineName;
	}

	public String getDate() {
		return date;
	}

	public String getDay() {
		return day;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getRdcName() {
		return rdcName;
	}

}
