package com.cg.petshop.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.cg.petshop.core.entitybean.Order;
import com.cg.petshop.core.entitybean.OrderDetails;

@Stateless
public class BuyProductService implements IBuyProductService {

	
	
	@PersistenceContext(unitName = "defaultPersistenceUnit")
    private EntityManager entityManager;
	final static Logger logger = Logger.getLogger(BuyProductService.class.getName());
	
	
	/**
	 * This Method Returns the New OrderID
	 */
	public String getNextOrderID() {

		BigInteger generatedValue=null;
		try{
			Query query = entityManager.createNativeQuery("select nextval('PSM_ORDER_SEQUENCE')");
			generatedValue = (BigInteger)query.getSingleResult();
			
		}
		catch(Exception exp){
			System.out.println(exp);
			
		}
		return "ORD"+generatedValue.toString();
	}

	/**
	 * This is responsible for getting the Total cost of the Order
	 */
	public BigInteger getTotalOrderCost(Set<OrderDetails> orderDetails) {
		
		int totalPrice=0;
		logger.debug("getting total cost of the order:::::");
		try{
			
			if(orderDetails!=null){
				
				for(OrderDetails order : orderDetails)
				{
					totalPrice=totalPrice + (Integer.parseInt(order.getPrice_per_product()));
				}
				
			}
		}
		catch(Exception exp){
			logger.error("Error While getting the total cost of the product::::", exp);
		}
		
		return new BigInteger(Integer.toString(totalPrice));
		
		
	}
	
	public void createNewOrder(Order order){
		
		logger.debug("Placing an new Order:::::");
		
		try{
			
			entityManager.persist(order);
		}
		catch(Exception exp){
			
			logger.error("Error While Saving the Product::::", exp);
			
		}
		
	}
	
	public void createNewOrderDetails(OrderDetails order){
		
		logger.debug("Placing an new Order:::::");
		
		try{
			
			entityManager.persist(order);
		}
		catch(Exception exp){
			
			logger.error("Error While Saving the Product::::", exp);
			
		}
		
	}
	
	
	public List<Order> getOrdersByUser(String userId){
		
		logger.debug("Getting Orders By User:::::");
		
		List<Order> orderList= null;
		try{
			
			Query query = entityManager.createNativeQuery("select * from pst_order_master where user_id='"+userId+"' order by order_id desc;",Order.class);
			
			orderList = (List<Order>)query.getResultList();
		}
		catch(Exception exp){
			
			logger.error("Error While getting the Order List for User::::", exp);
			
		}
		
		return orderList;
		
	}
	
	public List<OrderDetails> getOrderSummaryByUser(String order_id){
		
		logger.debug("Getting OrderSummary By User:::::");
		
		List<OrderDetails> orderList= null;
		try{
			
			Query query = entityManager.createNativeQuery("select * from PST_ORder_detail where order_id='"+order_id+"' order by order_id desc;",OrderDetails.class);
			
			orderList = (List<OrderDetails>)query.getResultList();
		}
		catch(Exception exp){
			
			logger.error("Error While getting the OrderSummary List for User::::", exp);
			
		}
		
		return orderList;
		
	}
	
}
