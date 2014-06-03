package com.bjss.waitrose.util;

import java.util.ArrayList;
import java.util.List;

import com.bjss.waitrose.domain.persistence.Flow;
import com.bjss.waitrose.domain.persistence.Store;

public class StoreUtil {

	public static List<Store> createStores(int howMany) {
		List<Store> stores = new ArrayList<>();
		for (int i = 0; i < howMany; i++) {
			stores.add(new Store(i, "name" + i));
		}
		return stores;
	}

	public static List<Store> createStoresFromStoreProduct(List<Flow> storeProducts) {
		List<Store> stores = new ArrayList<>();
		for (Flow sp : storeProducts){
			Store store = new Store(sp.getStoreNumber(), "name" + sp.getStoreNumber());
			stores.add(store);
		}
		return stores;
	}

}
