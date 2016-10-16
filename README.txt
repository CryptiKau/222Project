UPDATED 17/10
-Database functions moved to own private class and are now called within Program functions rather than run through them
-All functions and windows linked via passed DBClass Object
-View Profile has been revised to inlcude Date
-Basis for Authenticated menu loaded(For now if your Sec Level is larger than 1 you will get a message saying your page is not read)
-Registration has not been changed yet.

UPDATED 16/10
-Database switch to MySQL
-Login now works with MySQL
-Registration is complete at Basic level
-Profile now reads in basic details Username email etc.
IMPORTANT: You will need to get the new Java library for MySQL https://dev.mysql.com/downloads/connector/j/5.1.html
YOU WILL ALSO NEED TO SET UP YOUR OWN TEST DATA!
In each function you will need to change the DB to your own!
The tables have also been changed SecLevel was added to the base SuperUser.


We will need to complete the the functions in each of these areas before we move to the next.

//////////////////////////////////////////////////////////////////////////

USER ACCESS

Complete
--------------
Login Screen          //This has been finished

Semi- Complete
--------------
Register Screen       //This has been basically finished DATA VALIDATION NEEDS TO BE COMPLETED
View Profile          //This has been basically finished retreival of Rep and numReports needs to be done

Not Complete
--------------
Passing Username into next menu
Search Bugs

///////////////////////////////////////////////////////////////////////

AUTHENTICATED USER ACCESS

Not Complete
-------------
Main Menu for AUTHENTICATED USERS
Profile page for AUTHENTICATED USERS (Allows edits)
Search Bugs that allows to submit comments and Register Interest
Request Report
