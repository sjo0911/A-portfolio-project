package com.jonasson.eshopJsf.dt.clients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.jonasson.eshopJsf.bt.DTOs.OrderDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MQOrderClient implements IMQOrderClient {
	private final String QUEUE_NAME = "order";
	Connection connection;
	
	@Override
	public void post(OrderDTO orderDTO) {
		Channel channel = retriveChannel();
		try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = orderDTOToString(orderDTO);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String orderDTOToString(OrderDTO orderDTO) {
		Gson gson = new Gson();
		return gson.toJson(orderDTO).toString();
	}
	
	private Channel retriveChannel() {
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri("amqps://iaqmcoek:EuPEe_Au1nAuEa8DD8tWstWP3VwFwV1m@rattlesnake.rmq.cloudamqp.com/iaqmcoek");
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		Channel channel = null;
		
		try {
			factory.setConnectionTimeout(30000);
			connection = factory.newConnection();
			channel = connection.createChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channel;
	}
}
