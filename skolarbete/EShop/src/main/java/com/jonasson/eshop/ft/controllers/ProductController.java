package com.jonasson.eshop.ft.controllers;

import com.jonasson.eshop.bt.repositories.IProductRepository;
import com.jonasson.eshop.dt.DTOs.ProductDTO;

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

import com.mongodb.WriteResult;


@Path("/product")
public class ProductController{
    
	@Context
    UriInfo uriInfo;

	private @Inject IProductRepository productRepository;
	
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
		WriteResult result = productRepository.post(product);
		return Response.ok(result).build();
	}
    
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(ProductDTO product) {
		WriteResult result = productRepository.update(product);
		return Response.ok(result).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
		WriteResult result = productRepository.delete(id);
		return Response.ok(result).build();
    }
	
}