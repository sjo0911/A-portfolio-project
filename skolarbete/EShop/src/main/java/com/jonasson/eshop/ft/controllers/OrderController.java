package com.jonasson.eshop.ft.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.repositories.IOrderRepository;
import com.jonasson.eshop.bt.repositories.IProductRepository;
import com.jonasson.eshop.dt.DTOs.OrderDTO;
import com.jonasson.eshop.dt.DTOs.ProductDTO;
import com.jonasson.eshop.dt.enteties.OrderItem;

@Path("/order")
public class OrderController {
	@Context
    UriInfo uriInfo;

	private @Inject IOrderRepository orderRepository;
	private @Inject IProductRepository productRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderDTO> getAll() {
		return orderRepository.getAll();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderDTO getById(@PathParam("id") String id) {
		return orderRepository.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(OrderDTO orderDTO) {
		System.out.println(orderDTO.getCustomer().getFirstName());
		for(OrderItem prod : orderDTO.getOrderItemList()) {
			ProductDTO productToUpdate = productRepository.get(prod.getProductId());
			if(productToUpdate.getStock() < prod.getAmount())
				return Response.status(400).build();
			productToUpdate.setStock(productToUpdate.getStock() - prod.getAmount());
			productRepository.update(productToUpdate);
		}

		String resp = orderRepository.post(orderDTO);
		return Response.ok(resp).build();
	}
	
	//Delete without resetting stock
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		String resp = null;
		try {
			resp = orderRepository.delete(id);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		return Response.ok(resp).build();
	}
	
	//Delete and reset stock
	@DELETE
	@Path("cancelorder/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelOrder(@PathParam("id") String id) {
		OrderDTO orderToCancel = orderRepository.get(id);
		if(orderToCancel == null) 
			return Response.status(400).build();
		orderToCancel.getOrderItemList().forEach(prod -> {
			ProductDTO productToUpdate = productRepository.get(prod.getProductId());
			productToUpdate.setStock(productToUpdate.getStock() + prod.getAmount());
			productRepository.update(productToUpdate);
		});
		
		String resp = null;
		try {
			resp = orderRepository.delete(id);
		} catch (NotFoundException e) {
			return Response.status(400).build();
		}
		return Response.ok(resp).build();
	}
	
}
