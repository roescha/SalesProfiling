package com.bjss.waitrose.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjss.waitrose.domain.persistence.Store;
import com.bjss.waitrose.repository.FlowRepository;
import com.bjss.waitrose.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService {
	private final StoreRepository storeRepository;
	private final FlowRepository flowRepository;

	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository, FlowRepository flowRepository) {
		this.storeRepository = storeRepository;
		this.flowRepository = flowRepository;
	}

	@Override
	public List<Store> getAllStores() {
		return storeRepository.findAll();
	}

	@Override
	public long getStoresCount() {
		return storeRepository.count();
	}

	@Override
	public Store getStoreByStoreNumber(int number) {
		return storeRepository.findByNumber(number);
	}

	@Override
	public List<Store> getStoresForIds(Collection<Integer> storeIds) {
		return storeRepository.findByNumberIn(storeIds);
	}

	@Override
	public List<Integer> getStoreIdsForProduct(int lineNumber) {
		return flowRepository.findStoreNumbersForProduct(lineNumber);
	}
}
