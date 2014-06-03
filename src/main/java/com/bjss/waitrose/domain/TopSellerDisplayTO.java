package com.bjss.waitrose.domain;

public class TopSellerDisplayTO {
		private final long lineNumber;
		private final String name;
		private final int salesForPeriod;
		private final int numOffSale;
		
		public TopSellerDisplayTO(long lineNum, String name, int salesForPeriod, int numOffSale) {
			this.lineNumber = lineNum;
			this.name = name;
			this.salesForPeriod = salesForPeriod;
			this.numOffSale = numOffSale;
		}
		
		public long getLineNumber() {
			return lineNumber;
		}
		
		public String getName() {
			return name;
		}
		
		public int getSalesForPeriod() {
			return salesForPeriod;
		}

		public int getNumOffSale() {
			return numOffSale;
		}
		
}
