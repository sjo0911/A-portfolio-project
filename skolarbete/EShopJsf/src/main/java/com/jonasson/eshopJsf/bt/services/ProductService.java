package com.jonasson.eshopJsf.bt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.models.Order;
import com.jonasson.eshopJsf.dt.models.Product;

@RequestScoped
public class ProductService implements IProductService {

	public ProductService() {
	}
	
	@Override
	public Product get(String id) {
        URL url;
        Product product = null;
		try {
			url = new URL("http://sjo0911wildfly.herokuapp.com/EShop/product/" +id);
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
			url = new URL("http://sjo0911wildfly.herokuapp.com/EShop/product");
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
	
	@Override
	public int addOne(String id) throws DBException {
		try {
				URL url = new URL ("http://sjo0911wildfly.herokuapp.com/EShop/product/addone/" +id);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.setDoOutput(true);
				con.connect();
				int responseCode =  con.getResponseCode();
				con.disconnect();
				return responseCode;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new DBException();
			} catch (IOException e) {
				e.printStackTrace();
				throw new DBException();
			}
	}
	
	@Override
	public int addMany(String id, int amount) throws DBException {
		try {
				URL url = new URL ("http://sjo0911wildfly.herokuapp.com/EShop/product/addmany/" +id +"/" + amount);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.setDoOutput(true);
				con.connect();
				int responseCode =  con.getResponseCode();
				con.disconnect();
				return responseCode;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new DBException();
			} catch (IOException e) {
				e.printStackTrace();
				throw new DBException();
			}
	}
	
	@Override
	public int removeOne(String id) throws DBException {
		try {
				URL url = new URL ("http://sjo0911wildfly.herokuapp.com/EShop/product/removeone/" +id);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.setDoOutput(true);
				con.connect();
				int responseCode =  con.getResponseCode();
				con.disconnect();
				return responseCode;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new DBException();
			} catch (IOException e) {
				e.printStackTrace();
				throw new DBException();
			}
	}

}
