package com.cg.petshop.service;

import java.util.List;

import javax.ejb.Local;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;
import com.cg.petshop.core.entitybean.ProductSummary;


@Local
public interface ProductSearch {
	
	
	public ProductSummary[] rtrvAllAvailableProducts();
	public ProductSummary[] rtrvAllAvailableProducts(String category,String name);
	public ProductCategory[] rtrvProductCategories();
	public List<Product> getAllProductsList();
	public Product getProductById(String id);

}
