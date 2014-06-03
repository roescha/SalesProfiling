package com.bjss.waitrose.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.domain.persistence.Flow;
import com.bjss.waitrose.domain.persistence.Product;
import com.bjss.waitrose.domain.persistence.Store;
import com.bjss.waitrose.service.ProductService;
import com.bjss.waitrose.service.StoreService;
import com.bjss.waitrose.util.FlowUtil;
import com.bjss.waitrose.util.StoreUtil;
import com.bjss.waitrose.util.ProductUtil;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
	@Mock
	private ProductService productService;
	@Mock
	private StoreService storeService;
	/*@Mock
	private ProfileBuilder profileBuilder;*/
	//@Mock
	//ProfileLoaderService profileLoader;

	private ProfileController productController;

/*
	@Before
	public void setUp() throws Exception {
		productController = new ProfileController(profileBuilder, profileLoader);
	}
*/
	@Test
	public void shouldGetProductProfilePage() {
	/*	int lineNumber = 42245;
		Product product = stubServiceToReturnProductDetails(lineNumber);
		//List<Store> stores = stubServicetoReturnAllStores(5);
		List<StoreProduct> storeProduct = stubServiceToReturnStoreProductForProduct(lineNumber, 2);
		List<Store> stores = stubServiceToReturnStoreByNumber(storeProduct);
		ModelAndView view = productController.getProductProfileView(lineNumber);

		verify(productService, times(1)).getProductDetails(lineNumber);
	//	verify(storeService, times(1)).getAllStores();
		verify(flowService, times(1)).getFlowProductByProduct(lineNumber);
		verify(storeService, times(1)).getStoreByStoreNumber(storeProduct.get(0).getStoreNumber());

		assertEquals("View name should be right", "product_profile", view.getViewName());
		assertEquals(	"Model should contain attribute with product details coming from the service",
									product, view.getModel().get("product"));
		assertEquals(stores.size(), ((List<Store>) view.getModel().get("stores")).size());*/
	}

	private List<Store> stubServiceToReturnStoreByNumber(List<Flow> storeProducts) {
		List<Store> stores = StoreUtil.createStoresFromStoreProduct(storeProducts);
		int i = 0;
		for (Store store : stores){
			when(storeService.getStoreByStoreNumber(store.getNumber())).thenReturn(store);
		}
		return stores;
	}

	/*private List<StoreProduct> stubServiceToReturnStoreProductForProduct(int lineNumber, int howMany) {
		List<StoreProduct> storeProducts = FlowUtil.createStoreProduct(howMany);
		when(flowService.getFlowProductByProduct(lineNumber)).thenReturn(storeProducts);
		return storeProducts;
	}*/

	private List<Store> stubServicetoReturnAllStores(int howMany) {
		List<Store> stores = StoreUtil.createStores(howMany);
		when(storeService.getAllStores()).thenReturn(stores);
		return stores;
	}

	private Product stubServiceToReturnProductDetails(int lineNumber) {
		Product product = ProductUtil.createProduct(lineNumber);
		when(productService.getProductDetails(lineNumber)).thenReturn(product);
		return product;
	}

}
