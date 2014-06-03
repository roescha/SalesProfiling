package com.bjss.waitrose.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bjss.waitrose.domain.persistence.Product;
import com.bjss.waitrose.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll(new Sort(Sort.Direction.DESC, "longname"));
	}

	@Override
	public long getProductsCount() {
		return productRepository.count();
	}

	@Override
	public Product getProductDetails(int lineNumber) {
		return productRepository.findByLineNumber(lineNumber);
	}

	@Override
	public List<Product> getProductsByLongnameLike(String longname) {
		return productRepository.findByLongnameLikeIgnoreCase("%" + longname +"%" );
	}

}
