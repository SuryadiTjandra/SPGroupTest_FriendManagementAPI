# FriendManagementAPI

This API runs on the `[root]/friend` path.
Suppose root is `http://localhost:8080` and the user wants to access the `getFriends` service, 
then user would send a request to the URL `http://localhost:8080/friend/getFriends`

### Possible Operations

1. Retrieve a list of friends of a user.
   * Path : `/getFriends`
   * Sample Input :
   ```
   {
		"email":"example@example.com"
   }
   ```
   * Sample Output :
   ```
   {
		"success": true,
		"friends":[
			"friend1@example.com",
			"friend2@example.com",
			"friend3@example.com"
		],
		"count":3
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format
	  * 001 : The email address in the input is invalid
	  * 002 : The user specified by the email address does not exist
	  
2.  Retrieve a list of common friends of two users
    * Path : `/getCommonFriends`
    * Sample Input :
    ```
    {
		"friends":[
			"example@example.com",
			"example2@example.com"
		]
    }
    ```
    * Sample Output :
    ```
    {
		"success": true,
		"friends":[
			"commonfriend1@example.com",
			"commonfriend2@example.com"
		],
		"count":2
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format, and the 'friends' field length is not 2
	  * 001 : Any of the email addresses in the input are invalid
	  * 002 : The users specified by the email addresses do not exist
	  * 003 : The two email addresses in the input are the same
	  
3.  Create a friend connection between two users
    * Path : `/makeFriends`
    * Sample Input :
    ```
    {
		"friends":[
			"example@example.com",
			"example2@example.com"
		]
    }
    ```
    * Sample Output :
    ```
    {
		"success": true
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format, and the 'friends' field length is not 2
	  * 001 : Any of the email addresses in the input are invalid
	  * 002 : The users specified by the email addresses do not exist
	  * 003 : The two email addresses in the input are the same
	  * 100 : The two users are already friends
	  * 101 : One of the users have blocked the others
	  
4.  Let a user subscribe to another user
    * Path : `/subscribe`
    * Sample Input :
    ```
    {
		"requestor":"example@example.com",
		"target":"example2@example.com"
    }
    ```
    * Sample Output :
    ```
    {
		"success": true
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format
	  * 001 : Any of the email addresses in the input are invalid
	  * 002 : The users specified by the email addresses do not exist
	  * 003 : The two email addresses in the input are the same
	  * 200 : The requestor already subscribed to the target

5.  Let a user block updates from another user
    * Path : `/block`
    * Sample Input :
    ```
    {
		"requestor":"example@example.com",
		"target":"example2@example.com"
    }
    ```
    * Sample Output :
    ```
    {
		"success": true
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format
	  * 001 : Any of the email addresses in the input are invalid
	  * 002 : The users specified by the email addresses do not exist
	  * 003 : The two email addresses in the input are the same
	  * 300 : The requestor already blocked updates from the target
	  
6.  Let a user post an update. This will return a list of email addresses that will receive the update.
    * Path : `/postUpdate`
    * Sample Input :
    ```
    {
		"sender":"example@example.com",
		"target":"Good morning mentioned@example.com"
    }
    ```
    * Sample Output :
    ```
    {
		"success": true,
		"recipients":[
			"friend1@example.com",
			"friend2@example.com",
			"subscriber@example.com",
			"mentioned@example.com"
		]
	}
	```
	* Possible errors : 
	  * 000 : The input does not follow the correct format
	  * 001 : The sender's email addresses in the input is invalid
	  * 002 : The user specified by the sender email address does not exist
	  
### Errors

If any of the above operations fail, they will output an error response instead. The error response has the following format

    ```
    {
		"success": false,
		"errorCode": "000",
		"message":"Sample error message"
	}
	```
	
The errors are:
1. Generic errors. These errors begin with "0" and may happen in many operations.
   * 000 : Wrong input format
   * 001 : Invalid email address
   * 002 : User does not exist
   * 003 : Input specifies the same users
2. Friend errors. These errors begin with "1".
   * 100 : Trying to make friend connection between two users who are already friends
   * 101 : Trying to make friend connection between two users where one of them has blocked the other
3. Subscription errors. These errors begin with "2".
   * 200 : One user tries to subsribe to another user whom they have already subscribed to
4. Block errors. These errors begin with "3".
   * 300 : One user tries to block another user whom they have already blocked
5. Unknown errors. These errors begin with "9" and are results of things outside the application, such as database connection.
   * 999 : Unknown error
   
### Initial Database

The application currently uses in-memory database, so when the application is shut down, all data will be reset.
During startup, the database is initialized with the following data.

There are 10 users in the database:
* alice@a.com (Alice)
* bob@b.com (Bob)
* carol@c.com (Carol)
* dennis@d.com (Dennis)
* eve@e.com (Eve)
* frank@f.com (Frank)
* grace@g.com (Grace)
* harold@h.com (Harold)
* ingrid@i.com (Ingrid)
* jennifer@j.com (Jennifer)

Their relationships are:
* Alice is friends with Bob and Dennis. Alice blocks updates from Carol.
* Bob is friends with Alice, Carol, and Dennis. Bob blocks updates from Eve.
* Carol is friends with Bob and Dennis.
* Dennis is friends with Alice, Bob, Carol, and Eve.
* Eve is friends with Dennis. Eve subscribes to Alice, Bob, and Carol.
* Frank, Grace, Harold, Ingrid, and Jennifer have no relationships with anyone else.
