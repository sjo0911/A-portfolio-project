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
public class OrderService implements IOrderService {
	
	@Override
	public void post(Order order) throws DBException {
		try {
			URL url = new URL ("http://sjo0911wildfly.herokuapp.com/EShop/EShop/order");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInputString = mapper.writeValueAsString(order);
			OutputStream os = con.getOutputStream();
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input);
			os.flush();
			os.close();
			System.out.println(con.getResponseCode());
			try(BufferedReader br = new BufferedReader(
					  new InputStreamReader(con.getInputStream(), "utf-8"))) {
					    StringBuilder response = new StringBuilder();
					    String responseLine = null;
					    while ((responseLine = br.readLine()) != null) {
					        response.append(responseLine.trim());
					    }
					    System.out.println(response.toString());
					}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new DBException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new DBException();
		}
	}
	
	@Override
	public List<Order> getAll() {
        // Create a neat value object to hold the URL
        URL url;
        List<Order> orderList = null;
		try {
			url = new URL("http://sjo0911wildfly.herokuapp.com/EShop/EShop/order");
	        // Open a connection(?) on the URL(?) and cast the response(??)
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        // Now it's "open", we can set the request method, headers etc.
	        connection.setRequestProperty("accept", "application/json");

	        // This line makes the request
	        InputStream responseStream = connection.getInputStream();

	        // Manually converting the response body InputStream to APOD using Jackson
	        ObjectMapper mapper = new ObjectMapper();
	        Order[] orderArray = mapper.readValue(responseStream, Order[].class);
	        orderList = Arrays.asList(orderArray);	        
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return orderList;
	}
}
