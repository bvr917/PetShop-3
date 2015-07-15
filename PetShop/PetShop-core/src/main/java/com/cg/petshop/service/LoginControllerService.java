package com.cg.petshop.service;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.cg.petshop.User;

@Stateless
public class LoginControllerService implements ILoginController {

	@PersistenceContext(unitName = "defaultPersistenceUnit")
	private EntityManager entityManager;
	final static Logger logger = Logger.getLogger(productSearchImpl.class
			.getName());

	/**
	 * This Method is responsible for validating the User If valid Login, Return
	 * Success else return Failed
	 */
	public String validateUser(User userObj) {

		User userResult = null;
		String resultString = null;
		try {
			logger.info("Validating the User::::" + userObj.getUser_id());

			userResult = entityManager.find(User.class, userObj.getUser_id());

			if (userResult != null
					&& userObj.getPassword().equals(userResult.getUser_id()))
				resultString = "Success";
			else
				resultString = "Failed";
			logger.info("Result of validating the User :::::" + resultString);
		} catch (Exception exp) {

			logger.error("Error While Validaing the User in Service::::", exp);
			resultString = "Failed";

		}

		return resultString;

	}

	/**
	 * Retrieve the User Object by User ID
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserById(String userId) {

		logger.info("Getting User Object by User ID:::: Starts ");
		User userObj = null;

		try {

			userObj = entityManager.find(User.class, userId);

		} catch (Exception exp) {
			logger.error("Error While getting User Object By User ID::::", exp);
		}
		logger.info("Getting User Object by User ID:::: Ends ");
		return userObj;
	}

	/**
	 * Persisting New User(Saving User Details into Database)
	 * 
	 * @param newUserObj
	 */
	public void saveNewUser(User newUserObj) {

		logger.info("Saving New User::::" + newUserObj.getUser_id());

		try {
			entityManager.persist(newUserObj);
		} catch (Exception exp) {
			logger.error("Error While Saving the new USer", exp);

		}
		logger.info("Successfully Saved the User Details::::"
				+ newUserObj.getUser_id());
	}
}
