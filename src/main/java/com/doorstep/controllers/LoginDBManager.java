/**
 * 
 */
package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.doorstep.dao.Person;

public class LoginDBManager {

	private EntityManagerFactory entityManagerFactory;

	public LoginDBManager() {

		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * This will verify if the user exists in database and if user is there then
	 * validates password and email
	 * 
	 * @param email
	 *            email of user
	 * @param password
	 *            password of user
	 * @return The person if valid, else false
	 */
	public Person verifyUserLogin(String email, String password) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {

			/*
			 * Get all the persons from database
			 */
			List<Person> result = entityManager.createQuery("from Person", Person.class).getResultList();
			for (Person person : result) {
				/*
				 * If email is found return, -compare the password, if they
				 * match return true -compare the password, if they dont match
				 * return false
				 * 
				 * If email is not found, -At the end return false
				 */
				if (person.getEmail().equals(email))
					if (person.getPassword().equals(password))
						return person;
					else
						return null;
			}
		} finally {
			entityManager.close();
		}
		return null;
	}

}
