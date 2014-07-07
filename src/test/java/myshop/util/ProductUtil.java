package myshop.util;

import java.util.ArrayList;
import java.util.List;

import myshop.domain.persistence.Product;

public class ProductUtil {
	public static List<Product> createProductList(int howMany) {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < howMany; i++) {
			String longName = howMany <= 4 ? "longname" : "namelong";
			products.add(new Product(i, longName + i, "shortName" + 1));
		}
		return products;
	}
	
	public static Product createProduct(int lineNumber){
		Product product = new Product(lineNumber, "longname", "shortname");
		
		return product;
		
	}

	public static List<Product> createProductsWithLongnames(String[] similars) {
		// TODO Auto-generated method stub
		return null;
	}
}
