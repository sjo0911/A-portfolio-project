package com.jonasson.eshopJsf.ft.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.jonasson.eshopJsf.dt.models.CartItem;
import com.jonasson.eshopJsf.dt.models.Product;

@Named("cartBean")
@SessionScoped
public class CartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<CartItem> cartItemList  = new ArrayList<CartItem>();
	
	public void addToCart(Product product) {
		boolean isProductExistingInCart = cartItemList
				.stream()
				.anyMatch(prodInCart -> prodInCart.getProduct().getId().equals(product.getId()));
		if(!isProductExistingInCart) {
			CartItem cartItem = new CartItem();
			cartItem.setAmount(1);
			cartItem.setProduct(product);
			cartItemList.add(cartItem);

		} else {
			cartItemList.forEach(prodInCart -> {
				if(prodInCart.getProduct().getId().equals(product.getId()))
					prodInCart.setAmount(prodInCart.getAmount() + 1);
			});
		} 
	}
	
	public void removeFromCart(Product product) {
		for(CartItem cartItem : cartItemList) {
			if(cartItem.getProduct().getId().equals(product.getId())) {
				if(cartItem.getAmount() == 1) {
					cartItemList.remove(cartItem);
					break;
				}else {
					cartItem.setAmount(cartItem.getAmount() -1);
				}
			}
		}
	}
	
	public double cartsTotalAmount(){
		double amount = 0;
		for(CartItem cartItem: cartItemList) {
			amount += cartItem.totalAmount();
		}
		return amount;
	}

	public List<CartItem> getProductInCartList() {
		return cartItemList;
	}

	public void setProductInCartList(List<CartItem> productInCartList) {
		this.cartItemList = productInCartList;
	}
	
}
