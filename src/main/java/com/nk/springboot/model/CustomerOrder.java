package com.nk.springboot.model;
//import java.util.*;
import java.sql.Date;

public class CustomerOrder {
	
	
	int profileID, orderID;
	Date dateOrdered;
	double totalAmountForOrder;
	public String status;
	public static enum status {received, inProgress, cancelled, ready, inTransit, delivered, closed}; // in customer order add column called delivery option for choose pickup or delivery

	public int getProfileID() {
		return profileID;
	}

	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public double getTotalAmountForOrder() {
		return totalAmountForOrder;
	}

	public void setTotalAmountForOrder(double totalAmountForOrder) {
		this.totalAmountForOrder = totalAmountForOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerOrder(int profileID, int orderID, Date dateOrdered, double totalAmountForOrder, String status)
	{
		this.profileID = profileID;
		this.orderID = orderID;
		this.dateOrdered = dateOrdered;
		this.totalAmountForOrder = totalAmountForOrder;
		this.status = status;
		
	}
	
	public CustomerOrder()
	{
		
	}
	
	@Override
	public boolean equals(Object co)
	{
		CustomerOrder co1 = (CustomerOrder) co;

		return (orderID == co1.getOrderID()) ? true : false;
	}
	

	
}
