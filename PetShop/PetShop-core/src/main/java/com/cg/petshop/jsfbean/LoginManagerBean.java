/**
 * 
 */
package com.cg.petshop.jsfbean;

import java.util.Collection;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cg.petshop.User;
import com.cg.petshop.service.ILoginController;
import com.cg.petshop.service.productSearchImpl;

/**
 * @author bomvenka
 *
 *         This class is responsible for managing the Login for both
 *         administrator and user
 */

@ManagedBean(name = "loginController", eager = true)
@SessionScoped
public class LoginManagerBean {

	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	protected String user_name;
	protected String password;

	boolean loginStatusFailed = false;

	@EJB
	protected ILoginController LoginControllerService;

	private User loggedInUserObj = null;
	private boolean userLoggedIn = false;

	public User getUserObj() {
		return loggedInUserObj;
	}

	public void setUserObj(User userObj) {
		this.loggedInUserObj = userObj;
	}

	public User getLoggedInUserObj() {
		return loggedInUserObj;
	}

	public void setLoggedInUserObj(User loggedInUserObj) {
		this.loggedInUserObj = loggedInUserObj;
	}

	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}

	public void setUserLoggedIn(boolean userLoggedIn) {
		this.userLoggedIn = userLoggedIn;
	}

	/**
	 * Validates The User Credentials
	 *
	 * @param
	 * @return String 'Success' for successful Login ,Failed for Invalid Login
	 */
	public String validateUser() {

		String result = "Failed";
		logger.info("Validating the User Starts:: ");
		try {
			if (user_name == null || user_name.length() == 0
					|| password == null || password.length() == 0) {

				logger.debug("Password Or UserName is null");
				result = "Failed";
			} else {

				User userObj = new User();
				userObj.setUser_id(user_name);
				userObj.setPassword(password);

				logger.debug("Before Calling the Service Validate User::: "
						+ result);

				result = LoginControllerService.validateUser(userObj);
				logger.debug("After Calling the Service Validate User::: "
						+ result);

				if (result != null && "Failed".equals(result)) {
					loginStatusFailed = true;
				} else if (result != null && "Success".equals(result)) {
					loggedInUserObj = LoginControllerService
							.getUserById(user_name);
					userLoggedIn = true;

				}
			}

		} catch (Exception exp) {

			logger.error("Error While validating the User:::", exp);

		}
		return result;

	}

	/**
	 * This Method is responsible for Logging out the User and invalidating the
	 * Existing User Session.
	 * 
	 * @return
	 */
	public String logOutUser() {

		logger.info("Logging Out The User:::" + user_name);

		try {
			setUserLoggedIn(false);
			setUserObj(null);
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.invalidate();
		} catch (Exception exp) {

			logger.error("Error While Logging Out the User:::::", exp);

		}
		return "Success";
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginStatusFailed() {
		return loginStatusFailed;
	}

	public void setLoginStatusFailed(boolean loginStatusFailed) {
		this.loginStatusFailed = loginStatusFailed;
	}

}
