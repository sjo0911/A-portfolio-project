package com.jonasson.eshop.dt.daos;

import javax.annotation.PostConstruct;

import com.jonasson.eshop.dt.enteties.Product;

public class ProductDAO extends DAOAbstract<Product> implements IProductDAO {

	@PostConstruct 
	protected void init() {
		setCollection("eshop", "productcollection");
	}
	
}
