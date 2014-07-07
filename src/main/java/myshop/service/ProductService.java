package myshop.service;

import java.util.List;

import myshop.domain.persistence.Product;

public interface ProductService {
	List<Product> getAllProducts();	
	List<Product> getProductsByLongnameLike(String longname);
	long getProductsCount();
	Product getProductDetails(int lineNumber);
}
