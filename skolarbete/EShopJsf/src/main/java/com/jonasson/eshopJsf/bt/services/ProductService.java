package com.jonasson.eshopJsf.bt.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasson.eshopJsf.dt.models.Product;


public class ProductService implements IProductService {

	public ProductService() {
	}
	
	@Override
	public Product get(String id) {
        URL url;
        Product product = null;
		try {
			url = new URL("http://127.0.0.1:8080/EShop/product/" +id);
	        // Open a connection(?) on the URL(?) and cast the response(??)
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        // Now it's "open", we can set the request method, headers etc.
	        connection.setRequestProperty("accept", "application/json");

	        // This line makes the request
	        InputStream responseStream = connection.getInputStream();

	        // Manually converting the response body InputStream to APOD using Jackson
	        ObjectMapper mapper = new ObjectMapper();
	        product = mapper.readValue(responseStream, Product.class);
	        System.out.println(product);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return product;
	}
	
	@Override
	public List<Product> getAll() {
        // Create a neat value object to hold the URL
        URL url;
        List<Product> productList = null;
		try {
			url = new URL("http://127.0.0.1:8080/EShop/product");
	        // Open a connection(?) on the URL(?) and cast the response(??)
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        // Now it's "open", we can set the request method, headers etc.
	        connection.setRequestProperty("accept", "application/json");

	        // This line makes the request
	        InputStream responseStream = connection.getInputStream();

	        // Manually converting the response body InputStream to APOD using Jackson
	        ObjectMapper mapper = new ObjectMapper();
	        Product[] productArray = mapper.readValue(responseStream, Product[].class);
	        productList = Arrays.asList(productArray);	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return productList;
        
	}

}
