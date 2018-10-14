package com.nk.springboot.database;

import java.sql.Connection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.model.Customer;

public interface CustomerDAO 
{
	public Customer insertIntoCustomerTable(Customer c);
	
	public Customer getCustomerDetails(int profileID);
	
	
	public Connection getConnection() throws Exception;
	

}
