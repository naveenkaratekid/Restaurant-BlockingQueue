package com.nk.springboot.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MenuDAOImpl extends BaseDAOImpl implements MenuDAO 
{
	private static Logger logger = LoggerFactory.getLogger(MenuDAOImpl.class);
	private static String selectAllFromMenu = "select * from menu;";
	private static String insertIntoCustomerOrderDetails = "insert into customerOrderDetails values (?,?,?);";
	
	
	public List<MenuItem> getAllItemsFromMenu()
	{
		try (Connection c1 = getConnection())
		{
			
			List<MenuItem> list = new ArrayList<MenuItem>();
			PreparedStatement ps = c1.prepareStatement(selectAllFromMenu);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				MenuItem mi = new MenuItem();
				int itemID = rs.getInt(1);
				String itemName = rs.getString(2);
				double unitPrice = rs.getDouble(3);
				System.out.println(itemID);
				System.out.println(itemName);
				System.out.println(unitPrice);
				
				mi.setItem(itemName);
				mi.setItemID(itemID);
				mi.setPrice(unitPrice);
				list.add(mi);	
			}
			return list;		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public void addMenuItemToOrder(CustomerOrderDetail cod)
	{
		// customer order details
		try (Connection c1 = getConnection())
		{			
			
			PreparedStatement ps = c1.prepareStatement(insertIntoCustomerOrderDetails);
			ps.setInt(1, cod.getOrderID());		
			ps.setInt(2, cod.getItemID());
			ps.setDouble(3, cod.getQuantity());
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
}
