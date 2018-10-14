# Restaurant-BlockingQueue
Customers can order food from a menu in the database


This project runs in Eclipse IDE. This is a Maven project, with a pom.xml to compile the xml file with dependencies used for this project, including libraries such as springboot, logger, etc. Once the program runs, the Tomcat server starts up.

Similar to a restaurant, a customer has a profile with their name, address, phone, etc. Each user is generated a profile ID that is auto incremented each time a new entry is made into the table. 

JDBC is used to interact with the database. When customers order food from the menu, they are given an orderID that auto increments per user order. The orderID is then sent to a queue where the queue will service each order. Using a thread, the program will keep running in the background even if the queue is empty.
__________________________________________________________________________________________________________________________
                                                            API
__________________________________________________________________________________________________________________________
In Postman, click Headers tab, and Set key to Content-Type, and Value to application/json

Customers can order food from a menu in a PostGreSQL database using Postman.

--------------------------------------------------------------------------------------------
Create Customer Profile: createCustomer()
POST /orders/customer/
{"customerName":"{name}","email":"{email}","address":"{address}","phoneNumber":"{phone}"}

--------------------------------------------------------------------------------------------
Create customer order: createCustomerOrder()
POST /orders/customer/order
Postman headers body: {"dateOrdered":"yyyy-MM-dd", "profileID":{profileID}}	

--------------------------------------------------------------------------------------------
Order item from menu: addItemToOrder()
POST /orders/customer/order/item
Postman headers body: {"itemID":{itemID},"orderID":{orderID}, "quantity": {quantity}}

--------------------------------------------------------------------------------------------
Get Customer order: getCustomerOrder()
GET /orders/customer/order/{ordernum}

--------------------------------------------------------------------------------------------
Get entire food menu: getEntireMenu()
GET /orders/customer/order/menu

--------------------------------------------------------------------------------------------
Get order details from customer
GET /orders/customer/order/{orderID}/details

--------------------------------------------------------------------------------------------
Get customer profile
GET /orders/customer/{profileID}

--------------------------------------------------------------------------------------------
