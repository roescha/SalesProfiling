package myshop.repository;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import myshop.domain.persistence.Flow;

import org.springframework.cache.annotation.Cacheable;

import com.google.common.collect.Sets;

public class FlowRepositoryImpl implements CustomFlowRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Cacheable("flows")
	@SuppressWarnings("unchecked")
	public Set<Flow> findByLineNumberAndStoreNumberAndDateBetween(int lineNumber,
			int storeNumber,
			Date from,
			Date to) {

		Query query = entityManager
				.createQuery("from Flow where lineNumber = ?1 and storeNumber = ?2 and date between ?3 and ?4");
		query.setParameter(1, lineNumber).setParameter(2, storeNumber).setParameter(3, from).setParameter(4, to).setMaxResults(1500);

		return Sets.<Flow>newHashSet(query.getResultList());
	}

	@Cacheable("flow")	
	@Override
	public Flow findByLineNumberAndStoreNumberAndDate(int lineNumber, int storeNumber, Date date) {
		Query query = entityManager
				.createQuery("from Flow where lineNumber = ?1 and storeNumber = ?2 and date = ?3");
		query.setParameter(1, lineNumber).setParameter(2, storeNumber).setParameter(3, date).setMaxResults(1);
		Flow flow;
		if (!query.getResultList().isEmpty())
			flow = (Flow) query.getResultList().get(0);
		else {
			flow = new Flow();
		}
		return flow;
	}

	@Cacheable("range")
	@SuppressWarnings("unchecked")
	@Override
	public Set<Flow> findByLineNumberAndDateBetween(int lineNumber, Date from, Date to, int pageNumber) {
	
		Query query = entityManager
				.createQuery("from Flow where lineNumber = ?1 and date between ?2 and ?3");
		query.setParameter(1, lineNumber).setParameter(2, from).setParameter(3, to).setMaxResults(65000).setFirstResult(pageNumber *65000);
		query.getResultList();
		
	  return Sets.<Flow>newHashSet(query.getResultList());
	}
}
