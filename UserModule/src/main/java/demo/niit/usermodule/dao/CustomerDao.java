package demo.niit.usermodule.dao;

import demo.niit.usermodule.model.Customer;

public interface CustomerDao {

	boolean addCustomer(Customer customer);
	Customer isValidCustomer(String customerId, String password);
}
