package myshop.repository;

import java.util.Collection;
import java.util.List;

import myshop.domain.persistence.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
public interface StoreRepository extends JpaRepository<Store, String> {
	public Store findByNumber(int number);
	public List<Store> findByNumberIn(Collection<Integer> storeIds);
}

