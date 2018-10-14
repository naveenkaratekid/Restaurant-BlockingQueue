package com.nk.springboot.service.task;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nk.springboot.database.CustomerOrderDAO;
import com.nk.springboot.database.CustomerOrderDAOImpl;
import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.OrderConsumerResult;
import com.nk.springboot.queue.OrderQueue;
import com.nk.springboot.queue.OrderQueueFactory;
import com.nk.springboot.model.CustomerOrder.status;

public class OrderConsumerTask implements Callable<OrderConsumerResult> {

	private OrderQueue oq = OrderQueueFactory.theInstance();
	private CustomerOrder co;
	private static Logger logger = LoggerFactory.getLogger(OrderConsumerTask.class);
	
	private static CustomerOrderDAO coDAO = new CustomerOrderDAOImpl();
	@Override
	public OrderConsumerResult call() throws Exception {
		// TODO Auto-generated method stub
		consumeOrder();
		return null;
	}
	
	public OrderConsumerTask(OrderQueue oq, CustomerOrder co)
	{
		this.oq = oq;
		this.co = co;
	}
	
	public OrderConsumerResult consumeOrder()
	{
		logger.info(Thread.currentThread().getName() + " processing order " + co.getOrderID());
		OrderConsumerResult cor = new OrderConsumerResult();
		try
		{
			//while(true)
			//{
				//CustomerOrder co = oq.peek();
				if(co != null)
				{
					logger.info("{}", co.getOrderID());
					logger.info("{}", co.getDateOrdered());
					logger.info("{}", co.getStatus());
					logger.info("{}", co.getProfileID());
					
					//CustomerOrder co1 = oq.take();
					/*if(listOfCancelledOrderIDs.contains(co.getOrderID()))
					{
						if(co.getStatus().equals(status.received.toString()))
						{
							co.setStatus(status.cancelled.toString());
							listOfCancelledOrderIDs.remove(listOfCancelledOrderIDs.indexOf(co.getOrderID()));
						}
					}*/
					logger.info(String.format("Order %d for profile ID %d has been processed", co.getOrderID(), co.getProfileID()));
					logger.info(co.getStatus());
					switch(co.getStatus())
					{
						case "received":
							co.setStatus(status.inProgress.toString());
							oq.addToQueue(co);
							break;
						
						case "inProgress":
							co.setStatus(status.ready.toString());
							oq.addToQueue(co);
							break;
						
						case "ready":
							co.setStatus(status.closed.toString());
							oq.addToQueue(co);
							break;
						
						case "closed":
							//CustomerOrder co2 = oq.take();
							logger.info("Order {} is closed", co.getOrderID());
							break;	
					}
					coDAO.updateStatus(co);
					
					
					}
				/*else
				{
					logger.info("Queue is empty. Waiting for 5 seconds");
				}
				
				try
				{
					Thread.sleep(60000);
				}
				catch(Exception e)
				{
					logger.error(e.getMessage());
				}*/
			//}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return cor;
	}

}
