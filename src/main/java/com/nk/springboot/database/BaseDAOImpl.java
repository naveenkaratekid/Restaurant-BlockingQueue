package com.nk.springboot.database;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class BaseDAOImpl {

	Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);
	private static String dr = "org.postgresql.Driver", url = "jdbc:postgresql://localhost:5432/restaurant";
	private static String name = "Naveen", pwd = "";
	private static Connection c1;
	public Connection getConnection()
	{
		try
		{
			Class.forName(dr);
			c1 = DriverManager.getConnection(url, name, pwd);
			return c1;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage(), e);
		}
		return null;
		
	}
	
	
	
	
}

