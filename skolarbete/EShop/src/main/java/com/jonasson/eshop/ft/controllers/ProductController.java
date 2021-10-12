package com.jonasson.eshop.ft.controllers;

import com.jonasson.eshop.bt.DTOs.ProductDTO;
import com.jonasson.eshop.bt.exceptions.NotFoundException;
import com.jonasson.eshop.bt.exceptions.ValidationException;
import com.jonasson.eshop.bt.services.IProductService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/product")
public class ProductController{
    
	@Context
    UriInfo uriInfo;

	private @Inject IProductService productRepository;
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> getAll() {
        return productRepository.getAll();
    }

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public ProductDTO getById(@PathParam("id") String id) {
        return productRepository.get(id);
    }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(ProductDTO product) {
		String result = null;
		try {
			result = productRepository.post(product);
		} catch (ValidationException e) {
			return Response.status(400, e.getMessage()).build();
		}
		return Response.ok(result).build();
	}
	
	@POST
	@Path("addone/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response addOneProduct(@PathParam("id") String id) {
        ProductDTO productToAdd = productRepository.get(id);
        productToAdd.setStock(productToAdd.getStock() +1);
        String result = null;
		try {
			result = productRepository.update(productToAdd);
		} catch (NotFoundException e) {
			return Response.status(400).build();
		} catch (ValidationException e) {
			return Response.status(400, e.getMessage()).build();
		}
        if(result.equals("Updated!")) {
        	return Response.ok(result).build();
        } else {
        	return Response.status(400).build();
        }
        	
    }
	
	@POST
	@Path("addmany/{id}/{amount}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response addManyProducts(@PathParam("id") String id, @PathParam("amount") int amount) {
        ProductDTO productToAdd = productRepository.get(id);
        productToAdd.setStock(productToAdd.getStock() + amount);
		return update(productToAdd);
    }
	
	@POST
	@Path("removeone/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response removeOneProduct(@PathParam("id") String id) {
        ProductDTO productToAdd = productRepository.get(id);
        if(productToAdd.getStock() < 1)
        	return Response.status(400).build();
        productToAdd.setStock(productToAdd.getStock() - 1);
		return update(productToAdd); 	
    }
    
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(ProductDTO product) {
		String result = null;
		try {
			result = productRepository.update(product);
		} catch (NotFoundException e) {
			return Response.status(400).build();
		} catch (ValidationException e) {
			return Response.status(400, e.getMessage()).build();
		}
        if(result.equals("Updated!")) {
        	return Response.ok(result).build();
        } else {
        	return Response.status(400).build();
        }
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
		String result = null;
		try {
			result = productRepository.delete(id);
		} catch (NotFoundException e) {
			return Response.status(400).build();
		}
		return Response.ok(result).build();
    }
	
}