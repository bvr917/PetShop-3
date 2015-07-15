/**
 * 
 */
package com.cg.petshop.service;

import javax.ejb.Local;

import com.cg.petshop.User;

/**
 * @author bomvenka
 *
 */
@Local
public interface ILoginController {
	
	public String validateUser(User userObj);
	public User getUserById(String userId);
	public void saveNewUser(User newUserObj);

}
