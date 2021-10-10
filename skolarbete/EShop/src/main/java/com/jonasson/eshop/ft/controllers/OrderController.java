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

import com.jonasson.eshop.bt.DTOs.OrderDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;
import com.jonasson.eshop.bt.services.IOrderService;

@Path("/order")
public class OrderController {
	@Context
    UriInfo uriInfo;

	private @Inject IOrderService orderService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderDTO> getAll() {
		return orderService.getAll();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderDTO getById(@PathParam("id") String id) {
		return orderService.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(OrderDTO orderDTO) {
		String resp;
		try {
			resp = orderService.post(orderDTO);
		} catch (ValidationException e) {
			resp = e.getMessage();
			return Response.status(400, resp).build();
		}
		return Response.ok(resp).build();
	}
	
	//Delete without resetting stock
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		String resp = null;
		try {
			resp = orderService.delete(id);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		return Response.ok(resp).build();
	}
}
