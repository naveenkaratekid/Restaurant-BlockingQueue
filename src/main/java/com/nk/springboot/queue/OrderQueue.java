package com.nk.springboot.queue;

import java.util.Queue;
import com.nk.springboot.model.CustomerOrder;


public interface OrderQueue 
{
	public boolean addToQueue(CustomerOrder co) throws Exception;
	
	public CustomerOrder remove() throws Exception;
	public CustomerOrder peek() throws Exception;
	public CustomerOrder take() throws Exception;
	public boolean contains(CustomerOrder co);
	 //create isThereInstance(). singelton pattern

	
	// refer to javafx cardashboard singleton code
	
	//static OrderQueueImpl theInstance();
	//static OrderQueue theInstance();
}
