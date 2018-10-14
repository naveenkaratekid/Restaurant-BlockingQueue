package com.nk.springboot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrder.status;
import com.nk.springboot.model.CustomerOrderDetail;

public class CustomerOrderDAOImpl extends BaseDAOImpl implements CustomerOrderDAO {

	private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
	private static String selectFromCustomerOrder = "select * from customerOrder where profileid = ?;";
	private static String selectFromCustomerOrderDetails = "select * from customerOrderDetails where orderID = ?;";
	private static String insertIntoCustomerOrder = "insert into customerOrder (profileID, dateOrdered, totalamountfororder, status) values (?,?,?,?);";
	private static String getCustomerOrder = "select * from customerOrder where orderID = ?;";
	private static String updateStatusinCustomerOrder = "update customerOrder set status = ? where orderID = ?;";	
	
	public CustomerOrder createCustomerOrder(Date date, int profileID)
	{
		Date d1 = new Date(date.getTime());
		System.out.println(d1);
		CustomerOrder co1 = new CustomerOrder();
		
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(insertIntoCustomerOrder, Statement.RETURN_GENERATED_KEYS);			
			System.out.println(ps);
			ps.setInt(1, profileID);
			ps.setDate(2, (java.sql.Date) d1);		
			ps.setDouble(3, 0.0);
			ps.setString(4, status.received.toString());
			co1.setDateOrdered(d1);
			co1.setProfileID(profileID);
			co1.setStatus(status.received.toString());
			co1.setTotalAmountForOrder(0.0);
			
			
			try
			{
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next())
				{
					int orderID = rs.getInt(2);
					logger.info("{}", orderID);
					System.out.println(orderID);
					co1.setOrderID(orderID);
				}
			}
			catch(Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			return co1;			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public void updateStatus(CustomerOrder co)
	{
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(updateStatusinCustomerOrder);
			ps.setString(1, co.getStatus());
			ps.setInt(2, co.getOrderID());
			System.out.println(ps);
			try
			{
				ps.executeUpdate();
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				e.printStackTrace();
			}	
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public CustomerOrder getCustomerOrder(int orderID)
	{
		CustomerOrder co = new CustomerOrder();
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(getCustomerOrder);
			ps.setInt(1,orderID);
			logger.info(ps.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int profileID = rs.getInt(1);
				int oID = rs.getInt(2);
				Date date = rs.getDate(3);
				double orderTotal = rs.getDouble(4);
				String status = rs.getString(5);
				
				co.setProfileID(profileID);
				co.setOrderID(oID);
				co.setDateOrdered(date);
				co.setTotalAmountForOrder(orderTotal);
				co.setStatus(status);
				return co;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return co;
	}
	
	public List<CustomerOrder> getListOfCustomerOrders(int profileID)
	{
		logger.info("Getting customer order");
		List<CustomerOrder> list = new ArrayList<CustomerOrder>();
		
		try (Connection c1 = getConnection())
		{
			CustomerOrder co = new CustomerOrder();
			
			PreparedStatement ps = c1.prepareStatement(selectFromCustomerOrder);
			ps.setInt(1, profileID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int orderID = rs.getInt(2);
				Date date = rs.getDate(3);
				double total = rs.getDouble(4);
				co.setDateOrdered(date);
				co.setOrderID(orderID);
				co.setProfileID(profileID);
				co.setTotalAmountForOrder(total);
				list.add(co);
				return list;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		return list;
	}
	
	public List<CustomerOrderDetail> getListOfCustomerOrderDetails(int orderID) {
		List<CustomerOrderDetail> list = new ArrayList<CustomerOrderDetail>();
		
		try (Connection c1 = getConnection())
		{
			CustomerOrderDetail cod = new CustomerOrderDetail();
			PreparedStatement ps = c1.prepareStatement(selectFromCustomerOrderDetails);
			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				int itemID = rs.getInt(2);
				int quantity = rs.getInt(3);
				cod.setItemID(itemID);
				cod.setQuantity(quantity);
				cod.setOrderID(orderID);
				list.add(cod);
				return list;
			}
		}
		catch(Exception e) 
		{
			logger.error(e.getMessage());
			e.printStackTrace();	
		} 
		return list;
	}

}
