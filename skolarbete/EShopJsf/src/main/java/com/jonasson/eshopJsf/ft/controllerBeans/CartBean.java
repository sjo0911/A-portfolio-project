package com.jonasson.eshopJsf.ft.controllerBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jonasson.eshopJsf.bt.DTOs.CartItem;
import com.jonasson.eshopJsf.bt.DTOs.ProductDTO;
import com.jonasson.eshopJsf.bt.exceptions.DBException;
import com.jonasson.eshopJsf.bt.services.IProductService;

@Named("cartBean")
@SessionScoped
public class CartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CartItem> cartItemList  = new ArrayList<CartItem>();
	private @Inject IProductService productService;
	
	@PreDestroy
	public void clearCartAndReturnProducts() {
		if(!cartItemList.isEmpty()) {
			cartItemList.forEach(item -> {
				try {
					productService.addMany(item.getProduct().getId(), item.getAmount());
				} catch (DBException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public void addToCart(ProductDTO product) {
		if(product.getStock() > 0) {	
			boolean isProductExistingInCart = cartItemList
					.stream()
					.anyMatch(prodInCart -> prodInCart.getProduct().getId().equals(product.getId()));
			if(!isProductExistingInCart) {
				CartItem cartItem = new CartItem();
				cartItem.setAmount(1);
				cartItem.setProduct(product);
				try {
					if(productService.removeOne(product.getId()) == 200)
						cartItemList.add(cartItem);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			} else {
				cartItemList.forEach(prodInCart -> {
					if(prodInCart.getProduct().getId().equals(product.getId()))
						try {
							if(productService.removeOne(product.getId()) == 200)
							prodInCart.setAmount(prodInCart.getAmount() + 1);
						} catch (DBException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				});
			} 
		}
	}
	
	public void clearCartAfterOrderCompleted() {
		cartItemList = new ArrayList<CartItem>();
	}
	
	public void removeFromCart(ProductDTO product) {
		for(CartItem cartItem : cartItemList) {
			if(cartItem.getProduct().getId().equals(product.getId())) {
				if(cartItem.getAmount() == 1) {
					try {
						if(productService.addOne(product.getId())  == 200) {
							cartItemList.remove(cartItem);
							break;
						}
					} catch (DBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					try {
						if(productService.addOne(product.getId())  == 200)
						cartItem.setAmount(cartItem.getAmount() -1);
					} catch (DBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public double cartsTotalPrice(){
		double amount = 0;
		for(CartItem cartItem: cartItemList) {
			amount += cartItem.totalAmount();
		}
		return amount;
	}
	
	public int numberOfItems() {
		int totalItem = 0;
		for (Iterator iterator = cartItemList.iterator(); iterator.hasNext();) {
			CartItem cartItem = (CartItem) iterator.next();
			totalItem += cartItem.getAmount();
		}
		return totalItem;
	}

	public List<CartItem> getProductInCartList() {
		return cartItemList;
	}

	public void setProductInCartList(List<CartItem> productInCartList) {
		this.cartItemList = productInCartList;
	}
	
}
