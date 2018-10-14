package com.nk.springboot.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;
import com.nk.springboot.model.OrderItem;

public interface OrderDAO {
	
	public void insertIntoOrderTable(int profileID /* foreign key to customer table profile ID*/, int orderID, String dateOrdered, String totalAmountForOrder, String status);

	//public String getOrderInformation();
	public  List<OrderItem> getFromMenu(int orderID);
	public double getItemsAndUnitPriceFromMenu(int orderID, int itemID);
	public void updateCustomerOrderPrice(double price, int orderID);
	
	public CustomerOrderDetail getOrderFromCustomerDetails(int orderID, int itemID);
	public boolean isOrderClosed(int orderID) throws Exception;
	public void updateQuantityInOrderDetails(int quantity, int orderID, int itemID);
	
}
