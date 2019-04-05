/**
 * 
 */
package com.doorstep.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(schema = "public", name = "category")
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * We will not generate the id of this as we can put our
	 * own ID for category, although it will still stay unique
	 */
	@Id	
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "category_name", unique = true, length = 255, nullable = false)
	private String category_name;

	@Column(name = "is_active", nullable = false)
	private boolean is_active;

	public Category(){
		
	}
	
	public Category(int id, String categoryName, boolean is_active){
		this.id = id;
		this.category_name = categoryName;
		this.is_active = is_active;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

}
