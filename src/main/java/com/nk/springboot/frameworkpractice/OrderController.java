package com.nk.springboot.frameworkpractice;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nk.springboot.database.CustomerDAO;
import com.nk.springboot.database.CustomerDAOImpl;
import com.nk.springboot.database.MenuDAO;
import com.nk.springboot.database.MenuDAOImpl;
import com.nk.springboot.exception.OrderNotFoundException;
import com.nk.springboot.model.Customer;

import com.nk.springboot.model.CustomerOrder;
import com.nk.springboot.model.CustomerOrderDetail;
import com.nk.springboot.model.MenuItem;
import com.nk.springboot.model.OrderItem;
import com.nk.springboot.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	CustomerDAO dao = new CustomerDAOImpl();
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	MenuDAO mDAO = new MenuDAOImpl();
	
	@Autowired
	private OrderService orderService;
	
	/*@GetMapping("/welcome")
	public String welcome(@RequestParam String name) // getting a request parameter
	{
		return orderService.welcomeMessage(name);
	}
	
	@PostMapping("/welcome")
	public String welcomeByPOST(@RequestBody String name) // getting a request body for input. Input comes in request body
	{
		return orderService.welcomeMessage(name);
	}*/
	
	// get customer profile information
	@GetMapping("/customer/{profileID}")
	public Customer getCustomer(@PathVariable int profileID)
	{
		Customer c = orderService.getCustomer(profileID);
		return c;
	}
	
	// create customer profile
	@PostMapping("/customer")
	public ResponseEntity createCustomer(@RequestBody Customer c)
	{
		Customer c1 = orderService.createCustomer(c);
		System.out.println(c1.getProfileID());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c1.getProfileID()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/*@GetMapping("/customer/order/{orderID}")
	public List<CustomerOrder> getCustomerOrders(@PathVariable int profileID)
	{
		System.out.println("Came here");
		List<CustomerOrder> co = orderService.getCustomerOrderInfo(profileID);
		return co;
	}*/
	
	@GetMapping("/customer/order/{orderID}")
	public CustomerOrder getCustomerOrder(@PathVariable int orderID)
	{
		CustomerOrder co = orderService.getCustomerOrder(orderID);
		return co;
	}
	
	@PostMapping("/customer/order")
	public ResponseEntity createCustomerOrder(@RequestBody CustomerOrder co) throws Exception
	{
		CustomerOrder co1 = orderService.createCustomerOrder(co);
		System.out.println(co1.getOrderID());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderID}").buildAndExpand(co1.getOrderID()).toUri();
		return ResponseEntity.created(location).build();
		
	}
		
	
	@GetMapping("/customer/order/menu")
	public List<MenuItem> getEntireMenu()
	{
		List<MenuItem> menu = orderService.getMenu();
		return menu;
	}
	
	@PostMapping("/customer/order/item")
	public ResponseEntity addItemToOrder(@RequestBody CustomerOrderDetail cod) throws Exception
	{
		//try
		//{
			orderService.addItemToOrder(cod);
		//}
		/*catch(OrderNotFoundException one)
		{
			logger.error(one.getMessage(), one);
			return ResponseEntity.notFound().build();
		}
		catch(IllegalStateException ise)
		{
			logger.error(ise.getMessage(), ise);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}*/
		
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{quantity}").buildAndExpand(cod.getQuantity()).toUri();
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/customer/order/{orderID}/details")
	public List<OrderItem> getOrderDetails(CustomerOrderDetail cod)
	{
		List<OrderItem> list = orderService.getOrderDetails(cod);
		
		return list;
	}
	
	@PostMapping("customer/order/{orderID}/cancel")
	public ResponseEntity cancelCustomerOrder(@PathVariable int orderID) throws Exception
	{
		//try
		//{
			orderService.cancelOrder(orderID);
			return ResponseEntity.ok(null);
			
		//}
		/*catch(OrderNotFoundException ofe)
		{
			return ResponseEntity.notFound().build();
		}*/
		
		
	}

}
