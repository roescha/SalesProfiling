package com.bjss.waitrose.repository;

import java.util.Collection;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bjss.waitrose.domain.persistence.Store;

@Transactional(readOnly=true)
public interface StoreRepository extends JpaRepository<Store, String> {
	public Store findByNumber(int number);
	public List<Store> findByNumberIn(Collection<Integer> storeIds);
}

