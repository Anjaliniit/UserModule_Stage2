package demo.niit.usermodule.dao;

import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.niit.usermodule.config.AppInitializer;
import demo.niit.usermodule.model.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	private static final Logger log = LoggerFactory.getLogger(CustomerDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean addCustomer(Customer customer) {
		log.debug("customer data is going to end");
		try{
		customer.setCustomerId("CUS"+UUID.randomUUID().toString().substring(0,10).toUpperCase());
		sessionFactory.getCurrentSession().persist(customer);
		log.debug("customer data added successfully");
		return true;
		}
		catch(HibernateException exception)
		{
		log.error(exception.getMessage());
		return false;
		}
	}
	@Transactional
	public Customer isValidCustomer(String customerId, String password) {
		try{
			Query query=sessionFactory.getCurrentSession().createQuery("from Customer where customerId=? and password=?");
			query.setParameter(0,customerId);
			query.setParameter(1,password);
			Customer customer=(Customer)query.uniqueResult();
			return customer;
			}
			catch(HibernateException exception)
			{
				log.error(exception.getMessage());
				return null;
			}

	}

}
