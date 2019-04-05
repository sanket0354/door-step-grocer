package com.doorstep.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
@Table(schema = "public", name = "person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_generator")
	@SequenceGenerator(name="person_id_generator", sequenceName = "person_id", initialValue=1, allocationSize=1)	
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "first_name", length = 255, nullable = false)
	private String first_name; 

	@Column(name = "last_name", length = 255, nullable = false)
	private String last_name; 

	@Column(name = "email", unique = true, length = 255, nullable = false)
	private String email; 

	@Column(name = "password", length = 255, nullable = false)
	private String password; 

	@Column(name = "phone", unique = false, length = 15, nullable = false)
	private String phone; 

	@Column(name = "is_admin", nullable = false)
	private boolean is_admin;

	@Column(name = "is_driver", nullable = false)
	private boolean is_driver;

	@Column(name = "email_subscription", nullable = false)
	private boolean email_subscription;

	@Column(name = "is_first_order", nullable = false)
	private boolean is_first_order;

	@Column(name = "createdDate", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "address", unique = false, length = 255, nullable = false)
	private String address;

	@Column(name = "city", unique = false, length = 255, nullable = false)
	private String city;

	@Column(name = "country", unique = false, length = 150, nullable = false)
	private String country;

	@Column(name = "postalcode", unique = false, length = 55, nullable = false)
	private String postalcode;

	//TODO: One to Many Relationships
	@OneToMany
	private Set <Order> list_of_last_orders; 
	
	
	/*
	 * @Column(name = "last_category_id", length = 25) private int
	 * last_category_id; // = models.CharField(max_length=25, null=True) Just
	 * incase if we want to setup user to login back to his last category in the
	 * store
	 */

	public Person() {

	}

	/**
	 * @param first_name
	 * @param last_name
	 * @param email
	 * @param password
	 * @param phone
	 * @param is_admin
	 * @param is_driver
	 * @param email_subscription
	 * @param is_first_order
	 * @param createdDate
	 * @param address
	 * @param city
	 * @param country
	 * @param postalcode
	 * @param list_of_last_orders
	 */
	public Person(String first_name, String last_name, String email, String password, String phone)//, boolean email_subscription) 
	{
		
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		
		//preset for our purposes
		this.is_admin = false;
		this.is_driver = false;
		this.email_subscription = false;
		this.is_first_order = true;
				
		this.createdDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
		this.address = "--";
		this.city = "--";
		this.country = "--";
		this.postalcode = "--";
		
		this.list_of_last_orders = null;
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
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the is_admin
	 */
	public boolean isIs_admin() {
		return is_admin;
	}

	/**
	 * @param is_admin the is_admin to set
	 */
	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	/**
	 * @return the is_driver
	 */
	public boolean isIs_driver() {
		return is_driver;
	}

	/**
	 * @param is_driver the is_driver to set
	 */
	public void setIs_driver(boolean is_driver) {
		this.is_driver = is_driver;
	}

	/**
	 * @return the email_subscription
	 */
	public boolean isEmail_subscription() {
		return email_subscription;
	}

	/**
	 * @param email_subscription the email_subscription to set
	 */
	public void setEmail_subscription(boolean email_subscription) {
		this.email_subscription = email_subscription;
	}

	/**
	 * @return the is_first_order
	 */
	public boolean isIs_first_order() {
		return is_first_order;
	}

	/**
	 * @param is_first_order the is_first_order to set
	 */
	public void setIs_first_order(boolean is_first_order) {
		this.is_first_order = is_first_order;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	/**
	 * @return the list_of_last_orders
	 */
	public Set<Order> getSet_of_last_orders() {
		return list_of_last_orders;
	}

	/**
	 * @param list_of_last_orders the list_of_last_orders to set
	 */
	public void setSet_of_last_orders(Set<Order> list_of_last_orders) {
		this.list_of_last_orders = list_of_last_orders;
	}
	
	
	
	

}
