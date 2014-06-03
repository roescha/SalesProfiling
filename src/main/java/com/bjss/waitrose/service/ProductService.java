package com.bjss.waitrose.service;

import java.util.List;

import com.bjss.waitrose.domain.persistence.Product;

public interface ProductService {
	List<Product> getAllProducts();	
	List<Product> getProductsByLongnameLike(String longname);
	long getProductsCount();
	Product getProductDetails(int lineNumber);
}
