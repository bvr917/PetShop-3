package com.cg.petshop.service;

import java.math.BigInteger;

import javax.ejb.Local;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;

@Local
public interface ProductManagerService {
	
	
	public void saveNewProduct(Product newProduct);
	public void updateProduct(Product updateProduct);
	public void saveNewProductCategory(ProductCategory newProductCategory);
	public Product getProductById(String id);
	public ProductCategory getProductCategoryById(String id);
	public void deleteProductCategoryById(String id);
	public BigInteger getNextProductId();
	public BigInteger getNextProductCategoryId();
	public void deleteProductById(String id);
	public void updateProductCategory(ProductCategory updateProduct);


}
