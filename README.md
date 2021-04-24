## Goals
The purpose of this repository is to develop a ticketing backend system.
## Description
This repository allows clients create and send tickets to a user via a REST API, and the user can respond to it.
The response will be notified via mail
## Database
I have used postgresSQL for development and kindly change it to your need by adding dependency and configuring the same in application.properties
# EndPoints and Parameters
#####1.a. to create a ticket
HttpType: POST  
Url: http://localhost:8080/ticket-handler-service/ticket/createTicket  
Content Type: Json  
Body: {
    "type": "BUG",
    "title": "DEFAULT_BUG",
    "description": "DEFAULT_DESCRIPTION",
    "createdUser": "DEFAULT_USER",
    "assignedToUser": "User1",
    "customer": "Customer1",
    "status": "OPEN",
    "priority": "NORMAL"
}
#####1.b. List all the tickets
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/getAllTickets 

#####1.c. List all the tickets filtered by
######i)assigned agent
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/getTicketsByAssignedUser  
Content Type: Json  
Body: ["User1"]  
######ii)customer
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/getTicketsByCustomer  
Content Type: Json  
Body: ["Customer1"]  
######iii)status
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/getTicketsByStatus  
Content Type: Json  
Body: ["OPEN", "RESOLVED"]  

######C)To filter based on all three combined
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/getFilteredTickets  
Content Type: Json  
Body: {
    "assignedToUser": ["User1"],
    "status":["OPEN"],
    "customer": ["Customer1"]
}  


#####1.d. List details of a given ticket id
HttpType: GET   
Url: http://localhost:8080/ticket-handler-service/ticket/id-8 

#####1.e. edit details about the ticket
HttpType: PUT  
Url: http://localhost:8080/ticket-handler-service/ticket/editTicket  
Content Type: Json  
Body: {
	"ticketId": 8,
    "type": "BUG",
    "title": "DEFAULT_BUG",
    "description": "DEFAULT_DESCRIPTION",
    "createdUser": "DEFAULT_USER",
    "assignedToUser": "User1",
    "customer": "Customer1",
    "status": "OPEN",
    "priority": "NORMAL"
}
#####1.f. update status for the ticket
HttpType: PUT  
Url: http://localhost:8080/ticket-handler-service/ticket/updateStatus  
Content Type: Json  
Body: {
	"ticketId": 8,
    "status": "RESOLVED",
}

#####1.g. assign the ticket to an agent
HttpType: PUT  
Url: http://localhost:8080/ticket-handler-service/ticket/assignAgent  
Content Type: Json  
Body: {
	"ticketId": 8,
	"assignedToUser": "User1"
}  
Note: If while creating itself, if assignesToUser is Empty, automatically assigned to User with less work load.  

#####1.h. to add response to the ticket
HttpType: POST  
Url: http://localhost:8080/ticket-handler-service/ticket/addResponse  
Content Type: Json  
Body: {
	"ticketId": 8,
	"response": "DEFAULT_RESPONSE"
}  

#####1.i. delete the ticket
HttpType: DELETE  
Url: http://localhost:8080/ticket-handler-service/ticket/deleteTicket  
Content Type: Json  
Body: {
	"ticketId": 8
}  

#####2. send email to the customer when agent adds a response
already achieved in 1.h  

####3. Assign the ticket equally to available agents
already acheived in 1.a  
Still extra api provided  
 
HttpType: PUT  
Url: http://localhost:8080/ticket-handler-service/ticket/autoSelectAssignee  
Content Type: Json  
Body: {
	"ticketId": 8
}  

####4. Update tickets that were marked as Resolved status 30 days ago as closed status
Please check ScheduleStatus.java for scheduled method.  
Still an extra api is provided.  

HttpType: PUT  
Url: http://localhost:8080/ticket-handler-service/ticket/changeResolvedToClosed  
Content Type: Json  

####4. Master Methods for inserting data in user master and customer master tables

Note: Data will be deleted and inserted as a whole.
  
###### Customer Data  
HttpType: POST  
Url: http://localhost:8080/ticket-handler-service/data/addNewCustomers  
Content Type: Json  
Body: [{
    "customer": "Customer1",
    "emailId": "shame995@gmail.com"
},{
    "customer": "Customer2",
    "emailId": "shame995@gmail.com"
},{
    "customer": "Customer3",
    "emailId": "shame995@gmail.com"
},{
    "customer": "Customer4",
    "emailId": "shame995@gmail.com"
}]  

###### User Data
HttpType: POST  
Url: http://localhost:8080/ticket-handler-service/data/addNewUsers  
Content Type: Json  
Body: [{
    "userName": "User1",
    "emailId": "shame995@gmail.com"
},{
    "userName": "User2",
    "emailId": "shame995@gmail.com"
},{
    "userName": "User3",
    "emailId": "shame995@gmail.com"
},{
    "userName": "User4",
    "emailId": "shame995@gmail.com"
}]   

