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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(schema = "public", name = "cart")
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_generator")
	@SequenceGenerator(name="cart_id_generator", sequenceName = "cart_id", initialValue=1, allocationSize=1)	
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "person_id", insertable = true, updatable = false)
	private Person person_id; // fk_person_id

	@Column(name = "is_checked_out", nullable = false)
	private boolean is_checked_out;

	public Cart(){
		
	}
	
	public Cart(Person person_id,boolean is_checked_out){
		this.person_id = person_id;
		this.is_checked_out = is_checked_out;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Person person_id) {
		this.person_id = person_id;
	}

	public boolean isIs_checked_out() {
		return is_checked_out;
	}

	public void setIs_checked_out(boolean is_checked_out) {
		this.is_checked_out = is_checked_out;
	}

	
	
}
