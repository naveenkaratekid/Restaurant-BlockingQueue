package com.nk.springboot.model;

public class MenuItem {

	public String item;
	public int itemID;
	public double price;
	
	public MenuItem(String item, int itemID, double price)
	{
		this.item = item;
		this.itemID = itemID;
		this.price = price;
	}
	
	public MenuItem()
	{
		
	}
	
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	
	
}
