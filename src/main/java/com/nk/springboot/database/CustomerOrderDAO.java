package com.nk.springboot.database;

import java.sql.Date;
import java.util.List;

import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrderDetail;

public interface CustomerOrderDAO {

	public List<CustomerOrder> getListOfCustomerOrders(int profileID);
	public List<CustomerOrderDetail> getListOfCustomerOrderDetails(int orderID);
	//public CustomerOrder getCustomerOrder(int orderID);
	public void updateStatus(CustomerOrder co);
	public CustomerOrder createCustomerOrder(Date date, int profileID);
	public CustomerOrder getCustomerOrder(int orderID);
}
