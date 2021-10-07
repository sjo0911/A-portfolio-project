package com.jonasson.eshop.ft.controllers;

import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.repositories.ICartRepository;
import com.jonasson.eshop.bt.repositories.IProductRepository;
import com.jonasson.eshop.dt.DTOs.CartDTO;
import com.jonasson.eshop.dt.DTOs.ProductDTO;
import com.jonasson.eshop.dt.enteties.OrderItem;

@Path("/cart")
public class CartController {
	@Context
    UriInfo uriInfo;

	private @Inject ICartRepository cartRepository;
	private @Inject IProductRepository productRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CartDTO> getAll() {
		return cartRepository.getAll();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CartDTO getById(@PathParam("id") String id) {
		return cartRepository.get(id);
	}
	
	@GET
	@Path("/getNew")
	@Produces(MediaType.APPLICATION_JSON)
	public CartDTO getNew() {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setProductList(new ArrayList<OrderItem>());
		String id = cartRepository.post(cartDTO);
		cartDTO.setId(id);
		return cartDTO;
	}
	
	@Path("/addToCart/{cartId}/product/{productId}")
	@PATCH
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToCart(@PathParam("cartId") String cartId,@PathParam("productId") String productId) {
		ProductDTO product = productRepository.get(productId);
		//Check if product exists and if its in stock.
		if(product == null || product.getStock() <= 0)
			return Response.status(400).build();
		CartDTO cartDTO = cartRepository.get(cartId);
		boolean isProductExistingInCart = false;
		if(cartDTO.getProductList() != null) {
			isProductExistingInCart = cartDTO.getProductList()
					.stream()
					.anyMatch(prod -> prod.getProductId().equals(productId));
		}
		if(!isProductExistingInCart) {
			OrderItem productToOrder = new OrderItem();
			productToOrder.setProductId(productId);
			productToOrder.setAmount(1);
			cartDTO.getProductList().add(productToOrder);
		} else {
			cartDTO.getProductList().forEach(prod -> {
				if(prod.getProductId().equals(productId))
					prod.setAmount(prod.getAmount() + 1);
			});
		}
		
		String resp;
		try {
			resp = cartRepository.update(cartDTO);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		product.setStock(product.getStock() -1);
		productRepository.update(product);
		return Response.ok(resp).build();
	}
	
	@Path("/removeFromCart/{cartId}/product/{productId}")
	@PATCH
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeFromCart(@PathParam("cartId") String cartId,@PathParam("productId") String productId) {
		ProductDTO product = productRepository.get(productId);
		//Check if product exists
		if(product == null)
			return Response.status(400).build();
		CartDTO cartDTO = cartRepository.get(cartId);
		if(cartDTO.getProductList() == null)
			return Response.status(400).build();
		
		boolean isProductExistingInCart = cartDTO.getProductList()
					.stream()
					.anyMatch(prod -> prod.getProductId().equals(productId));
		
		if(!isProductExistingInCart) {
			return Response.status(400).build();
		} else {
			//Removes 1 product if amount > 1 else remove the whole product from the productlist
			cartDTO.getProductList().forEach(prod -> {
				if(prod.getProductId().equals(productId))
					if(prod.getAmount() > 1)
						prod.setAmount(prod.getAmount() -1);
					else
						cartDTO.getProductList().remove(prod);
			});
		}
		String resp;
		try {
			resp = cartRepository.update(cartDTO);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		product.setStock(product.getStock() +1);
		productRepository.update(product);
		return  Response.ok(resp).build();
		
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") String id) {
		String resp = null;
		try {
			resp = cartRepository.delete(id);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		return Response.ok(resp).build();
	}
	
	@DELETE
	@Path("deleteAndReturnProducts/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAndReturnProducts(@PathParam("id") String id) {
		CartDTO cart = cartRepository.get(id);
		if(cart == null)
			return Response.status(400).build();
		//Return product to stock
		cart.getProductList().forEach(prod -> {
			ProductDTO product = productRepository.get(prod.getProductId());
			product.setStock(product.getStock() + prod.getAmount());
			productRepository.update(product);
		});
		String resp = null;
		try {
			resp = cartRepository.delete(id);
		} catch (NotFoundException e) {
			return Response.status(404).build();
		}
		return Response.ok(resp).build();
	}
	
}
