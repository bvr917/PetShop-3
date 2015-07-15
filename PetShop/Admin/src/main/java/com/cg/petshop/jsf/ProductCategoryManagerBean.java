package com.cg.petshop.jsf;


import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.ProductCategory;
import com.cg.petshop.service.ProductManagerService;
import com.cg.petshop.service.ProductSearch;
import com.cg.petshop.service.productSearchImpl;

/**
 * 
 * @author bomvenka
 * This Class is responsible for Managing the Product Categories(Add/Remove/edit) and showing Summary Screen
 */

@ManagedBean(name="productCategoryBean")
@RequestScoped
public class ProductCategoryManagerBean {
	
	
	@EJB
	private ProductSearch productSearchService;

	@EJB
    protected ProductManagerService productManagerService;
	
	
	final static Logger logger = Logger.getLogger(productSearchImpl.class.getName());
	
	
	protected ProductCategory[] availableProductCategories;
	
	protected String productCatId;
	
	protected boolean showUpdate=true;
	
	public boolean isShowUpdate() {
		return showUpdate;
	}


	public void setShowUpdate(boolean showUpdate) {
		this.showUpdate = showUpdate;
	}

	@ManagedProperty(value="#{newProductCategory}")
	protected ProductCategory newProductCategory;
	

	public ProductCategory getNewProductCategory() {
		return newProductCategory;
	}


	public void setNewProductCategory(ProductCategory newProductCategory) {
		this.newProductCategory = newProductCategory;
	}


	public String getProductCatId() {
		return productCatId;
	}


	public void setProductCatId(String productCatId) {
		this.productCatId = productCatId;
	}


	public void setAvailableProductCategories(
			ProductCategory[] availableProductCategories) {
		this.availableProductCategories = availableProductCategories;
	}

	
	 public ProductCategory[] getAvailableProductCategories() {
	    	
	    	try{
	    		availableProductCategories = productSearchService.rtrvProductCategories();
	    	}
	    	catch(Exception exp){
	    		
	    	}
			return availableProductCategories;
	}
	 
	 
	 public String addNewProduct(){
			
			try{
				System.out.println("Adding the Product::::");
				productManagerService.saveNewProductCategory(newProductCategory);
				newProductCategory = new ProductCategory();
			}
			catch(Exception exp){
				System.out.println(exp);
				
			}
			return "newProductCategory";
		}
		
	 
	 /**
	  * Responsible for navigating to selected record details.
	  * @param event
	  */
	 public void editSelectedProductCategory(ActionEvent event){
		 
		 
		try {

			Map<String,String> reqMap = (Map<String,String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.productCatId=reqMap.get("productCatId");

			newProductCategory = productManagerService.getProductCategoryById(productCatId);

		} catch (Exception exp) {
			logger.error("Error while editing the product category record for ID :::"+productCatId ,exp);

		}
		 
		 
	 }

	 /**
	  * Responsible for deleting selected Product Category
	  * @param event
	  */
	 public void deleteSelectedProductCategory(ActionEvent event){
		 
		 
		try {

			Map<String,String> reqMap = (Map<String,String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.productCatId=reqMap.get("productCatId");

			productManagerService.deleteProductCategoryById(productCatId);

		} catch (Exception exp) {
			logger.error("Error while editing the product category record for ID :::"+productCatId ,exp);

		}
		 
		 
	 }
	 
	 /**
		 * @author bomvenka
		 * This Method is responsible for generating Product Category ID and navigating to newProductCategory Screen 
		 */
		public void generateNewProductCategoryID(){
			
			try{
				
				if(newProductCategory.getCategory()==null){
					String pID = null;
					pID = "C" + productManagerService.getNextProductCategoryId().intValue();
					newProductCategory.setCategory(pID);
				}
				
			}
			catch(Exception exp){
				
			}
			
		}
		
		
		public void updateProductCategory(){
			
			try{
				System.out.println("updating the new Product :::");
				
				productManagerService.updateProductCategory(newProductCategory);
				
				
			}
			catch(Exception exp){
				System.out.println();
				
			}
			
		}
	
		/**
		 * @author bomvenka
		 * This Method is responsible for generating Product ID and navigating to newProduct Screen 
		 */
		public void getProductCategoryToView(){
			
			try{
				
				Map<String,String> reqMap = (Map<String,String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
				this.productCatId=reqMap.get("id");
					
				if(productCatId!=null && productCatId.length()>0){
					newProductCategory=productManagerService.getProductCategoryById(productCatId);
					setShowUpdate(false);
				}
				
			}
			catch(Exception exp){
				
			}
			
		}

}
