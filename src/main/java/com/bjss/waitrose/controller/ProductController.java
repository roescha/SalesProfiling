package com.bjss.waitrose.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bjss.waitrose.domain.persistence.Product;
import com.bjss.waitrose.domain.persistence.Store;
import com.bjss.waitrose.service.ProductService;
import com.bjss.waitrose.service.StoreService;

@Controller
public class ProductController {
	private static final Logger Log = LoggerFactory.getLogger(ProductController.class);

	private final ProductService productService;
	private final StoreService storeService;

	@Inject
	public ProductController(ProductService productService, StoreService branchService) {
		this.productService = productService;
		this.storeService = branchService;
	}

	@RequestMapping(value = "product/{lineNumber}", method = RequestMethod.GET)
	public ModelAndView getProductDetailsView(@PathVariable int lineNumber, 
			@RequestParam(defaultValue = "", required = false) String dateFrom,
			@RequestParam(defaultValue = "", required = false) String dateTo,
			@RequestParam(defaultValue = "0", required = false) int storeNumber,
			@RequestParam(defaultValue = "false", required = false) boolean autoDisplay) {

		Log.info("Processing request for product details view for line number {}", lineNumber);
		Collection<Integer> storeIdsForProduct = null;
		List<Store> storesForProduct;
		if (autoDisplay) {
			storesForProduct = storeService.getAllStores();
		} else {
			storeIdsForProduct = storeService.getStoreIdsForProduct(lineNumber);
		
			if (storeIdsForProduct.isEmpty()) {
				ModelMap model = new ModelMap();
				model.addAttribute("message", "Product is not in stores");
				return new ModelAndView("error_page", model);
			}
			storesForProduct = storeService.getStoresForIds(storeIdsForProduct);
		}
		
		Collections.sort(storesForProduct);

		ModelMap model = new ModelMap();
		model.addAttribute("product", productService.getProductDetails(lineNumber));
		model.addAttribute("stores", storesForProduct);
		if (autoDisplay) {
			model.addAttribute("autoDisplay", autoDisplay);
			model.addAttribute("dateFrom", dateFrom);
			model.addAttribute("dateTo", dateTo);
		}
		model.addAttribute("storeNumber", storeNumber);

		Log.info("Completed request for product details view for line number {}", lineNumber);
		
		return new ModelAndView("product_profile", model);
	}

	@RequestMapping(value = "products", method = RequestMethod.GET)
	@ResponseBody
	public
			List<Product>
			getProductsByLineNumberOrNameLike(@RequestBody @RequestParam(required = false) String nameLike,
																				@RequestBody @RequestParam(defaultValue = "0", required = false) int lineNumber) {

		Log.info(	"Processing request for product list with line number {} and name like {}",
							lineNumber, nameLike);

		List<Product> products = new ArrayList<>();
		if (lineNumber > 0) {
			Product product = productService.getProductDetails(lineNumber);
			if (product != null) {
				products.add(product);
			}
		} else if (nameLike != null && nameLike.trim().length() > 0) {
			products = productService.getProductsByLongnameLike(nameLike);
			Collections.sort(products);
		}

		Log.info("Completed request for product list.");
		return products;
	}

}
