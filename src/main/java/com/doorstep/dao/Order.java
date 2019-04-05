/**
 * 
 */
package com.doorstep.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 */
@Entity
@Table(schema = "public", name = "order")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Order_id_generator")
	@SequenceGenerator(name = "Order_id_generator", sequenceName = "Order_id", initialValue = 1, allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "order_number", unique = false, nullable = true)
	private int order_number;

	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date created_date;

	@Column(name = "delivery_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date delivery_date;

	@Column(name = "delivery_time", nullable = true)
	@Temporal(TemporalType.TIME)
	private Date delivery_time;

	@Column(name = "complete_address", unique = false, length = 255, nullable = false)
	private String complete_address;

	@Column(name = "total_price", nullable = false)
	private float total_price;

	@Column(name = "tax", nullable = false)
	private float tax;

	@Column(name = "delivery_charge", nullable = false)
	private float delivery_charge;

	@Column(name = "order_confirmation_code", unique = true, length = 255, nullable = true)
	private String order_confirmation_code;

	@Column(name = "order_status", unique = false, length = 255, nullable = true)
	private String order_status; // pending | delivered | on delivery |etc

	@ManyToOne
	@JoinColumn(name = "person_id", insertable = true, updatable = true)
	private Person person_id; // fk_person_id

	@ManyToOne
	@JoinColumn(name = "cart_id", insertable = true, updatable = true)
	private Cart cart_id; // fk_cart_id

	@ManyToOne
	@JoinColumn(name = "assigned_driver", insertable = true, updatable = true)
	private Person assigned_driver; // fk_person_id

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the order_number
	 */
	public int getOrder_number() {
		return order_number;
	}

	/**
	 * @param order_number
	 *            the order_number to set
	 */
	public void setOrder_number(int order_number) {
		this.order_number = order_number;
	}

	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}

	/**
	 * @param created_date
	 *            the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return the delivery_date
	 */
	public Date getDelivery_date() {
		return delivery_date;
	}

	/**
	 * @param delivery_date
	 *            the delivery_date to set
	 */
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	/**
	 * @return the delivery_time
	 */
	public Date getDelivery_time() {
		return delivery_time;
	}

	/**
	 * @param delivery_time
	 *            the delivery_time to set
	 */
	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}

	/**
	 * @return the total_price
	 */
	public float getTotal_price() {
		return total_price;
	}

	/**
	 * @param total_price
	 *            the total_price to set
	 */
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	/**
	 * @return the tax
	 */
	public float getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(float tax) {
		this.tax = tax;
	}

	/**
	 * @return the delivery_charge
	 */
	public float getDelivery_charge() {
		return delivery_charge;
	}

	/**
	 * @param delivery_charge
	 *            the delivery_charge to set
	 */
	public void setDelivery_charge(float delivery_charge) {
		this.delivery_charge = delivery_charge;
	}

	/**
	 * @return the order_confirmation_code
	 */
	public String getOrder_confirmation_code() {
		return order_confirmation_code;
	}

	/**
	 * @param order_confirmation_code
	 *            the order_confirmation_code to set
	 */
	public void setOrder_confirmation_code(String order_confirmation_code) {
		this.order_confirmation_code = order_confirmation_code;
	}

	/**
	 * @return the order_status
	 */
	public String getOrder_status() {
		return order_status;
	}

	/**
	 * @param order_status
	 *            the order_status to set
	 */
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	/**
	 * @return the person_id
	 */
	public Person getPerson_id() {
		return person_id;
	}

	/**
	 * @param person_id
	 *            the person_id to set
	 */
	public void setPerson_id(Person person_id) {
		this.person_id = person_id;
	}

	/**
	 * @return the cart_id
	 */
	public Cart getCart_id() {
		return cart_id;
	}

	/**
	 * @param cart_id
	 *            the cart_id to set
	 */
	public void setCart_id(Cart cart_id) {
		this.cart_id = cart_id;
	}

	/**
	 * @return the assigned_driver
	 */
	public Person getAssigned_driver() {
		return assigned_driver;
	}

	/**
	 * @param assigned_driver
	 *            the assigned_driver to set
	 */
	public void setAssigned_driver(Person assigned_driver) {
		this.assigned_driver = assigned_driver;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the complete_address
	 */
	public String getComplete_address() {
		return complete_address;
	}

	/**
	 * @param complete_address the complete_address to set
	 */
	public void setComplete_address(String complete_address) {
		this.complete_address = complete_address;
	}
	

}
