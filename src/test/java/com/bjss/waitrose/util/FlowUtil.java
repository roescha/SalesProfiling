package com.bjss.waitrose.util;

import java.util.ArrayList;
import java.util.List;

import com.bjss.waitrose.domain.persistence.Flow;

public class FlowUtil {

	public static List<Flow> createStoreProduct(int howMany) {
		List<Flow> list = new ArrayList<>();
		for (int i = 1; i <= howMany; i++) {
			Flow storeProduct = new Flow();
			//.setLineNumber(i);
			storeProduct.setStoreNumber(i*2 + 13);
			list.add(storeProduct);
		}
		return list;
	}

}
