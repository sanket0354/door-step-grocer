package com.doorstep.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.doorstep.dao.Order;
import com.doorstep.dao.Person;

public class PersonDBManager {
	private EntityManagerFactory entityManagerFactory;

	public PersonDBManager() {
		/**
		 * get the instance already created in EntityManagerFactoryManager Class
		 */
		entityManagerFactory = EntityManagerFactoryManager.getEntityManagerFactory();

	}

	/**
	 * to get the person from database
	 * 
	 * @param personId
	 *            the id of the person to be fetched
	 * @return the person fetched from database
	 */
	public Person getPerson(int personId) {
		Person person;
		EntityManager entityManger = entityManagerFactory.createEntityManager();
		try {
			// entityManger.getTransaction().begin();
			/*
			 * find the person using the primary key id
			 */
			person = entityManger.find(Person.class, personId);
		} finally {
			entityManger.close();
		}
		return person;
	}

	public List<Person> getAllUsers() {
		List<Person> users = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			users = entityManager.createQuery("SELECT p FROM Person p").getResultList();
		} finally {
			entityManager.close();
		}

		return users;
	}

	public void updateAdminState(String email) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Person> person;
		try {
			entityManager.getTransaction().begin();

			Query query = entityManager.createQuery("SELECT p FROM Person p WHERE email='" + email + "'");
			person = (List<Person>) query.getResultList();

			person.get(0).setIs_admin(!person.get(0).isIs_admin());
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}

	public void updateDriverState(String email) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<Person> person;
		try {
			entityManager.getTransaction().begin();

			Query query = entityManager.createQuery("SELECT p FROM Person p WHERE email='" + email + "'");
			person = (List<Person>) query.getResultList();

			person.get(0).setIs_driver(!person.get(0).isIs_driver());
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}
}
