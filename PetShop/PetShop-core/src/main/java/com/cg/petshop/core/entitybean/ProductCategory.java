/**
 * 
 */
package com.cg.petshop.core.entitybean;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author bomvenka
 * This class is Entity Class for Table PSM_PRODUCT_CATEGORY
 *
 */
@Entity
@Table(name = "PSM_PRODUCT_CATEGORY")
@ManagedBean(name="newProductCategory")
public class ProductCategory {
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
	@Id 
	private String category;
	private String name;
	private String description;  
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
