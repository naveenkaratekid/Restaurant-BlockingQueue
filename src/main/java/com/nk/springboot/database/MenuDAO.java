package com.nk.springboot.database;

import java.util.List;

import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;

public interface MenuDAO {

	
	public List<MenuItem> getAllItemsFromMenu();
	
	public void addMenuItemToOrder(CustomerOrderDetail cod);
	
}
