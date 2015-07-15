package com.cg.petshop.jsf;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.core.entitybean.ProductSummary;
import com.cg.petshop.service.ProductManagerService;
import com.cg.petshop.service.ProductSearch;

import javax.servlet.http.Part;  



/**
 * This Class is responsible for Managing Actions on Products(Edit,New) and
 * Showing Products Summary to  Admin User
 * @author bomvenka
 *
 */
	
@ManagedBean(name="productManagerBean", eager=true)
@RequestScoped
public class ProductManagerBean {
	
	@ManagedProperty(value="#{newProduct}")
	protected Product newProduct;
	

	private Part image; 
    
    public Part getImage() {
		return image;
	}

    public void setImage(Part image) {
		this.image = image;
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

	public boolean isCancelShow=false;
	
	

	

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
		
		try{
			
			if((productName!=null && !"".equals(productName)) || (productCategory!=null && !"0".equals(productCategory))){
				
				availableProducts = productSearchService.rtrvAllAvailableProducts(productCategory,productName);
			}
			else{
				availableProducts = productSearchService.rtrvAllAvailableProducts();
			}
			
			
			
			
		}catch(Exception exp){
			
			System.out.println(exp);
		}
		
		return availableProducts;
	}
	
	
	public String goToNewProduct(){

		
		return "newProduct";
	}

	public String addNewProduct(){
		
		try{
			
			byte[] imageData = getUploadedImageSteam();
			newProduct.setImage(imageData);
			productManagerService.saveNewProduct(newProduct);
			newProduct = new Product();
		}
		catch(Exception exp){
			System.out.println(exp);
			
		}
		return "newProduct";
	}
	


	public String goToNewProductCategory(){
		
		return "newProductCategory";
	}
	
	
	public void editSelectedProduct(ActionEvent actionEvent){
		
		
		try{
	        this.productId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");

			newProduct = productManagerService.getProductById(productId);
			
		}
		catch(Exception exp){
			
		}
		
	}
	
	public void editProduct(){
		
		try{
			System.out.println("updating the new Product :::");
			
			productManagerService.updateProduct(newProduct);
		}
		catch(Exception exp){
			System.out.println();
			
		}
		
	}
	
	public String deleteSelectedProduct(ActionEvent actionEvent){
		
		
		try{
	        this.productId = (String) ((UIParameter)actionEvent.getComponent().getFacet("extraParameter")).getValue();

			productManagerService.deleteProductById(productId);
			
		}
		catch(Exception exp){
			
			System.out.println(exp);
		}
		
		return "deleteProduct";
		
	}
	
	/**
	 * @author bomvenka
	 * This Method is responsible for generating Product ID and navigating to newProduct Screen 
	 */
	public void generateNewProductID(){
		
		try{
			
			if(newProduct.getProductId()==null){
				String pID = null;
				pID = "P" + productManagerService.getNextProductId().intValue();
				newProduct.setProductId(pID);
			}
			
		}
		catch(Exception exp){
			
		}
		
	}
	
	/**
	 * @author bomvenka
	 * This Method is responsible for generating Product ID and navigating to newProduct Screen 
	 */
	public void productView(){
		
		try{
			
			Map<String,String> reqMap = (Map<String,String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			this.productId=reqMap.get("id");
				
			if(productId!=null && productId.length()>0)
				newProduct=productManagerService.getProductById(productId);
			
		}
		catch(Exception exp){
			
		}
		
	}
	
	/**
	 * This Method is responsible for getting the uploaded image file name
	 * @param uploadedImage
	 * @return Uploaded Image File Name
	 */
	private String getFileName(Part uploadedImage){
		
		String filename = null;

		try{
			for (String cd : uploadedImage.getHeader("content-disposition").split(";")) {  
	            if (cd.trim().startsWith("filename")) {  
	            	filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
	            }  
	        }
			return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			
		}
		catch(Exception exp){
			
			System.out.println("Exception while getting the File Name :::"+exp);
			
		}
		
		return filename;
	}
	
	private byte[] getUploadedImageSteam(){
		
		byte[] imageBytes = null;         

		try{
			
			InputStream inSteam = image.getInputStream();
			FileOutputStream fOutStream = new FileOutputStream(getFileName(image));
			imageBytes =new byte[4096]; 
	        int bytesRead = 0;  
	        while(true) {                          
	            bytesRead = inSteam.read(imageBytes);  
	            if(bytesRead > 0) {  
	            	fOutStream.write(imageBytes, 0, bytesRead);  
	            }else {  
	                break;  
	            }                         
	        }  
	        inSteam.close();  
	        fOutStream.close();
			
		}
		catch(Exception exp){
			System.out.println("Exception while uploading the Image Stream123 ::: "+exp);
		}
		
		return imageBytes;
		
	}
	
}
