package com.nk.springboot.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.exception.OrderNotFoundException;
import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;
import com.nk.springboot.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends BaseDAOImpl implements OrderDAO {

	private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
	private static String insertIntoOrderTable = "insert into customerOrder values (?,?,?,?,?)";
	//private static String selectFromOrderTable = "select * from customerOrder where profileID = ?";
	//private static String selectFromCustomerOrder = "select * from customerOrder where profileid = ?";
	private static String selectFromMenu = "select a.quantity, a.itemID, b.itemName, b.unitPrice from customerorderdetails a, menu b where a.itemId = b.itemId and a.orderID = ?;";
	
	private static String selectFromMenuAndCustomerOrderDetails = "select a.itemid,a.quantity,a.quantity * b.unitprice price from customerorderdetails a, menu b where a.itemid=b.itemid and a.orderid= ? and a.itemid= ?;";
	private static String updateCustomerOrderPrice = "update customerorder set totalamountfororder = totalamountfororder + ? where orderID = ?;";
	
	private static String selectQuantityFromDetails = "select * from customerorderdetails where orderID = ? and itemID = ?;";
	private static String updateQuantityinOrderDetails = "update customerorderdetails set quantity = ? where orderID = ? and itemID = ?;";
	
	private static String getStatusFromCustomerOrder = "select status from customerorder where orderID = ?;";
	
	public void insertIntoOrderTable(int profileID, int orderID, String dateOrdered, String totalAmountForOrder,
			String status)
	{
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(insertIntoOrderTable);
			ps.setInt(2, orderID);
			ps.setString(3, dateOrdered);
			ps.setString(4, totalAmountForOrder);
			ps.setString(5, status);
			logger.info(ps.toString());
			System.out.println(ps);
			try
			{
				ps.execute();
			}
			catch(Exception e)
			{
				logger.info(e.getMessage());
				e.printStackTrace();
			}		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}	
	}

	public CustomerOrderDetail getOrderFromCustomerDetails(int orderID, int itemID)
	{
		CustomerOrderDetail cod = null;
		try (Connection c1 = getConnection())
		{
			// first we check if it exists
			PreparedStatement ps = c1.prepareStatement(selectQuantityFromDetails);
			ps.setInt(1, orderID);
			ps.setInt(2, itemID);
			System.out.println(ps);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{				
				int order = rs.getInt(1);
				int item = rs.getInt(2);
				int q = rs.getInt(3);
				
				cod = new CustomerOrderDetail();
				cod.setItemID(item);
				cod.setQuantity(q);
				cod.setOrderID(order);
				return cod;			
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return cod;
	}
	
	public void updateQuantityInOrderDetails(int quantity, int orderID, int itemID)
	{
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(updateQuantityinOrderDetails);
			ps.setInt(1, quantity);
			ps.setInt(2, orderID);
			ps.setInt(3, itemID);
			try
			{
				ps.executeUpdate();
			}
			catch(Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	public double getItemsAndUnitPriceFromMenu(int orderID, int itemID)
	{
		try(Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(selectFromMenuAndCustomerOrderDetails);
			ps.setInt(1, orderID);
			ps.setInt(2, itemID);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				double price = rs.getDouble(3);
				System.out.println("Total price: $"+ price);
				return price;
			}
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return 0;
	}
	
	public void updateCustomerOrderPrice(double price, int orderID)
	{
		try(Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(updateCustomerOrderPrice);
			ps.setDouble(1, price);
			ps.setInt(2, orderID);
			System.out.println(ps);
			try
			{
				ps.executeUpdate();
			}
			catch(Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
	
	public boolean isOrderClosed(int orderID) throws Exception
	{
		try(Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(getStatusFromCustomerOrder);
			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				String status = rs.getString(1);
				if(status.equals("closed") || status.equals("ready") || status.equals("cancelled"))
				{
					return true;
				}
				return false;
			}
			else
			{
				throw new OrderNotFoundException("Customer order not found");
			}
		}
	}

	public List<OrderItem> getFromMenu(int orderID)
	{
		try (Connection c1 = getConnection())
		{	
			List<OrderItem> list = new ArrayList<OrderItem>();
			PreparedStatement ps = c1.prepareStatement(selectFromMenu);
			ps.setInt(1, orderID);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				OrderItem oi = new OrderItem();
				int quantity = rs.getInt(1);
				int itemID = rs.getInt(2);
				String itemName = rs.getString(3);
				double price = rs.getDouble(4);
				oi.setItemName(itemName);
				oi.setPrice(price);
				oi.setQuantity(quantity);
				oi.setItemID(itemID);
				list.add(oi);				
			}
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		finally
		{
			//c1.close();
		}
		return null;

	}

}
