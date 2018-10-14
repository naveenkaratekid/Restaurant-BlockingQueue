package com.nk.springboot.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

import com.nk.springboot.database.CustomerOrderDAO;
import com.nk.springboot.database.CustomerOrderDAOImpl;
import com.nk.springboot.model.CustomerOrder;



public class OrderQueueImpl implements OrderQueue {

	public Logger logger = LoggerFactory.getLogger(OrderQueueImpl.class);
	private static final long TIMEOUT = 8000;
	public BlockingQueue<CustomerOrder> coQueue = new ArrayBlockingQueue<>(10);
	private static OrderQueue oq;
	
	/*
	 * addToQueue() will try to insert a customer order into the queue. If it is unable to, it will throw an exception
	 */
	
	@Override
	public boolean addToQueue(CustomerOrder co) throws Exception {
		try
		{
			boolean isInsertedIntoQueue = coQueue.offer(co, TIMEOUT, TimeUnit.MILLISECONDS);
			logger.info("Added customerOrder to queue " + co.getOrderID() + " " + co.getStatus());
			return isInsertedIntoQueue;
		
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			throw new Exception ("Unable to add to queue", e);	
		}
		
	}

	@Override
	public boolean contains(CustomerOrder co) {
		
		return (coQueue.contains(co));
		
	}
	
	/* 
	 * theInstance() will check if there is an instance of the class, if not, create it, and return it
	 * 
	 */
	
	public static OrderQueueImpl theInstance()
	{
		if(oq == null)
		{
			oq = new OrderQueueImpl();
			return (OrderQueueImpl) oq;
		}
		return (OrderQueueImpl) oq;
	}
	
	
	@Override
	public CustomerOrder remove() throws Exception {
		try
		{
			CustomerOrder co = coQueue.remove();
			return co;
		}
		
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new Exception ("Unable to delete from queue", e);
		}
		/*if(!coQueue.isEmpty())
		{
			return coQueue.remove();
		}
		// TODO Auto-generated method stub
		else
		{
			throw new Exception("Queue is empty. Cannot remove anything");
		}*/
	}
	
	public CustomerOrder peek() throws Exception
	{
		try
		{
			CustomerOrder co = coQueue.peek();
			return co;
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new Exception("Unable to peek at queue");
		}
		
	}
	
	public CustomerOrder take() throws Exception
	{
		try
		{
			CustomerOrder co = coQueue.take();
			return co;
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			throw new Exception("Unable to take first entry from queue");
		}
	}

}
