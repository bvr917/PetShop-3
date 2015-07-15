package com.cg.petshop.jsfbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;
import com.cg.petshop.service.ProductSearch;
import com.cg.petshop.service.productSearchImpl;


@ManagedBean(name="productsearchmanagedbean", eager=true)
@RequestScoped
public class ProductSearchManagedBean {
	
	@EJB
	private ProductSearch productSearch = null;
	
	private String productName = null;
	private String productCategory = null;
	private  String result = "product_summary";
	final static Logger logger = Logger.getLogger(productSearchImpl.class.getName());

	
	private String prod[] = new String[2];


	public String[] getProd() {
		
		prod[0]="venkat";
		prod[1]="venkat";
		
		return prod;
	}
	public void setProd(String[] prod) {
		
		this.prod = prod;
	}


	List<Product> productList = null; 
	
	ProductCategory[] availableProducts = null;
	List<ProductCategory> availableProductsList = null;
	
	
	public List<ProductCategory> getAvailableProductsList() {
		return availableProductsList;
	}
	public void setAvailableProductsList(List<ProductCategory> availableProductsList) {
		this.availableProductsList = availableProductsList;
	}
	public ProductCategory[] getAvailableProductCategories() {
		
		availableProducts = productSearch.rtrvProductCategories();
		return availableProducts;
		
	}
	public void setAvailableProducts(ProductCategory[] availableProducts) {
		this.availableProducts = availableProducts;
	}
	public String getproductName() {
		return productName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public void setproductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	/**
     * Retrieve The Product Categories from the Database.
     *
     * @param 
     * @return ProductCategory[] 
     */
	
	public ProductCategory[] retrieveProductCategories() {

		logger.info("Retriving the Product Categories::::: Starts");
		try {
			availableProducts = productSearch.rtrvProductCategories();
		} catch (Exception exp) {
			logger.error("Error While retriving the Product Categories...", exp);
		}

		return availableProducts;

	}

	
	
	
	
	
	
	

}
