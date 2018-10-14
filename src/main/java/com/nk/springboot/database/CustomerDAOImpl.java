package com.nk.springboot.database;

import java.sql.*;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Date;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.nk.springboot.model.Customer;

public class CustomerDAOImpl extends BaseDAOImpl implements CustomerDAO
{

	//private static Customer customer;
	private static Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);
	private static String insertIntoCustomerTable = "insert into customers (customername, email, address, phonenumber) values (?,?,?,?)";
	private static String selectCustomerOrderNameFromCustomerOrderTable = "select * from customers where id = ?";
	
	public Customer insertIntoCustomerTable(Customer c)
	{
		try (Connection c1 = getConnection())
		{
			PreparedStatement ps = c1.prepareStatement(insertIntoCustomerTable, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getCustomerName());
			ps.setString(2, c.getEmail());
			ps.setString(3, c.getAddress());
			ps.setString(4, c.getPhoneNumber());
			
			System.out.println(ps);
			logger.info(ps.toString());
			
			try
			{
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					int ID = rs.getInt(1);
					System.out.println(ID);
					logger.info(Integer.toString(ID));
					c.setProfileID(ID);
				}
				else
				{
					logger.info("No generated keys");
				}
				return c;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Customer getCustomerDetails(int profileID) {
		// TODO Auto-generated method stub
		try (Connection c1 = getConnection())
		{
			Customer customer = new Customer();
			PreparedStatement ps = c1.prepareStatement(selectCustomerOrderNameFromCustomerOrderTable);
			ps.setInt(1, profileID);
			logger.info(ps.toString());
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int ID = rs.getInt(1);
				customer.setProfileID(ID);
				String customerName = rs.getString(2);
				String email = rs.getString(3);
				String address = rs.getString(4);
				String phoneNumber = rs.getString(5);
				customer.setCustomerName(customerName);
				customer.setEmail(email);
				customer.setAddress(address);
				customer.setPhoneNumber(phoneNumber);
				
				return customer;
			}
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/*public Connection getConnection() throws Exception
	{
		try
		{
			Class.forName(dr);
			c1 = DriverManager.getConnection(url, name, pwd);
			
			return c1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return null;
	}*/
	
	public static void main(String[] args) throws Exception
	{
		
		CustomerDAO cod = new CustomerDAOImpl();
		cod.getConnection();
		//cod.insertIntoCustomerDetails(1, "Krishna", "krishna@smccd.edu", "3843 Burton Common Fremont, CA 94536", "19197920254");
		//String info  = cod.getCustomerDetails(1);
		//System.out.println(info);
	}

	

}
