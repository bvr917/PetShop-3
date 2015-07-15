package com.cg.petshop.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;
import com.cg.petshop.core.entitybean.ProductSummary;

/**
 * Session Bean implementation class ManageEmployeeBean
 */
@Stateless
public class productSearchImpl implements ProductSearch {

	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager entityManager;
	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	/**
	 * Retrieves the All Available Products to Show to the Admin
	 * 
	 * @return Array Of ProductSummary (View)Objects
	 */
	public ProductSummary[] rtrvAllAvailableProducts() {

		ProductSummary[] availableProducts;

		logger.info("Getting the Available Products Starts::::");
		try {

			Query query = entityManager
					.createQuery("SELECT p FROM ProductSummary p order by p.productId desc");

			List<ProductSummary> products = (List<ProductSummary>) query
					.getResultList();

			logger.info("Available Products Size::::" + products.size());

			availableProducts = new ProductSummary[products.size()];

			availableProducts = products.toArray(availableProducts);

			logger.info("Success Fully Retrived the Products::::"
					+ availableProducts.length);
			return availableProducts;

		} catch (Exception exp) {
			logger.error("Error While Getting the Available Products", exp);
			return null;
		}

	}

	/**
	 * Retrieves the All Available Product Categories to Show to the Admin
	 * 
	 * @return
	 */
	public ProductCategory[] rtrvProductCategories() {

		ProductCategory[] availableProductCategories;
		logger.info("Getting the Available Product Categories Starts::::");
		try {
			Query query = entityManager
					.createQuery("SELECT e FROM ProductCategory e order by e.category asc");
			List<ProductCategory> productCategoriesList = (List<ProductCategory>) query
					.getResultList();

			logger.info("Available Product Categories Size::::"
					+ productCategoriesList.size());
			availableProductCategories = new ProductCategory[productCategoriesList
					.size()];

			availableProductCategories = productCategoriesList
					.toArray(availableProductCategories);

			logger.info("Success Fully Retrived the Product Categories::::"
					+ availableProductCategories.length);

			return availableProductCategories;
		} catch (Exception exp) {
			logger.error(
					"Error While Getting the Available Product Categories", exp);
			return null;

		}

	}

	/**
	 * Responsible for retrieving the all products by search criteria
	 * 
	 * @return Returns the ProductSummary Array depending on Search critiria
	 */
	public ProductSummary[] rtrvAllAvailableProducts(String category,
			String name) {

		ProductSummary[] availableProducts;
		logger.info("Getting the Products By search critiria starst::::");
		try {
			String queryString = null;

			if (name != null && name.length() != 0)
				queryString = "SELECT e FROM ProductSummary e WHERE e.name LIKE '%"
						+ name + "%' and e.categoryID='" + category + "'";
			else
				queryString = "SELECT e FROM ProductSummary e WHERE  e.categoryID='"
						+ category + "'";

			logger.debug("Query String is ::" + queryString);

			Query query = entityManager.createQuery(queryString);

			List<ProductSummary> products = (List<ProductSummary>) query
					.getResultList();

			logger.debug("Product List is " + products);

			availableProducts = new ProductSummary[products.size()];

			availableProducts = products.toArray(availableProducts);
			logger.info("Success Fully Retrived the Product ::::"
					+ availableProducts.length);

			return availableProducts;

		} catch (Exception exp) {
			logger.error("Error While Getting the Available Product by search",
					exp);
			return null;
		}

	}

	public List<Product> getAllProductsList() {

		List<Product> productsList = null;
		try {

			Query query = entityManager
					.createQuery(" SELECT e FROM Product e order by e.category asc");
			productsList = query.getResultList();

		} catch (Exception exp) {
			logger.error("Error While getting the Product Product List", exp);

		}

		return productsList;
	}

	/**
	 * Getting the Product Object by product ID
	 * 
	 * @param Product
	 *            ID
	 * @return Product Object
	 */
	public Product getProductById(String id) {

		Product prdObject = null;
		logger.info(" Retriving the Product Object by product ID:::");
		try {

			prdObject = entityManager.find(Product.class, id);
			logger.info(" Successfully retrivied the Product Object:::");
		} catch (Exception exp) {
			logger.error("Error While getting the Product Object by ID", exp);
		}

		return prdObject;
	}

}
