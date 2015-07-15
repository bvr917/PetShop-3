/**
 * 
 */
package com.cg.petshop.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import com.cg.petshop.core.entitybean.Order;
import com.cg.petshop.core.entitybean.OrderDetails;
import com.cg.petshop.core.entitybean.OrderSummary;

/**
 * @author bomvenka
 *
 */
@Local
public interface IBuyProductService {
	
	
	public String getNextOrderID();
	public BigInteger getTotalOrderCost(Set<OrderDetails> orderDetails);
	public void createNewOrder(Order order);
	public void createNewOrderDetails(OrderDetails order);
	
	public List<Order> getOrdersByUser(String userID);
	public List<OrderDetails> getOrderSummaryByUser(String order_id);
	

}
