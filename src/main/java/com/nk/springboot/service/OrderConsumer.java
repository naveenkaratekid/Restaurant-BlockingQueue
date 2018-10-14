package com.nk.springboot.service;

import com.nk.springboot.queue.OrderQueue;
import com.nk.springboot.queue.OrderQueueFactory;
import com.nk.springboot.queue.OrderQueueImpl;
import com.nk.springboot.service.task.OrderConsumerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.database.CustomerOrderDAO;
import com.nk.springboot.database.CustomerOrderDAOImpl;
import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrder.status;

public class OrderConsumer implements Runnable
{
	private OrderQueue queue = OrderQueueFactory.theInstance();
	private Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
	//private CustomerOrderDAO coDAO = new CustomerOrderDAOImpl();
	private List<Integer> listOfCancelledOrderIDs = new ArrayList<Integer>();
	private ExecutorService threadPool;
	
	
	public OrderConsumer(OrderQueue queue, List<Integer> listOfCancelledOrderIDs)
	{
		this.listOfCancelledOrderIDs = listOfCancelledOrderIDs;
		this.queue = queue;
		threadPool = Executors.newFixedThreadPool(5);
	}
	
	public void consumeQueue()
	{
		try
		{
			while(true)
			{
				CustomerOrder co1 = queue.peek();
				if(co1 != null)
				{
					co1 = queue.take();
					OrderConsumerTask oct = new OrderConsumerTask(queue, co1);
					threadPool.submit(oct);
				}
				else
				{
					logger.info("Queue is empty. Waiting for 5 seconds");
					Thread.sleep(60000); // convert minute to ms: minute * 60 * 1000
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		consumeQueue();
		
	}

}
