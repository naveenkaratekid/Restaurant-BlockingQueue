package com.nk.springboot.queue;

public class OrderQueueFactory {

	static OrderQueue instance;
	public static OrderQueue theInstance() 
	{
		if(instance == null)
		{
			instance = new OrderQueueImpl();
			return instance;
		}
		return instance;
	}
	
}
