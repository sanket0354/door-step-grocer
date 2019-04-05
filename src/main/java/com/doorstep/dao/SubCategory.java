/**
 * 
 */
package com.doorstep.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(schema = "public", name = "sub_category")
public class SubCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "sub_category_name", unique = false, length = 255, nullable = false)
	private String sub_category_name;
	
	@Column(name = "is_active", nullable = false)
	private boolean is_active;
	
	@ManyToOne
	@JoinColumn(name ="category_id", insertable = true, updatable = false)
	private Category category_id; //fk_category_id
	
	public SubCategory(){
		
	}
	

	public SubCategory(int id, String sub_category_name,boolean is_active, Category category_id){
		this.id = id;
		this.sub_category_name = sub_category_name;
		this.is_active = is_active;
		this.category_id = category_id;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the sub_category_name
	 */
	public String getSub_category_name() {
		return sub_category_name;
	}


	/**
	 * @param sub_category_name the sub_category_name to set
	 */
	public void setSub_category_name(String sub_category_name) {
		this.sub_category_name = sub_category_name;
	}


	/**
	 * @return the is_active
	 */
	public boolean isIs_active() {
		return is_active;
	}


	/**
	 * @param is_active the is_active to set
	 */
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}


	/**
	 * @return the category_id
	 */
	public Category getCategory_id() {
		return category_id;
	}


	/**
	 * @param category_id the category_id to set
	 */
	public void setCategory_id(Category category_id) {
		this.category_id = category_id;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
