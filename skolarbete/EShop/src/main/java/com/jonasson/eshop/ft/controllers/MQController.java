package com.jonasson.eshop.ft.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.google.gson.Gson;
import com.jonasson.eshop.bt.DTOs.OrderDTO;
import com.jonasson.eshop.bt.exceptions.ValidationException;
import com.jonasson.eshop.bt.services.IOrderService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Startup
@Singleton
public class MQController {
	private final String QUEUE_NAME = "order";
	private @Inject IOrderService orderService;
	
	public MQController() {
		Channel channel = retriveChannel();
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			    String message = new String(delivery.getBody(), "UTF-8");
			    OrderDTO orderDTO = stringToOrderDTO(message);
			    try {
					orderService.post(orderDTO);
				} catch (ValidationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
		};
		try {
			channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private OrderDTO stringToOrderDTO(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, OrderDTO.class);
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
			Connection connection = factory.newConnection();
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
