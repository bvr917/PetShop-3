package com.cg.petshop.jsf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.Order;
import com.cg.petshop.core.entitybean.OrderDetails;
import com.cg.petshop.core.entitybean.OrderSummary;
import com.cg.petshop.core.entitybean.Product;
import com.cg.petshop.service.BuyProductService;
import com.cg.petshop.service.IBuyProductService;
import com.cg.petshop.service.ProductSearch;
import com.cg.petshop.service.productSearchImpl;

/**
 * This Class is Responsible for Buying the Products,Adding to the Cart,Checkout the Order 
 * @author bomvenka
 *
 */
@ManagedBean(name="buyOrCheckOut")
@SessionScoped
public class BuyOrCheckoutOrder {
	
	@EJB
	ProductSearch productService;
	
	@EJB
	IBuyProductService buyProductSerive;
	
	final static Logger logger = Logger.getLogger(productSearchImpl.class.getName());

	
	private int quantity;
	private String orderCost;
	public String getOrderCost() {
		return orderCost;
	}
	
	boolean isFromOrderSummary = false;
	public boolean isFromOrderSummary() {
		return isFromOrderSummary;
	}

	public void setFromOrderSummary(boolean isFromOrderSummary) {
		this.isFromOrderSummary = isFromOrderSummary;
	}

	public void setOrderCost(String orderCost) {
		this.orderCost = orderCost;
	}

	private Set<OrderDetails> productsInCart;
	private int noOfProductsInCart = 0; 

	Set<OrderDetails> boughtProductDetails;
	

	public void setBoughtProductDetails(Set<OrderDetails> boughtProductDetails) {
		this.boughtProductDetails = boughtProductDetails;
	}

	/**
	 * This Method is responsible for getting the number of products in Cart
	 * @return No Of Products In Cart
	 */
	public int getNoOfProductsInCart() {
		
		logger.info("Getting the Number of Products In the Cart");
		try{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
		productsInCart = (Set<OrderDetails>)session.getAttribute("PRODUCTS_IN_CART");
		
		if(productsInCart!=null)
			noOfProductsInCart= productsInCart.size();
		
		}
		catch(Exception exp){
			logger.error("Error While getting the Number of Products in the cart::", exp);
		}
		return noOfProductsInCart;
	}

	public void setNoOfProductsInCart(int noOfProductsInCart) {
		this.noOfProductsInCart = noOfProductsInCart;
	}

	
	/**
	 * This is responsible for adding Product to Cart and Showing Home Page to User
	 * @return next Page
	 */
	public String addProductToCart(){
		
		logger.info("Adding a Product To Cart Strats ::::");
		FacesContext context = FacesContext.getCurrentInstance();

		try{
	        String selectedProductID = (String) context.getExternalContext().getRequestParameterMap().get("productId");
	        
	        Product product = productService.getProductById(selectedProductID);

			logger.debug("Selected Product to Add to Cart:::: "+selectedProductID);
			
			OrderDetails details = new OrderDetails();
			details.setProduct_id(selectedProductID);
			details.setPrd_name(product.getName());
			details.setPrice_per_product(orderCost);
			details.setPrd_quantity(Integer.toString(quantity));
			details.setIndividual_prd_price(Integer.toString(product.getPrice().intValue()));
			
			HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
			
			productsInCart = (Set<OrderDetails>)session.getAttribute("PRODUCTS_IN_CART");
			
			if(productsInCart==null)
				productsInCart = new HashSet<OrderDetails>();

			productsInCart.add(details);
			
			session.setAttribute("PRODUCTS_IN_CART", productsInCart);

			logger.info("Adding a Product To Cart Ends ::::");
			
		}
		catch(Exception exp){
			logger.error("Exception in Adding Product to Cart :::" ,exp);
			
		}
		
		return "User_Home";
		
		
		
	}
	/**
	 * This is responsible for buying the Products
	 * @return
	 */
	public Collection<OrderDetails> getBoughtProductDetails(){
		
		logger.info("Buying the Product::: Starts:::");
		
		Collection<OrderDetails> ordersInCart=null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		try{
			HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
			
			String order_id = (String)context.getExternalContext().getRequestParameterMap().get("order_id");

				if(order_id!=null && order_id.length()>0){
					
					ordersInCart = buyProductSerive.getOrderSummaryByUser(order_id);
					isFromOrderSummary = true;
					
				}
				else{
					
					String productID = (String)context.getExternalContext().getRequestParameterMap().get("product_id");
					
					if(productID!=null && productID.length()>0){
						Product product = productService.getProductById(productID);
						OrderDetails details = new OrderDetails();
						details.setProduct_id(productID);
						details.setPrd_name(product.getName());
						details.setPrice_per_product(orderCost);
						details.setPrd_quantity(Integer.toString(quantity));
						details.setIndividual_prd_price(Integer.toString(product.getPrice().intValue()));
						ordersInCart = new ArrayList<OrderDetails>();
						ordersInCart.add(details);
						isFromOrderSummary = false;
						
					}
					else{
					ordersInCart = (Set<OrderDetails>)session.getAttribute("PRODUCTS_IN_CART");	
					isFromOrderSummary = false;
					}
				}
			
			
				logger.info("Buying the Product::: Ends:::");
			
		}
		catch(Exception exp){
			logger.error("Exception in Getting the Bought Product Details Product to Cart :::" ,exp);
		}
		
		return ordersInCart;
		
	}
	
	
	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Set<OrderDetails> getProductsInCart() {
		return productsInCart;
	}

	public void setProductsInCart(Set<OrderDetails> productsInCart) {
		this.productsInCart = productsInCart;
	}

	
	

}
