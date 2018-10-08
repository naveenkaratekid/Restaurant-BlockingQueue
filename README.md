# Restaurant-BlockingQueue
Customers can order food from a menu in the database


This project runs in Eclipse IDE. This is a Maven project, with a pom.xml to compile the xml file with dependencies used for this project, including libraries such as springboot, logger, etc. Once the program runs, the Tomcat server starts up.



Similar to a restaurant, a customer has a profile with their name, address, phone, etc. Each user is generated a profile ID that is auto incremented each time a new entry is made into the table. 

Customers can order food from a menu in a PostGreSQL database using Postman. 

The URL to see the enture menu: 
http://localhost:8080/orders/customer/order/menu

JDBC is used to interact with the database. When customers order food from the menu, they are given an orderID that auto increments per user order. The orderID is then sent to a queue where the queue will service each order. Using a thread, the program will keep running in the background even if the queue is empty.

Using postman, the user can see their profile information:
http://localhost:8080/orders/customer/{profileID}

