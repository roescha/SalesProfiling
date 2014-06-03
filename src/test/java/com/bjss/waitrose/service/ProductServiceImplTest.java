package com.bjss.waitrose.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.bjss.waitrose.domain.persistence.Product;
import com.bjss.waitrose.repository.ProductRepository;
import com.bjss.waitrose.util.ProductUtil;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	private ProductService productService;

	@Before
	public void setUp() throws Exception {
		productService = new ProductServiceImpl(productRepository);
	}

	@Test
	public void shouldFindAllProducts_GivenThereAreSome() {
		stubRepositoryToReturnExistingProducts(10);
		List<Product> products = productService.getAllProducts();
		assertNotNull(products);
		assertEquals(products.size(), 10);
		verify(productRepository, times(1)).findAll(new Sort(Sort.Direction.DESC, "longname"));
	}
	
	@Test
	public void shouldFindProductDetails_GivenTheProductExists() {
		int lineNumber = 123;
		stubRepositoryToReturnExistingProductDetails(lineNumber);
		Product product = productService.getProductDetails(lineNumber);
		assertNotNull(product);
		assertEquals(product.getLineNumber(), lineNumber);
		verify(productRepository, times(1)).findByLineNumber(lineNumber);
	}
	
	@Test
	public void shouldFindProductsWithNameLike_GivenSimilarNamesExist() {
		String nameLike = "milk";
		stubRepositoryToReturnExistingProductNamesLike(nameLike, 2);
		List<Product> products = productService.getProductsByLongnameLike(nameLike);
		assertNotNull(products);
	//	assertEquals(products.size(), 2);
//		verify(productRepository, times(1)).findByLongnameLikeIgnoreCase(nameLike);	
	}
	
	@Test
	public void shouldReturnProductCount() {
		long total = 5;
		stubRepositoryToReturnProductCount(total);
		long count =productService.getProductsCount();
		assertEquals(count, total);
		verify(productRepository, times(1)).count();
	}

	private void stubRepositoryToReturnExistingProducts(int howMany) {
		when(productRepository.findAll(new Sort(Sort.Direction.DESC, "longname"))).thenReturn(
				ProductUtil.createProductList(howMany));
	}
	
	private void stubRepositoryToReturnExistingProductDetails(int lineNumber) {
		when(productRepository.findByLineNumber(lineNumber)).thenReturn(
				ProductUtil.createProduct(lineNumber));
	}
	
	private void stubRepositoryToReturnExistingProductNamesLike(String nameLike, int howmany) {
		when(productRepository.findByLongnameLikeIgnoreCase(nameLike)).thenReturn(
				ProductUtil.createProductList(howmany));
	}
	
	private void stubRepositoryToReturnProductCount(long total) {
		when(productRepository.count()).thenReturn(total);
	}
}
