package myshop.repository;


import java.util.List;

import myshop.domain.persistence.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
public interface ProductRepository extends JpaRepository<Product, String> {
	Product findByLineNumber(int lineNumber);
	List<Product> findByLongnameLikeIgnoreCase(String longname);
}
