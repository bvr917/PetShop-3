package com.cg.petshop.jsfbean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.cg.petshop.User;
import com.cg.petshop.service.ILoginController;
import com.cg.petshop.service.productSearchImpl;

/**
 * This Class is Responsible for Managing the Logged in User Session
 * 
 * @author bomvenka
 *
 */

@ManagedBean(name = "userRegister")
@RequestScoped
public class UserRegistrationManagerBean {

	private User newUser;
	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	@EJB
	ILoginController serviceObj;

	@PostConstruct
	public void init() {

		newUser = new User();

	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	private boolean isUserRegistered = false;

	public boolean getUserRegistered() {
		return isUserRegistered;
	}

	public void setUserRegistered(boolean isUserRegistered) {
		this.isUserRegistered = isUserRegistered;
	}

	private boolean userRegister = false;

	public boolean isUserRegister() {
		return userRegister;
	}

	public void setUserRegister(boolean userRegister) {
		this.userRegister = userRegister;
	}

	/**
	 * Registering New User
	 * 
	 * @return Register String
	 */
	public String registerNewUser() {

		logger.info("Registering New User Starts:::::");

		try {
			String role = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap().get("Role");
			newUser.setRole(role);
			serviceObj.saveNewUser(newUser);
			userRegister = true;

			logger.info("Registering New User Ends:::::" + newUser.getUser_id());
		} catch (Exception exp) {
			logger.error("Error While Registering New User:::::", exp);

		}
		return "Register";

	}

}
