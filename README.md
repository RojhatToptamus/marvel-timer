# marvel-timer
This is a stopwatch program which counts down in all number formats.


HOW THE PROGRAM WORKS
After clicking the Start Button, the program starts counting down and it is in the competitor's hands to stop the countdown. 
The countdown ends when the stop button is clicked. the competitor uses the save button to save the result and thus saves the score.
Clicking on the results button shows the competitor result and the result of the whole competition appears below the result button.

THE STRUCTURE  OF THE PROGRAM
The program consists of two main classes. One of these two classes is the class in which the program is running, with swing applications and algorithms. 
the other is the database connection and database operations. Thus, avoided from code clutter

A database named dbwork was created with the PHPMyAdmin interface. A table with 4 columns named competitors has been created in dbwork.
Database connection operations and sorting operations were performed in dbconnection class.
Clicking the save button in our main class will save the competitor information to our MySQL database.

Since the program is currently running on the localhost, the database should be imported via phpMyAdmin and a Wampserver or similar local server program should be used.

