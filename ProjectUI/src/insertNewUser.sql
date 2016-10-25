USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.insertNewUser;
CREATE DEFINER=`root`@`localhost`
PROCEDURE `insertNewUser`(IN uName varchar(20), IN fName varchar(20),
																														IN lName varchar(20), IN email varchar(20),
																														IN pWord varchar(20))
	BEGIN
		INSERT INTO SuperUser (SecLevel, UserName, FName, LName, Email, Password, JoinedDate)
		VALUES (0, uName, fName, lName, email, pWord, CURDATE());
		INSERT INTO RegisteredUser (UserID, UserReputation, BugReport, AccountStatus) VALUES
			((SELECT SuperUser.UserID FROM SuperUser WHERE SuperUser.UserName = uName), 'New', 0, 'Active');

	END;