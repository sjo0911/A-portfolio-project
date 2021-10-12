package com.jonasson.eshopJsf.dt.clients;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;

@RequestScoped
public class ProductClient implements IProductClient {
	@Override
	public ProductDTO get(String id) {
        URL url;
        ProductDTO product = null;
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
	        product = mapper.readValue(responseStream, ProductDTO.class);
	        System.out.println(product);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return product;
	}
	
	@Override
	public List<ProductDTO> getAll() {
        // Create a neat value object to hold the URL
        URL url;
        List<ProductDTO> productList = null;
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
	        ProductDTO[] productArray = mapper.readValue(responseStream, ProductDTO[].class);
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
				URL url = new URL ("http://127.0.0.1:8080/EShop/product/addone/" +id);
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
				URL url = new URL ("http://127.0.0.1:8080/EShop/product/addmany/" +id +"/" + amount);
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
				URL url = new URL ("http://127.0.0.1:8080/EShop/product/removeone/" +id);
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
