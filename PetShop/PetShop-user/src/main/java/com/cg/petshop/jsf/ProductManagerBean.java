package com.cg.petshop.jsf;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductCategory;
import com.cg.petshop.core.entitybean.ProductSummary;
import com.cg.petshop.service.ProductSearch;
import com.cg.petshop.service.productSearchImpl;

/**
 * This Class is responsible for Managing Actions on Products(Edit,New) and
 * Showing Products Summary to Admin User
 * 
 * @author bomvenka
 *
 */

@ManagedBean(name = "productManagerBean")
@SessionScoped
public class ProductManagerBean {

	// @ManagedProperty(value="#{newProduct}")
	protected Product newProduct;

	@PostConstruct
	public void init() {
		newProduct = new Product();

	}

	String imagePath = "D:\\venkat\\images\\product\\";
	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());
	private String productCategory;

	public String getProductCategory() {
		return productCategory;
	}

	private String quantity;
	private String[] quantityDropdown;

	private int inQuantity;

	public int getInQuantity() {
		return inQuantity;
	}

	public void setInQuantity(int inQuantity) {
		this.inQuantity = inQuantity;
	}

	public void setQuantityDropdown(String[] quantityDropdown) {
		this.quantityDropdown = quantityDropdown;
	}

	private int totalPrice;

	public int getTotalPrice() {

		return totalPrice;
	}

	public void calculatePrice(ValueChangeEvent e) {

		int quan = Integer.parseInt((String) e.getNewValue());

		totalPrice = quan * (newProduct.getPrice().intValue());
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private String productName;

	public Product getNewProduct() {

		return newProduct;
	}

	public void setNewProduct(Product newProduct) {
		this.newProduct = newProduct;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	protected ProductSummary[] availableProducts;
	protected ProductCategory[] availableProductCategories;

	public void setAvailableProductCategories(
			ProductCategory[] availableProductCategories) {
		this.availableProductCategories = availableProductCategories;
	}

	protected String productId;

	@EJB
	protected ProductSearch productSearchService;

	public void setAvailableProducts(ProductSummary[] availableProducts) {
		this.availableProducts = availableProducts;
	}

	public ProductSummary[] getAvailableProducts() {

		try {

			if ((productName != null && !"".equals(productName))
					|| (productCategory != null && !"C00"
							.equals(productCategory))) {

				availableProducts = productSearchService
						.rtrvAllAvailableProducts(productCategory, productName);
			} else {
				availableProducts = productSearchService
						.rtrvAllAvailableProducts();
			}

		} catch (Exception exp) {

			logger.error(
					"Error while getting the available Product Categories", exp);
		}

		return availableProducts;
	}

	public String goToNewProduct() {

		return "newProduct";
	}

	public String goToNewProductCategory() {

		return "newProductCategory";
	}

	/**
	 * @author bomvenka This Method is responsible for Products Search
	 */
	public String searchForProduct() {

		return "User_Home";

	}

	public ProductCategory[] getAvailableProductCategories() {
		logger.info("Inside getting Categories");

		availableProductCategories = productSearchService
				.rtrvProductCategories();
		return availableProductCategories;

	}

	/**
	 * Getting the Images for the available products to show to User
	 * 
	 * @return
	 */
	public StreamedContent[] listOfImages() {

		StreamedContent[] imgaesList = null;
		logger.info("Getting the list Of available product Images starts");
		try {

			List<Product> productList = productSearchService
					.getAllProductsList();
			int i = 0;
			for (Product prod : productList) {
				imgaesList = new StreamedContent[productList.size()];
				if (prod.getImage() != null) {
					InputStream imageStream = new ByteArrayInputStream(
							prod.getImage());
					StreamedContent imageSource = new DefaultStreamedContent(
							imageStream);
					imgaesList[i] = imageSource;
					i++;
				}
			}
			logger.info("Getting the list Of available product Images ends ");
		} catch (Exception exp) {
			logger.error("Exception in getting the images :: ", exp);

		}

		return imgaesList;
	}

	public StreamedContent getStreamContentByID() {
		logger.info("Getting the Stream for the Product Image starts");
		StreamedContent image = null;
		FacesContext context = FacesContext.getCurrentInstance();
		try {

			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				return new DefaultStreamedContent();
			} else {
				String productId = (String) FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap()
						.get("productId");
				InputStream imageStream = new FileInputStream(imagePath
						+ productId + ".jpg");
				image = new DefaultStreamedContent(imageStream, "image/png");
			}
			logger.info("Successfully set the Stream for the Product Image starts");
		} catch (Exception exp) {
			logger.error("Exception in getting the Stream :::", exp);
		}

		return image;
	}

	public String ShowProductDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		logger.info("Getting The Product details:::::");
		try {

			String productId = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap().get("pID");
			if (productId != null && productId.length() > 0)
				newProduct = productSearchService.getProductById(productId);

		} catch (Exception exp) {
			logger.error("Exception in getting the Product Details :::", exp);
		}

		return "Product_Details";

	}

	/**
	 * This Method is responsible for getting the Products by Category
	 * 
	 * @return
	 */
	public String searchProductsByCategory() {

		logger.info("Searching For a Product by Category Starts Here:::::");

		try {

			String prodCat = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap().get("PC");
			setProductCategory(prodCat);
			logger.debug("Searchin For Product Category:::" + prodCat);
		} catch (Exception exp) {
			logger.error("Error While Searching the Products By Category", exp);

		}

		return "User_Home";
	}

	public String[] getQuantityDropdown() {

		logger.info("Getting the Quantity Drop down depending on available Quaqntity::::");

		try {

			if (newProduct != null) {
				quantityDropdown = new String[newProduct.getQuantity()];
				for (int i = 0; i < newProduct.getQuantity(); i++) {
					quantityDropdown[i] = Integer.toString(i + 1);

				}
			}

		} catch (Exception exp) {
			logger.error("Error While getting the Quantity dropdown", exp);
		}

		return quantityDropdown;

	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
