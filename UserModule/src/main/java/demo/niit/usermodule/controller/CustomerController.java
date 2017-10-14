package demo.niit.usermodule.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.niit.usermodule.dao.CustomerDao;
import demo.niit.usermodule.dao.CustomerDaoImpl;
import demo.niit.usermodule.model.Customer;

@RestController
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerDao customerDao;
	
	@RequestMapping(value = "/CreateCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		log.debug("**********Starting of Method createCustomer**********");
		if(customerDao.addCustomer(customer)){
			log.debug("**********New Customer Created Successfully**********");
			return new ResponseEntity<Customer>(customer , HttpStatus.OK);
			}
		else
		{	customer.setErrorMessage("customer not created");
			customer.setErrorCode("500");
			return new ResponseEntity<Customer>(customer , HttpStatus.CONFLICT);
		}
	}	
	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<Customer> authentication(@RequestBody Customer customer){
		log.debug("**********Starting of Method authenticate**********");
		Customer user=customerDao.isValidCustomer(customer.getCustomerId(),customer.getPassword());
			if(user != null){
				log.debug("**********User Exist With Given Credentials.**********");
			return new ResponseEntity<Customer>(customer , HttpStatus.OK);
			}
			else
			{	customer.setErrorMessage("customer not exits");
				customer.setErrorCode("404");
				return new ResponseEntity<Customer>(customer , HttpStatus.UNAUTHORIZED);
			}
	}		
}
