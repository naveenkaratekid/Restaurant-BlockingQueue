package com.nk.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.nk.springboot.database.CustomerDAO;
import com.nk.springboot.database.CustomerDAOImpl;
import com.nk.springboot.database.CustomerOrderDAO;
import com.nk.springboot.database.CustomerOrderDAOImpl;
import com.nk.springboot.database.MenuDAO;
import com.nk.springboot.database.MenuDAOImpl;
import com.nk.springboot.database.OrderDAO;
import com.nk.springboot.database.OrderDAOImpl;
import com.nk.springboot.exception.OrderNotFoundException;
import com.nk.springboot.model.Customer;
import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrder.status;
import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;
import com.nk.springboot.model.OrderItem;
import com.nk.springboot.queue.OrderQueue;
import com.nk.springboot.queue.OrderQueueFactory;
import com.nk.springboot.queue.OrderQueueImpl;


@Component
public class OrderService {

	private CustomerDAO dao = new CustomerDAOImpl();
	private CustomerOrderDAO codao = new CustomerOrderDAOImpl();
	private MenuDAO mDAO = new MenuDAOImpl();
	OrderQueue queue = OrderQueueFactory.theInstance();
	Logger logger = LoggerFactory.getLogger(OrderService.class);
	private OrderDAO oDAO = new OrderDAOImpl();
	private List<Integer> listOfcancelledOrderIDs = new ArrayList<Integer>();
	
	public String welcomeMessage(String name)
	{
		return String.format("Welcome %s", name);
	}
	
	public OrderService()
	{
		logger.info("Starting order service");
		init(queue, listOfcancelledOrderIDs);
	}
	
	public void init(OrderQueue queue, List<Integer> listOfCancelledOrderIDs)
	{
		OrderConsumer oc = new OrderConsumer(queue, listOfCancelledOrderIDs);
		new Thread(oc).start();
	}
	
	@PreDestroy
	public void shutdown()
	{
		logger.info("Shutting down order service");
		
	}
	
	public Customer getCustomer(int profileID)
	{
		// call customer dao impl
		Customer customer = dao.getCustomerDetails(profileID);
		return customer;
	}
	
	public Customer createCustomer(Customer c)
	{
		Customer c1 = dao.insertIntoCustomerTable(c);
		return c1;
	}
	
	public CustomerOrder createCustomerOrder(CustomerOrder co) throws Exception
	{		
		try
		{	
			co.setStatus(status.received.toString());

			CustomerOrder co1 = codao.createCustomerOrder(co.getDateOrdered(), co.getProfileID());
			queue.addToQueue(co1);
			return co1;
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(),e);
			throw new Exception("Error creating order for customer" + co.getProfileID());
		}
		
		
	}
	
	public CustomerOrder getCustomerOrder(int orderID)
	{
		CustomerOrder co = codao.getCustomerOrder(orderID);
		return co;
	}
	
	// Here we need to get a customerOrder object containing the customers order. Take in a customer object and gets its details, and set its values to the customer order object's member variables
	// because we are trying to get the customer order based on the customer's orderID
	public List<CustomerOrder> getCustomerOrderInfo(int profileID)
	{
		List<CustomerOrder> coList1 = new ArrayList<CustomerOrder>();
		CustomerOrder co = new CustomerOrder();
		List<CustomerOrder> coList = codao.getListOfCustomerOrders(profileID);
		for(CustomerOrder co1: coList) 
		{
			co.setOrderID(co1.getOrderID());
			co.setDateOrdered(co1.getDateOrdered());
			co.setTotalAmountForOrder(co1.getTotalAmountForOrder());
			coList1.add(co);
		}
		return coList1;
		
	}
	
	// Here we need to return the customerorder detail object based on the customer order object we take in
	// because we are trying to get the customer order detail based on the customer order id
	public CustomerOrderDetail getCustomerOrderDetails(int orderID)
	{
		CustomerOrderDetail cod = new CustomerOrderDetail();
		List<CustomerOrderDetail> orderDetailList = codao.getListOfCustomerOrderDetails(orderID);
		for(CustomerOrderDetail cod1: orderDetailList)
		{
			cod.setItemID(cod1.getItemID());
			cod.setOrderID(cod1.getOrderID());
			cod.setQuantity(cod1.getQuantity());
		}
		return cod;
	}
	
	public List<MenuItem> getMenu()
	{
		List<MenuItem> allItemsFromMenu = mDAO.getAllItemsFromMenu();
		return allItemsFromMenu;
	}
	
	public void addItemToOrder(CustomerOrderDetail cod) throws Exception
	{
		
		boolean isStatusCancelled = oDAO.isOrderClosed(cod.getOrderID());
		if(!isStatusCancelled)
		{
			CustomerOrderDetail cod1 = getOrderFromCustomerDetail(cod.getOrderID(), cod.getItemID());
			if(cod1 == null)
			{
				mDAO.addMenuItemToOrder(cod);
				double price = oDAO.getItemsAndUnitPriceFromMenu(cod.getOrderID(), cod.getItemID());
				oDAO.updateCustomerOrderPrice(price, cod.getOrderID());
			}
			
			else
			{
				updateQuantity(cod1);			
			}	
		}
		else
		{
			throw new IllegalStateException("Order is either closed or cancelled");
		}

	}
	
	public List<OrderItem> getOrderDetails(CustomerOrderDetail cod)
	{
		List<OrderItem> list = oDAO.getFromMenu(cod.getOrderID());
		return list;
	}
	
	public void cancelOrder(int orderID) throws OrderNotFoundException
	{
		CustomerOrder co1 = new CustomerOrder();
		co1.setOrderID(orderID);
		if(queue.contains(co1))
		{
			listOfcancelledOrderIDs.add(orderID);
			logger.info("Your order is being cancelled. Check order status to make sure it is cancelled");		
		} 
		else
		{
			throw new OrderNotFoundException("We could not find your order");
		}	
	}
	
	public CustomerOrderDetail getOrderFromCustomerDetail(int orderID, int itemID)
	{
		CustomerOrderDetail cod = oDAO.getOrderFromCustomerDetails(orderID, itemID);
		return cod;
	}
	
	public void updateQuantity(CustomerOrderDetail cod)
	{
		oDAO.updateQuantityInOrderDetails(cod.getQuantity(), cod.getOrderID(), cod.getItemID());
	}
}
