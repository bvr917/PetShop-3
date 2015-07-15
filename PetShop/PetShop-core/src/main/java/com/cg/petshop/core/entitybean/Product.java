
package com.cg.petshop.core.entitybean;
import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PSM_PRODUCT")
@ManagedBean(name="newProduct")
public class Product {
	

	@Override
	public int hashCode() {
		
		if(this.productId!=null)
		return this.productId.hashCode();
		else
			return super.hashCode();
				
	}

	@Override
	public boolean equals(Object obj) {
		
		Product pr = (Product)obj;
		
		return this.productId.equals(pr.productId);
	}

	@Id
	private String productId;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private BigDecimal price;
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	private String name;
	
	private String category;
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private String description;
	private int quantity;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Lob
	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	
}
