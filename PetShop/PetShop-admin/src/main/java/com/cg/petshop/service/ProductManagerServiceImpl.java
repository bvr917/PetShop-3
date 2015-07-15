package com.cg.petshop.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;

/**
 * @author bomvenka
 *
 */
@Stateless
public class ProductManagerServiceImpl implements ProductManagerService {


	
	
	@PersistenceContext(unitName = "defaultPersistenceUnit")
    private EntityManager productManager;
	final static Logger logger = Logger.getLogger(ProductManagerServiceImpl.class.getName());
	
	
	
	/**
	 * Saving the New Product
	 */
	public void saveNewProduct(Product newProduct){
		
		try{
			logger.info("saving the New Product Object::");
			productManager.persist(newProduct);
			
		}catch(Exception exp){
			logger.error("Error While saving the new Product", exp);
			
		}
		
	}
	/**
	 * Saving the New Product Category
	 */
	public void saveNewProductCategory(ProductCategory newProductCategory){
		
		try{
			logger.info("saving the New Product Category Object::");
			productManager.persist(newProductCategory);
			
		}catch(Exception exp){
			logger.error("Error While saving the new Product Category", exp);
		}
		
	}
	
	/**
	 * Getting Product Object by Product ID
	 */
	public Product getProductById(String id){
		
		Product prdObject = null;
		try{
			logger.info("getting the New Product Object::");
			prdObject = productManager.find(Product.class, id);
		}
		catch(Exception exp){
			logger.error("Error While getting  Product", exp);
			
		}
		
		return prdObject;
	}
	
	/**
	 * Getting Product Category Object By ID
	 */
	public ProductCategory getProductCategoryById(String id){
		
		ProductCategory prdObject = null;
		try{
			logger.info("getting the New Product CAtegory Object::");
			prdObject = productManager.find(ProductCategory.class, id);
		}
		catch(Exception exp){
			logger.info("getting Product Category Object by ID ::::",exp);
			
		}
		
		return prdObject;
	}
	
	/**
	 * Deleting Product Category
	 */
	public void deleteProductCategoryById(String id){
		
		ProductCategory prdObject = null;
		try{
			logger.info("deleting Product ");
			prdObject = productManager.find(ProductCategory.class, id);
			productManager.remove(prdObject);
		}
		catch(Exception exp){
			logger.info("Error deleting  the  Product ::::",exp);
			
		}
		
	}

	/**
	 * Getting Auto Generated Product ID
	 */
	public BigInteger getNextProductId() {
		
		BigInteger generatedValue=null;
		logger.info("getting  the New Product ID::");
		try{
			Query query = productManager.createNativeQuery("select nextval('PSM_PID_SEQENCE')");
			generatedValue = (BigInteger)query.getSingleResult();
		}
		catch(Exception exp){
			logger.info("Error getting  the New Product ::::",exp);
			
		}
		return generatedValue;
	}
	
	/**
	 * Updating The Product Details
	 */
	public void updateProduct(Product updateProduct) {
		logger.info("updating   New Product ::");
		
		try{
			
			productManager.merge(updateProduct);
		}
		catch(Exception exp){
			logger.error("Error While updating the Product:::",exp);
			
		}
		
	}
	
	public void deleteProductById(String id){
		
		try{
			Product prdObject = getProductById(id);
			productManager.remove(prdObject);
		}
		catch(Exception exp){
			logger.error("Error While deleting the Product:::",exp);
		}
		
	}
	
	public BigInteger getNextProductCategoryId() {
		
		BigInteger generatedValue=null;
		try{
			Query query = productManager.createNativeQuery("select nextval('PSM_PC_SEQUENCE')");
			generatedValue = (BigInteger)query.getSingleResult();
			
		}
		catch(Exception exp){
			logger.error("Error While getting the Product Category ID:::",exp);
			
		}
		return generatedValue;
	}
	
	public void updateProductCategory(ProductCategory updateProduct) {
		
		try{
			
			productManager.merge(updateProduct);
		}
		catch(Exception exp){
			logger.error("Error While updating the Product Category :::",exp);
			
		}
		
	}
	
	
}
