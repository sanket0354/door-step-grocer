/**
 * 
 */
package com.doorstep.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.RollbackException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.postgresql.util.PSQLException;

import com.doorstep.dao.Person;

/**
 *
 */
public class SignUpDBManager {

	private EntityManagerFactory entityManagerFactory;

	public SignUpDBManager() {
		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = Persistence.createEntityManagerFactory("doorstep-grocer");
	}

	/**
	 * adds new user to database and thus completes the signup process of user
	 * 
	 * @param first_name
	 *            user first name to store in database
	 * @param last_name
	 *            user last name to store in database
	 * @param email
	 *            user email to store in database
	 * @param password
	 *            user password to store in database
	 * @param phone
	 *            user phone to store in database
	 * @return -1 for failure(exception), otherwise return the id of person
	 *         added to database
	 */
	public int addUserInfo(String first_name, String last_name, String email, String password, String phone) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Person person = new Person(first_name, last_name, email, password, phone);
		
		try {
			entityManager.persist(person);

			entityManager.getTransaction().commit();

		} catch (RollbackException ex) {
			/**
			 * catch PSQL exception, that will contain if there is a duplicate
			 * entry of email-address in database, so we can directly return
			 * false here and say Email already exists
			 * 
			 * return -1, for failure
			 */
			Throwable rootcause = ExceptionUtils.getRootCause(ex);
			if (rootcause instanceof PSQLException)
				return -1;
		} finally {


			entityManager.close();
		}

		/*
		 * return the id of the person for anything else
		 */
		return person.getId();

	}

}
