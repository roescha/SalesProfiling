package myshop.domain;

public class StoreAndProduct {
	private final int lineNumber;
	private final int storeNumber;
	
	private final int ALL_STORES = 0;

	public StoreAndProduct(int lineNumber, int storeNumber) {
		this.lineNumber = lineNumber;
		this.storeNumber = storeNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getStoreNumber() {
		return storeNumber;
	}
	
	public boolean forAllStores(){
		return storeNumber == ALL_STORES;
	}

	@Override
	public String toString() {
		return "StoreAndProduct [lineNumber=" + lineNumber + ", storeNumber=" + storeNumber + "]";
	}

}
