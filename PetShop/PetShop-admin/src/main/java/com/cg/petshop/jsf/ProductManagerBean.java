package com.cg.petshop.jsf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductSummary;
import com.cg.petshop.service.ProductManagerService;
import com.cg.petshop.service.ProductSearch;
import com.cg.petshop.service.productSearchImpl;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * This Class is responsible for Managing Actions on Products(Edit,New) and
 * Showing Products Summary to Admin User
 * 
 * @author bomvenka
 *
 */

@ManagedBean(name = "productManagerBean", eager = true)
@SessionScoped
public class ProductManagerBean {

	public ProductManagerBean() {
		newProduct = new Product();
	}

	protected Product newProduct;

	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());
	String imagePath = "D:\\venkat\\images\\product\\";

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	private Part image;

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;
	}

	StreamedContent imageSource;

	public StreamedContent getImageSource() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {

			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				return new DefaultStreamedContent();
			} else {
				String productId = (String) FacesContext.getCurrentInstance()
						.getExternalContext().getRequestParameterMap()
						.get("pID");
				InputStream inputStream = new FileInputStream(imagePath
						+ productId + ".jpg");
				imageSource = new DefaultStreamedContent(inputStream,
						"image/png");
			}
		} catch (Exception exp) {
			logger.error("Error While getting Image Source:::", exp);
		}

		return imageSource;
	}

	public void setImageSource(StreamedContent imageSource) {
		this.imageSource = imageSource;
	}

	private String productCategory;

	public String getProductCategory() {
		return productCategory;
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

	public boolean isCancelShow = false;

	public boolean isCancelShow() {
		return isCancelShow;
	}

	public void setCancelShow(boolean isCancelShow) {
		this.isCancelShow = isCancelShow;
	}

	protected ProductSummary[] availableProducts;
	protected String productId;

	@EJB
	protected ProductManagerService productManagerService;

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

			logger.error("Error While getting available products:::", exp);
		}

		return availableProducts;
	}

	public String goToNewProduct() {

		return "newProduct";
	}

	/**
	 * Adding the New Product
	 * 
	 * @return Return String to show new Product Screen
	 */
	public String addNewProduct() {

		try {
			logger.info("Adding the New Product:::");
			byte[] imageData = getUploadedImageSteam(newProduct.getProductId());
			newProduct.setImage(imageData);
			productManagerService.saveNewProduct(newProduct);
			newProduct = new Product();
			logger.info("Successfully Added  New Product:::");
		} catch (Exception exp) {
			logger.error("Error While adding new Product:::", exp);

		}
		return "newProduct";
	}

	public String goToNewProductCategory() {

		return "newProductCategory";
	}

	/**
	 * Adding Existing Product
	 * 
	 * @param actionEvent
	 */
	public void editSelectedProduct(ActionEvent actionEvent) {

		try {
			this.productId = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("productId");

			newProduct = productManagerService.getProductById(productId);

			imagePath = imagePath + newProduct.getProductId() + ".jpg";

		} catch (Exception exp) {
			logger.error("Error While editing the select Product::", exp);
		}

	}

	/**
	 * Updating modifies Product:::
	 */
	public void editProduct() {

		try {
			logger.info("updating the new Product :::");

			productManagerService.updateProduct(newProduct);
		} catch (Exception exp) {
			logger.error("Error While editing the Product::", exp);

		}

	}

	/**
	 * Deleting the Product
	 * 
	 * @param actionEvent
	 * @return
	 */
	public String deleteSelectedProduct(ActionEvent actionEvent) {

		try {
			logger.info("deleting the new Product :::");
			this.productId = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("productId");

			productManagerService.deleteProductById(productId);

		} catch (Exception exp) {

			logger.error("Error While deleting the select Product::", exp);
		}

		return "deleteProduct";

	}

	/**
	 * @author bomvenka This Method is responsible for generating Product ID and
	 *         navigating to newProduct Screen
	 */
	public void generateNewProductID() {

		try {
			logger.info("generating the new Product ID :::");
			if (newProduct.getProductId() == null) {
				String pID = null;
				pID = "P" + productManagerService.getNextProductId().intValue();
				newProduct.setProductId(pID);
			}

		} catch (Exception exp) {
			logger.error("Error While generating Product ID", exp);

		}

	}

	/**
	 * @author bomvenka This Method is responsible for generating Product ID and
	 *         navigating to newProduct Screen
	 */
	public void productView() {

		try {

			Map<String, String> reqMap = (Map<String, String>) FacesContext
					.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			this.productId = reqMap.get("id");

			if (productId != null && productId.length() > 0) {
				newProduct = productManagerService.getProductById(productId);
				HttpSession session = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(false);
				InputStream inputStream = new ByteArrayInputStream(
						newProduct.getImage());
				imageSource = new DefaultStreamedContent(inputStream);
				session.setAttribute("IMAGE_SOURCE", imageSource);
			}

		} catch (Exception exp) {
			logger.error("Error While viewing Product ", exp);
		}

	}

	/**
	 * This Method is responsible for getting the uploaded image file name
	 * 
	 * @param uploadedImage
	 * @return Uploaded Image File Name
	 */
	private String getFileName(Part uploadedImage) {

		String filename = null;

		try {
			for (String cd : uploadedImage.getHeader("content-disposition")
					.split(";")) {
				if (cd.trim().startsWith("filename")) {
					filename = cd.substring(cd.indexOf('=') + 1).trim()
							.replace("\"", "");
				}
			}
			return filename.substring(filename.lastIndexOf('/') + 1).substring(
					filename.lastIndexOf('\\') + 1);

		} catch (Exception exp) {

			logger.error("Exception while getting the File Name :::", exp);

		}

		return filename;
	}

	private byte[] getUploadedImageSteam(String pID) {

		byte[] imageBytes = null;
		logger.info("SAving the Uploaded image in File System Path::");

		try {

			InputStream inSteam = image.getInputStream();
			FileInputStream fOutStream = new FileInputStream(getFileName(image));

			BufferedImage image = ImageIO.read(fOutStream);
			ImageIO.write(image, "jpg", new File("D:/venkat/images/product/",
					pID + ".jpg"));

			inSteam.close();
			fOutStream.close();
			logger.info("Successfully Saved the Image");

		} catch (Exception exp) {
			logger.error("Exception while uploading the Image Stream ::: ", exp);
		}

		return imageBytes;

	}

}
