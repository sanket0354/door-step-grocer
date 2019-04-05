/**
 * 
 */
package com.doorstep.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "cart_item")
public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartItem_id_generator")
	@SequenceGenerator(name="cartItem_id_generator", sequenceName = "cartItem_id", initialValue=1, allocationSize=1)	
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "price", nullable = false)
	private float price;
	
	@Column(name = "tax", nullable = false)
	private float tax;
	
	@Lob
	@Column(name = "note", unique = false, length = 255, nullable = false)
	private String note;
	
	@ManyToOne
	@JoinColumn(name ="cart_id", insertable = true, updatable = true)
	private Cart cart_id; //fk_cart_id
	
	@ManyToOne
	@JoinColumn(name ="product_id", insertable = true, updatable = true)
	private Product product_id; //fk_product_id
	
	public CartItem(){
		
	}
	
	
	public CartItem(int quantity, float price, float tax, String note, Cart cart_id, Product product_id){
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.note = note;
		this.cart_id = cart_id;
		this.product_id = product_id;
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}


	/**
	 * @return the tax
	 */
	public float getTax() {
		return tax;
	}


	/**
	 * @param tax the tax to set
	 */
	public void setTax(float tax) {
		this.tax = tax;
	}


	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}


	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}


	/**
	 * @return the cart_id
	 */
	public Cart getCart_id() {
		return cart_id;
	}


	/**
	 * @param cart_id the cart_id to set
	 */
	public void setCart_id(Cart cart_id) {
		this.cart_id = cart_id;
	}


	/**
	 * @return the product_id
	 */
	public Product getProduct_id() {
		return product_id;
	}


	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(Product product_id) {
		this.product_id = product_id;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
}
