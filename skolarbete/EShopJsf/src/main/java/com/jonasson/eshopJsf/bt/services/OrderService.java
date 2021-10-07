package com.jonasson.eshopJsf.bt.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasson.eshop.bt.exceptions.DBException;
import com.jonasson.eshopJsf.dt.models.Order;

public class OrderService implements IOrderService {
	
	@Override
	public void post(Order order) throws DBException {
		try {
			URL url = new URL ("http://http://sjo0911wildfly.herokuapp.com/EShop/order");
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
}
