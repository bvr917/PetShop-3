package com.cg.petshop.jsf;

import java.util.Map;

import javax.annotation.PostConstruct;
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
 * @author bomvenka This Class is responsible for Managing the Product
 *         Categories(Add/Remove/edit) and showing Summary Screen
 */

@ManagedBean(name = "productCategoryBean")
@RequestScoped
public class ProductCategoryManagerBean {

	@EJB
	private ProductSearch productSearchService;

	@EJB
	protected ProductManagerService productManagerService;

	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	protected ProductCategory[] availableProductCategories;

	protected String productCatId;

	protected boolean showUpdate = true;

	public boolean isShowUpdate() {
		return showUpdate;
	}

	public void setShowUpdate(boolean showUpdate) {
		this.showUpdate = showUpdate;
	}
	
	@PostConstruct
	public void init(){
		newProductCategory = new ProductCategory();
	}
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

	/**
	 * Retrieves the Available product Categories
	 * 
	 * @return Array Of ProductCategory Objects
	 */
	public ProductCategory[] getAvailableProductCategories() {
		logger.info("Getting the Availbale Product Categories:::::");
		try {
			availableProductCategories = productSearchService
					.rtrvProductCategories();
			logger.info("Successfully retrives Availbale Product Categories:::::");
		} catch (Exception exp) {
			logger.error(
					"Error While retriving the Available product categories",
					exp);
		}
		return availableProductCategories;
	}

	/**
	 * Adding the New Category
	 * 
	 * @return Result String to show newProduct Screen Again.
	 */
	public String addNewProduct() {

		try {
			logger.info("Adding new Product Category::::");
			productManagerService.saveNewProductCategory(newProductCategory);
			newProductCategory = new ProductCategory();
			logger.info("Successfully Added the new Product Category");
		} catch (Exception exp) {
			logger.error("Error While Adding the New Product::::", exp);

		}
		return "newProductCategory";
	}

	/**
	 * Responsible for navigating to selected Product Category details.
	 * 
	 * @param event
	 */
	public void editSelectedProductCategory(ActionEvent event) {

		try {
			logger.info("Editing  Product Category::::");
			Map<String, String> reqMap = (Map<String, String>) FacesContext
					.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			this.productCatId = reqMap.get("productCatId");

			newProductCategory = productManagerService
					.getProductCategoryById(productCatId);
			logger.info("Successfully Got the editable Product CAtegory::");
		} catch (Exception exp) {
			logger.error(
					"Error while editing the product category record for ID :::"
							+ productCatId, exp);

		}

	}

	/**
	 * Responsible for deleting selected Product Category
	 * 
	 * @param event
	 */
	public void deleteSelectedProductCategory(ActionEvent event) {

		try {
			logger.info("deleting  Product Category::::");
			Map<String, String> reqMap = (Map<String, String>) FacesContext
					.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			this.productCatId = reqMap.get("productCatId");

			productManagerService.deleteProductCategoryById(productCatId);
			logger.info("Successfully deleted Product CAtegory::");
		} catch (Exception exp) {
			logger.error(
					"Error while Deliting the product category record for ID :::"
							+ productCatId, exp);

		}

	}

	/**
	 * @author bomvenka This Method is responsible for generating Product
	 *         Category ID and navigating to newProductCategory Screen
	 */
	public void generateNewProductCategoryID() {

		try {
			logger.info("Generating the new Product Category ID");
			if (newProductCategory.getCategory() == null) {
				String pID = null;
				pID = "C"
						+ productManagerService.getNextProductCategoryId()
								.intValue();
				newProductCategory.setCategory(pID);
			}
			logger.info("Successfully Generated the Product Category ID");
		} catch (Exception exp) {
			logger.error("Error While getting the new Product Category ID", exp);
		}

	}

	public void updateProductCategory() {

		try {
			logger.info("updating the new Product Category :::");

			productManagerService.updateProductCategory(newProductCategory);

			logger.info("Successfully updated the new Product Category :::");
		} catch (Exception exp) {
			logger.error("Error While updating Product Category ", exp);

		}

	}

	/**
	 * @author bomvenka This Method is responsible for generating Product ID and
	 *         navigating to newProduct Screen
	 */
	public void getProductCategoryToView() {

		try {
			logger.info("viewing the  Product Category :::");
			Map<String, String> reqMap = (Map<String, String>) FacesContext
					.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			this.productCatId = reqMap.get("id");

			if (productCatId != null && productCatId.length() > 0) {
				newProductCategory = productManagerService
						.getProductCategoryById(productCatId);
				setShowUpdate(false);
			}

		} catch (Exception exp) {
			logger.error("Error While viewing the Product Category", exp);
		}

	}

}
