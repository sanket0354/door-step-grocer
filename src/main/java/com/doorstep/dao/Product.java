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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(schema = "public", name = "product")
public class Product implements Serializable {

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
	
	@Column(name = "product_name", unique = false, length = 255, nullable = false)
	private String product_name;
	
	@Column(name = "brand_name", unique = false, length = 255, nullable = false)
	private String brand_name;
	
	@Lob
	@Column(name = "description", unique = false, length = 255, nullable = false)
	private String description;
	
	@Column(name = "img_url", unique = false, length = 255, nullable = false)
	private String img_url;
	
	@Column(name = "cost_price", nullable = false)
	private float cost_price;
	
	@Column(name = "selling_price", nullable = false)
	private float selling_price;
	
	@Column(name = "taxable", nullable = false)
	private int taxable;
	
	@Column(name = "is_available", nullable = false)
	private boolean is_available;
	
	@ManyToOne
	@JoinColumn(name ="category_id", insertable = true, updatable = false)
	private Category category_id; //fk_category_id
	
	@ManyToOne
	@JoinColumn(name ="sub_category_id", insertable = true, updatable = false, nullable = true)
	private SubCategory sub_category_id; //fk_sub_category_id
	
	public Product(){
		
	}
	
	public Product(int id,String product_name, String brand_name, String description,String img_url,float cost_price, float selling_price, int taxable, boolean is_available,Category category_id,SubCategory sub_category_id){
		this.id = id;
		this.product_name = product_name;
		this.brand_name = brand_name;
		this.description = description;
		this.img_url = img_url;
		this.cost_price = cost_price;
		this.selling_price = selling_price;
		this.is_available = is_available;
		this.taxable = taxable;
		this.category_id = category_id;
		this.sub_category_id = sub_category_id;
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
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}

	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	/**
	 * @return the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}

	/**
	 * @param brand_name the brand_name to set
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the img_url
	 */
	public String getImg_url() {
		return img_url;
	}

	/**
	 * @param img_url the img_url to set
	 */
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	/**
	 * @return the cost_price
	 */
	public float getCost_price() {
		return cost_price;
	}

	/**
	 * @param cost_price the cost_price to set
	 */
	public void setCost_price(float cost_price) {
		this.cost_price = cost_price;
	}

	/**
	 * @return the selling_price
	 */
	public float getSelling_price() {
		return selling_price;
	}

	/**
	 * @param selling_price the selling_price to set
	 */
	public void setSelling_price(float selling_price) {
		this.selling_price = selling_price;
	}

	/**
	 * @return the taxable
	 */
	public int getTaxable() {
		return taxable;
	}

	/**
	 * @param taxable the taxable to set
	 */
	public void setTaxable(int taxable) {
		this.taxable = taxable;
	}

	/**
	 * @return the is_available
	 */
	public boolean isIs_available() {
		return is_available;
	}

	/**
	 * @param is_available the is_available to set
	 */
	public void setIs_available(boolean is_available) {
		this.is_available = is_available;
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
	 * @return the sub_category_id
	 */
	public SubCategory getSub_category_id() {
		return sub_category_id;
	}

	/**
	 * @param sub_category_id the sub_category_id to set
	 */
	public void setSub_category_id(SubCategory sub_category_id) {
		this.sub_category_id = sub_category_id;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
