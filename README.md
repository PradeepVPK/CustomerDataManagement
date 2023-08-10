Sunbase API Integration
=======================

This project demonstrates the integration of Sunbase APIs for managing customer data. The APIs provided allow  to authenticate users, create, read, update, and delete customer records. Below are the details of each API and the required parameters.

# Output
You can view my project output demo here.
[Demo video](https://drive.google.com/file/d/12rF6JhMS0lmLPustuB3WFd0vlcRFu6Nl/view?usp=drivesdk)

### 1\. Authenticate User

Authenticate the user using their credentials to receive a bearer token for further API calls.

-   Path: <https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp>
-   Method: POST
-   Body:

    jsonCopy code

    `{
      "login_id": "test@sunbasedata.com",
      "password": "Test@123"
    }`

-   Response: On successful authentication, the API will return a bearer token.

### 2\. Create a New Customer

Create a new customer by providing the required customer details.

-   Path: <https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp>
-   Method: POST
-   Parameters:

    `cmd: create`

-   Header:

    makefileCopy code

    `Authorization: Bearer <token_recieved_in_authentication_API_call>`

-   Body:

    jsonCopy code

    `{
      "first_name": "Jane",
      "last_name": "Doe",
      "street": "Elvnu Street",
      "address": "H no 2",
      "city": "Delhi",
      "state": "Delhi",
      "email": "sam@gmail.com",
      "phone": "12345678"
    }`

-   Response:
    -   Success: 201, Successfully Created
    -   Failure: 400, First Name or Last Name is missing

### 3\. Get Customer List

Retrieve the list of customers.

-   Path: <https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp>
-   Method: GET
-   Parameters:

    makefileCopy code

    `cmd: get_customer_list`

-   Header:

    makefileCopy code

    `Authorization: Bearer <token_recieved_in_authentication_API_call>`

-   Response:
    -   200

    jsonCopy code

    `[{
      "first_name": "Jane",
      "last_name": "Doe",
      "street": "Elvnu Street",
      "address": "H no 2",
      "city": "Delhi",
      "state": "Delhi",
      "email": "sam@gmail.com",
      "phone": "12345678"
    }]`

### 4\. Delete a Customer

Delete a specific customer using their UUID.

-   Path: <https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp>
-   Method: POST
-   Parameters:

    javascriptCopy code

    `cmd: delete
    uuid: <uuid of the specific customer> `

-   Header:

    makefileCopy code

    `Authorization: Bearer <token_recieved_in_authentication_API_call>`

-   Response:
    -   200, Successfully deleted
    -   500, Error Not deleted
    -   400, UUID not found

### 5\. Update a Customer

Update the details of a specific customer using their UUID.

-   Path: <https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp>
-   Method: POST
-   Parameters:

    makefileCopy code

    `cmd: update
    uuid: <uuid of the specific customer>`

-   Header:

    makefileCopy code

    `Authorization: Bearer <token_recieved_in_authentication_API_call>`

-   Body:

    jsonCopy code

    `{
      "first_name": "Jane",
      "last_name": "Doe",
      "street": "Elvnu Street",
      "address": "H no 2",
      "city": "Delhi",
      "state": "Delhi",
      "email": "sam@gmail.com",
      "phone": "12345678"
    }`

-   Response:
    -   200, Successfully Updated
    -   500, UUID not found
    -   400, Body is Empty

### Notes

-   If you provide an incorrect value for the `cmd` parameter, you will receive a 500, Invalid Command error.
-   If authentication fails, you will receive a 401, Invalid Authorization error.
-   You can use tools like Postman to test these APIs.
-   The project consists of three screens: Login Screen, Customer List Screen, and Add a New Customer Screen.
-   The UI can be a basic HTML table, form, and buttons to perform the required actions.
