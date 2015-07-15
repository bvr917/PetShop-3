package com.cg.petshop.core.entitybean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class OrderSummary {

	
	private String product_id;
	private String name;
	private String prd_quantity;
	private String price;
	private String price_per_product;
	@Id
	private String order_id;
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrd_quantity() {
		return prd_quantity;
	}
	public void setPrd_quantity(String prd_quantity) {
		this.prd_quantity = prd_quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPrice_per_product() {
		return price_per_product;
	}
	public void setPrice_per_product(String price_per_product) {
		this.price_per_product = price_per_product;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}
