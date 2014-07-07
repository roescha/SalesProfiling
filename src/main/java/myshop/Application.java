package myshop;

import javax.inject.Inject;

import myshop.service.ProductService;
import myshop.service.StoreService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements
		CommandLineRunner {

	@Inject
	ProductService productService;

	@Inject
	StoreService storeService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Products found:" + productService.getProductsCount());
		System.out.println("-------------------------------");
		System.out.println("Stores found:" + storeService.getStoresCount());
		System.out.println("-------------------------------");
		System.out.println("*** COMPLETED STARTUP CHECKS *****");
		System.out.println();
	}
}
