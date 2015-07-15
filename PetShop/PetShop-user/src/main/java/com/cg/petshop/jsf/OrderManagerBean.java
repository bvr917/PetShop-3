package com.cg.petshop.jsf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;

import com.cg.petshop.User;
import com.cg.petshop.core.entitybean.Order;
import com.cg.petshop.core.entitybean.OrderDetails;
import com.cg.petshop.jsfbean.LoginManagerBean;
import com.cg.petshop.service.IBuyProductService;
import com.cg.petshop.service.productSearchImpl;

@ManagedBean(name = "orderManager")
@SessionScoped
public class OrderManagerBean {

	private String email;
	private String phoneNumber;
	private String address;
	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	@ManagedProperty(value = "#{loginController}")
	private LoginManagerBean loginManager;

	@EJB
	IBuyProductService buyProductSerive;

	private List<Order> userOrders;

	public void setUserOrders(List<Order> userOrders) {
		this.userOrders = userOrders;
	}

	/**
	 * Confirming the Order: Getting the Delivery Address Details if User Logged
	 * IN.
	 */
	public void confirmOrder() {
		logger.info("Confirming The Order Starts::::::");

		try {

			User loggedInUser = loginManager.getUserObj();

			if (loggedInUser != null) {

				email = loggedInUser.getEmail();
				phoneNumber = loggedInUser.getPhone_number();
				address = loggedInUser.getAddress();

			}

			logger.info("Confirming The Order Ends::::::");
		} catch (Exception exp) {

			logger.error("Error while Confirming The Order", exp);
		}

	}

	/**
	 * Buying the Product: adding new Row in PST_ORDER_MASTER and Adding Product
	 * Details in PST_ORDER_DETAILS
	 */
	public void buyOrder() {

		logger.info("CheckOut Order Starts::::::");

		Set<OrderDetails> confirmOrders = null;
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(false);
			confirmOrders = (Set<OrderDetails>) session
					.getAttribute("PRODUCTS_IN_CART");

			String orderID = buyProductSerive.getNextOrderID();


			if (confirmOrders != null && !confirmOrders.isEmpty()) {

				for (OrderDetails order : confirmOrders)

				{
					order.setOrder_id(orderID);
					buyProductSerive.createNewOrderDetails(order);

				}
			}

			Order newOrder = new Order();

			newOrder.setOrder_id(orderID);
			newOrder.setOrder_status("NEW");

			if (loginManager.getUserObj() != null)
				newOrder.setUser_id(loginManager.getUserObj().getUser_id());

			newOrder.setOrder_price(buyProductSerive
					.getTotalOrderCost(confirmOrders));

			newOrder.setEmail(email);
			newOrder.setAddress(address);
			newOrder.setPhone_number(phoneNumber);

			buyProductSerive.createNewOrder(newOrder);

			session.setAttribute("PRODUCTS_IN_CART", null);
			logger.info("CheckOut Order Ends::::::");

		} catch (Exception exp) {

			logger.error("Error While Saving New Order:::", exp);
		}

	}

	public String showOrderDetails() {
		return "User_Orders";
	}

	public List<Order> getUserOrders() {

		logger.info("Getting Orders For User:::");

		try {

			userOrders = buyProductSerive.getOrdersByUser(loginManager
					.getUserObj().getUser_id());

		} catch (Exception exp) {
			logger.error("Error While Getting the Order By User:::::", exp);

		}

		return userOrders;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LoginManagerBean getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManagerBean loginManager) {
		this.loginManager = loginManager;
	}

}
