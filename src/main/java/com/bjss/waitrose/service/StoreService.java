package com.bjss.waitrose.service;

import java.util.Collection;
import java.util.List;

import com.bjss.waitrose.domain.persistence.Store;

public interface StoreService {
	
	List<Store> getAllStores();

	List<Store> getStoresForIds(Collection<Integer> storeIds);

	long getStoresCount();

	Store getStoreByStoreNumber(int storeNumber);

	List<Integer> getStoreIdsForProduct(int lineNumber);
}
