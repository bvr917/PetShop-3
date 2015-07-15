package com.cg.petshop.service;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
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
	
	
	
	
	public void saveNewProduct(Product newProduct){
		
		try{
			
			productManager.persist(newProduct);
			
		}catch(Exception exp){
			
		}
		
	}
	
	public void saveNewProductCategory(ProductCategory newProductCategory){
		
		try{
			
			productManager.persist(newProductCategory);
			
		}catch(Exception exp){
			
		}
		
	}
	
	public Product getProductById(String id){
		
		Product prdObject = null;
		try{
			
			prdObject = productManager.find(Product.class, id);
		}
		catch(Exception exp){
			
		}
		
		return prdObject;
	}

	public ProductCategory getProductCategoryById(String id){
		
		ProductCategory prdObject = null;
		try{
			
			prdObject = productManager.find(ProductCategory.class, id);
		}
		catch(Exception exp){
			
		}
		
		return prdObject;
	}

	public void deleteProductCategoryById(String id){
		
		ProductCategory prdObject = null;
		try{
			prdObject = productManager.find(ProductCategory.class, id);
			productManager.remove(prdObject);
		}
		catch(Exception exp){
			
		}
		
	}

	
	public BigInteger getNextProductId() {
		
		BigInteger generatedValue=null;
		try{
			Query query = productManager.createNativeQuery("select nextval('PSM_PID_SEQENCE')");
			generatedValue = (BigInteger)query.getSingleResult();
			
		}
		catch(Exception exp){
			System.out.println(exp);
			
		}
		return generatedValue;
	}

	public void updateProduct(Product updateProduct) {
		
		try{
			
			productManager.merge(updateProduct);
		}
		catch(Exception exp){
			
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
			System.out.println(exp);
			
		}
		return generatedValue;
	}
	
	public void updateProductCategory(ProductCategory updateProduct) {
		
		try{
			
			productManager.merge(updateProduct);
		}
		catch(Exception exp){
			
		}
		
	}
	
	
}
