DROP DATABASE IF EXISTS BugTrackerPrime;

CREATE DATABASE BugTrackerPrime;

USE BugTrackerPrime;

CREATE TABLE SuperUser (
	UserID int NOT NULL AUTO_INCREMENT,
	AccessRights int NOT NULL,
	UserName varchar(30) UNIQUE,
	FName varchar(60),
	LName varchar(60),
	Email varchar(40),
	Password varchar(16),
	JoinedDate DATE,
	PRIMARY KEY(UserID)
);

CREATE TABLE RegisteredUser (
	UserID int NOT NULL DEFAULT 0,
    Role varchar(20),
    UserReputation int,
	BugReportCount int,
	AccountStatus ENUM('Active', 'Suspended', 'Deleted'),
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID)
		ON DELETE CASCADE
);

CREATE TABLE SysAdminUser (
	UserID int NOT NULL DEFAULT 0,
    Role varchar(20),
	# 	AssignedBug int,
	# 	AttachmentID int,
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID)
		ON DELETE CASCADE
);


CREATE TABLE Bug (
	BugID int NOT NULL AUTO_INCREMENT,
	BugName varchar(50) NOT NULL UNIQUE,
	Product varchar(50),
	Component varchar(50),
	Version varchar(10),
	OperatingSystem varchar(50),
	PRIMARY KEY (BugID)
);

CREATE TABLE BugReports (
	BugReportID int NOT NULL AUTO_INCREMENT,
	CreationTimestamp DATE,
	ShortDescription varchar(200),
	DeltaTimestamp DATE,
	BugID int,
	BugStatus ENUM('Reported', 'Approved', 'Progressing', 'Solved'),
	Resolution varchar(500),
	Keywords varchar(50),
	Priority ENUM('Low', 'Medium', 'High', 'Emergency'),
	BugSeverity ENUM ('Critical', 'Major', 'Minor', 'Cosmetic'),
	ReporterID int,
	AssignedTo int,
    LongDescription VARCHAR(500),
    PatchLocation VARCHAR(400),
    PatchUser int,
	PRIMARY KEY (BugReportID),
	FOREIGN KEY (BugID)
		REFERENCES Bug(BugID),
	FOREIGN KEY (ReporterID)
		REFERENCES SuperUser(UserID),
	FOREIGN KEY (AssignedTo)
		REFERENCES SuperUser(UserID),
	FOREIGN KEY (PatchUser)
		REFERENCES SuperUser(UserID)
);	

CREATE TABLE Comments (
	CommentID int NOT NULL AUTO_INCREMENT,
	UserID int,
    BugReportID int,
	CreationTimestamp DATE,
	CommentText varchar(500),
	PRIMARY KEY (CommentID),
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID),
	FOREIGN KEY (BugReportID)
		REFERENCES BugReports(BugReportID)
);

##### CREATE PROCEDURES/FUNCTIONS HERE ######
USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.setCurrentUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setCurrentUser`(uName VARCHAR(30))
	BEGIN
		SELECT SuperUser.UserID, UserName, FName, LName, Email, JoinedDate, UserReputation, AccountStatus, AccessRights, Password FROM
			BugTrackerPrime.SuperUser JOIN BugTrackerPrime.RegisteredUser WHERE UserName = uname;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.setCurrentAdmin;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setCurrentAdmin`(uName VARCHAR(30))
	BEGIN
		SELECT SuperUser.UserID, UserName, FName, LName, Email, JoinedDate, Role, AccessRights, Password FROM
			BugTrackerPrime.SuperUser JOIN BugTrackerPrime.SysAdminUser WHERE UserName = uname;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.insertNewUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `insertNewUser`(IN uName varchar(20), IN fName varchar(20),
													IN lName varchar(20), IN email varchar(20),
													IN pWord varchar(20))
	BEGIN
		INSERT INTO SuperUser (AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
		VALUES (1, uName, fName, lName, email, pWord, current_date());
		INSERT INTO RegisteredUser (UserID, UserReputation, BugReportCount, AccountStatus) VALUES
			((SELECT SuperUser.UserID FROM SuperUser WHERE SuperUser.UserName = uName), 0, 0, 'Active');


	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.insertNewUserAdmin;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `insertNewUserAdmin`(IN ascLvl int, IN uName varchar(20), IN fName varchar(20),
													IN lName varchar(20), IN email varchar(20),
													IN pWord varchar(20))
	BEGIN
		INSERT INTO SuperUser (AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
		VALUES (ascLvl, uName, fName, lName, email, pWord, current_date());
		INSERT INTO RegisteredUser (UserID, UserReputation, BugReportCount, AccountStatus) VALUES
			((SELECT SuperUser.UserID FROM SuperUser WHERE SuperUser.UserName = uName), 0, 0,'Active');

	END //
DELIMITER ;


DROP FUNCTION IF EXISTS BugTrackerPrime.verifyLogIn;
DELIMITER //
CREATE DEFINER='root'@'localhost'
FUNCTION `verifyLogIn`(uName VARCHAR(30), pass VARCHAR(16)) RETURNS INT DETERMINISTIC
	BEGIN
		DECLARE sLevel INT;
		DECLARE pWord VARCHAR(16);

		SELECT Password INTO pWord FROM BugTrackerPrime.SuperUser WHERE UserName = uName;

		IF(pWord = pass)
		THEN SELECT AccessRights INTO sLevel FROM BugTrackerPrime.SuperUser WHERE SuperUser.UserName = uName;
		ELSEIF(pWord != pass)
			THEN SET sLevel = 0;
		END IF;

		RETURN (sLevel);
	END //
DELIMITER ;

USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.updateAdmin;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `updateAdmin`(uName VARCHAR(30), fName VARCHAR(60), lName VARCHAR(60), email VARCHAR(40),
												pWord VARCHAR(40))
	BEGIN
		UPDATE SuperUser SET FName = fName, LName = lName, Email = email, Password = pWord WHERE UserName = uname;
	END //
DELIMITER ;

USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.updateAdminNoP;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `updateAdminNoP`(uName VARCHAR(30), fName VARCHAR(60), lName VARCHAR(60), email VARCHAR(40))
	BEGIN
		UPDATE SuperUser SET FName = fName, LName = lName, Email = email WHERE UserName = uname;
	END //
DELIMITER ;
#
DROP PROCEDURE IF EXISTS BugTrackerPrime.updateUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `updateUser`(uName VARCHAR(30), fName VARCHAR(60), lName VARCHAR(60), email VARCHAR(40),
												pWord VARCHAR(40), rep INT, ascLVL INT)
	BEGIN
		UPDATE SuperUser SET FName = fName, LName = lName, Email = email, Password = pWord, AccessRights = ascLVL WHERE UserName = uname ;
		UPDATE RegisteredUser SET UserReputation = rep WHERE UserID = (SELECT SuperUser.UserID FROM SuperUser WHERE UserName = uName);
	END //
DELIMITER ;

USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.updateUserNoP;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `updateUserNoP`(uName VARCHAR(30), fName VARCHAR(60), lName VARCHAR(60), email VARCHAR(40), rep INT, ascLVL INT)
	BEGIN
		UPDATE SuperUser SET FName = fName, LName = lName, Email = email, AccessRights = ascLVL  WHERE UserName = uname;
		UPDATE RegisteredUser SET UserReputation = rep WHERE UserID = (SELECT SuperUser.UserID FROM SuperUser WHERE UserName = uName);
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.deleteUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE deleteUser(userToDel VARCHAR(30))
	BEGIN
		DELETE FROM BugTrackerPrime.SuperUser WHERE UserName = userToDel;
	END //
DELIMITER ;

#

##NATHAN: NEW UPDATED
DROP PROCEDURE IF EXISTS BugTrackerPrime.setSearchDetailsByStatus;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setSearchDetailsByStatus`(bStatus ENUM('Reported', 'Approved', 'Progressing', 'Solved'))
	BEGIN
		SELECT DISTINCT BugReportID, CreationTimestamp, ShortDescription, BugStatus, Priority, bug.BugName, superuser.UserName
        FROM  BugTrackerPrime.BugReports
        LEFT JOIN BugTrackerPrime.bug 
        ON BugReports.BugID = Bug.BugID
        LEFT JOIN BugTrackerPrime.superuser 
        ON BugReports.ReporterID = superuser.UserID
		WHERE BugStatus = bStatus;
	END //
DELIMITER ;
##NATHAN: ADDED
DROP PROCEDURE IF EXISTS BugTrackerPrime.setSearchDetailsByPriority;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setSearchDetailsByPriority`(bPrio ENUM('Low', 'Medium', 'High', 'Emergency'))
	BEGIN
		SELECT DISTINCT BugReportID, CreationTimestamp, ShortDescription, BugStatus, Priority, bug.BugName, superuser.UserName
        FROM  BugTrackerPrime.BugReports
        LEFT JOIN BugTrackerPrime.bug 
        ON BugReports.BugID = Bug.BugID
        LEFT JOIN BugTrackerPrime.superuser 
        ON BugReports.ReporterID = superuser.UserID
		WHERE Priority = bPrio;
	END //
DELIMITER ;

##NATHAN: ADDED
DROP PROCEDURE IF EXISTS BugTrackerPrime.setSearchDetailsByUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setSearchDetailsByUser`(repName varchar(20))
	BEGIN
		SELECT DISTINCT BugReportID, CreationTimestamp, ShortDescription, BugStatus, Priority, bug.BugName, superuser.UserName
        FROM  BugTrackerPrime.BugReports
        LEFT JOIN BugTrackerPrime.bug 
        ON BugReports.BugID = Bug.BugID
        LEFT JOIN BugTrackerPrime.superuser 
        ON BugReports.ReporterID = superuser.UserID
		WHERE superuser.UserName = repName;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.setSearchDetailsByKeywords;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setSearchDetailsByKeywords`(kyWds varchar(200))
	BEGIN
		SELECT DISTINCT BugReportID, CreationTimestamp, ShortDescription, BugStatus, Priority, bug.BugName, superuser.UserName
        FROM  BugTrackerPrime.BugReports
        LEFT JOIN BugTrackerPrime.bug 
        ON BugReports.BugID = Bug.BugID
        LEFT JOIN BugTrackerPrime.superuser 
        ON BugReports.ReporterID = superuser.UserID
		WHERE BugReports.Keywords LIKE kyWds;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.setFindComments;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setFindComments`(brID INT)
	BEGIN
		SELECT DISTINCT Comments.CreationTimestamp, CommentText, superuser.UserName
        FROM  BugTrackerPrime.Comments
        LEFT JOIN BugTrackerPrime.BugReports 
        ON Comments.BugReportID = BugReports.BugReportID
        LEFT JOIN BugTrackerPrime.superuser 
        ON Comments.UserID = superuser.UserID
		WHERE BugReports.BugReportID = brID;
	END //
DELIMITER ;

##NATHAN: UPDATED
DROP PROCEDURE IF EXISTS BugTrackerPrime.populateExtReportDetails;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `populateExtReportDetails`(bId INT)
	BEGIN
		SELECT BugName, Bug.BugID, BugStatus, Priority, CreationTimestamp, Product, OperatingSystem, Component, Version,
			BugSeverity, a.UserName, b.UserName, LongDescription, Resolution, x.UserReputation, a.UserID
		FROM BugTrackerPrime.Bug 
        LEFT JOIN BugTrackerPrime.BugReports 
        ON Bug.BugID = BugReports.BugID
        LEFT JOIN BugTrackerPrime.superuser a
        ON BugReports.ReporterID = a.UserID
        LEFT JOIN BugTrackerPrime.registereduser x
        ON BugReports.ReporterID = x.UserID
        LEFT JOIN BugTrackerPrime.superuser b
        ON BugReports.AssignedTo = b.UserID
		WHERE BugReportID = bId;
	END //
DELIMITER ;


##########################
DROP PROCEDURE IF EXISTS BugTrackerPrime.insertNewComment;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `insertNewComment`(IN uID int, IN brID INT, cmnt varchar(500))
	BEGIN
		INSERT INTO Comments (userID, bugReportID, creationTimestamp, commentText)
		VALUES (uID, brID, CURDATE(), cmnt);
	END //
DELIMITER ;

################################# REPORT PROCEDURES #####################################################
DROP PROCEDURE IF EXISTS BugTrackerPrime.newBugReports;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `newBugReports`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE());
	END //
DELIMITER ;

USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.countReportsAssigned;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countReportsAssigned`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND AssignedTo IS NOT NULL ;
	END //
DELIMITER ;

USE BugTrackerPrime;
DROP PROCEDURE IF EXISTS BugTrackerPrime.countReportsUnassigned;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countReportsUnassigned`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND AssignedTo IS NULL ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countReportedBugs;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countReportedBugs`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugStatus = 'Reported' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countProgressBugs;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countProgressBugs`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugStatus = 'Progressing' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countSolvedBugs;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countSolvedBugs`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugStatus = 'Solved' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countLowPriority;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countLowPriority`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND Priority = 'Low' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countMedPriority;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countMedPriority`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND Priority = 'Medium' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countHighPriority;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countHighPriority`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND Priority = 'High' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countEmergencyPriority;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countEmergencyPriority`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND Priority = 'Emergency' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countCosmeticSev;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countCosmeticSev`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugSeverity = 'Cosmetic' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countMinorSev;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countMinorSev`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugSeverity = 'Minor' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countMajorSev;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countMajorSev`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugSeverity = 'Major' ;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.countCriticalSev;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `countCriticalSev`(timeRange INT)
	BEGIN
		SELECT COUNT(*) FROM BugTrackerPrime.BugReports WHERE (CreationTimestamp BETWEEN DATE_SUB(CURDATE(), INTERVAL timeRange DAY) AND CURDATE()) AND BugSeverity = 'Critical' ;
	END //
DELIMITER ;

######################### Submit Report ##############################################################################
DROP PROCEDURE IF EXISTS BugTrackerPrime.submitReport;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `submitReport`(bName VARCHAR(50), prod VARCHAR(50), compon VARCHAR(50), vers VARCHAR(10), oSys VARCHAR(50),
                         bStatus ENUM('Reported', 'Approved', 'Progressing', 'Solved'), kWords VARCHAR(50), prio ENUM('Low', 'Medium', 'High', 'Emergency'),
  bSev ENUM ('Critical', 'Major', 'Minor', 'Cosmetic'), lDesc VARCHAR(500), repID VARCHAR(30), shortDesc VARCHAR(200))
  BEGIN
    INSERT INTO BugTrackerPrime.Bug (BugName, Product, Component, Version, OperatingSystem)
    VALUES (bName, prod, compon, vers, oSys);

    INSERT INTO BugTrackerPrime.BugReports (CreationTimestamp, BugID, BugStatus, Keywords, Priority, BugSeverity, LongDescription , ReporterID, ShortDescription)
    VALUES (current_date(), (SELECT BugID FROM BugTrackerPrime.Bug WHERE BugName = bName), bStatus, kWords, prio, bSev, lDesc, repID, shortDesc);
  END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.updateReport;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `updateReport`(bName VARCHAR(50), prod VARCHAR(50), compon VARCHAR(50), vers VARCHAR(10), oSys VARCHAR(50),
                         bStatus ENUM('Reported', 'Approved', 'Progressing', 'Solved'), kWords VARCHAR(50), prio ENUM('Low', 'Medium', 'High', 'Emergency'),
  bSev ENUM ('Critical', 'Major', 'Minor', 'Cosmetic'), lDesc VARCHAR(500), repID INT, bID INT, reso VARCHAR(200), assign VARCHAR(20), userRep INT)
  BEGIN
    UPDATE BugTrackerPrime.Bug SET BugName = bName, Product = prod, Component = compon, Version = vers, OperatingSystem = oSys
    WHERE Bug.BugID = bID;

	UPDATE BugTrackerPrime.BugReports 
    SET DeltaTimestamp = current_date(), BugStatus = bStatus, Priority = prio, BugSeverity = bSev, 
    LongDescription = lDesc, Resolution = reso,
    AssignedTo = (SELECT UserID FROM superuser WHERE superuser.UserName = assign)
	WHERE BugReports.BugID = bID;
    
    UPDATE BugTrackerPrime.registereduser SET UserReputation = userRep
    WHERE registereduser.UserID = repID;
  END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.getDevs;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `getDevs`()
  BEGIN
    SELECT UserName FROM superuser WHERE AccessRights = 3;
  END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.getUsers;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `getUsers`()
  BEGIN
    SELECT UserName FROM superuser;
  END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.getEmails;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `getEmails`()
  BEGIN
    SELECT Email FROM superuser;
  END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.getPatch;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `getPatch`(brID INT)
	BEGIN
		SELECT PatchLocation FROM BugTrackerPrime.bugreports WHERE bugreports.BugReportID = brID;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.getPatchUser;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `getPatchUser`(brID INT)
	BEGIN
		SELECT superuser.UserName FROM BugTrackerPrime.superuser
        LEFT JOIN BugTrackerPrime.bugreports
        ON superuser.UserID = bugreports.PatchUser
        WHERE bugreports.BugReportID = brID;
	END //
DELIMITER ;

DROP PROCEDURE IF EXISTS BugTrackerPrime.setPatch;
DELIMITER //
CREATE DEFINER='root'@'localhost'
PROCEDURE `setPatch`(pl VARCHAR(300), pUser INT, brID INT)
	BEGIN
		UPDATE BugTrackerPrime.bugreports SET PatchLocation = pl, PatchUser = pUser WHERE bugreports.BugReportID = brID;
	END //
DELIMITER ;

#### CREATE NEW USERS AND PRIVS HERE ####
DROP USER 'newUser'@'localhost';
CREATE USER 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT INSERT, SELECT ON BugTrackerPrime.SuperUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT INSERT, SELECT ON BugTrackerPrime.RegisteredUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.insertNewUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setCurrentUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setCurrentAdmin TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON FUNCTION BugTrackerPrime.verifyLogIn TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.updateAdmin TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.updateAdminNoP TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.insertNewUserAdmin TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setSearchDetailsByStatus TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setSearchDetailsByUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setSearchDetailsByPriority TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setFindComments TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.populateExtReportDetails TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.insertNewComment TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.deleteUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.updateUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.updateUserNoP TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
########################################## REPORT PROCEDURE PRIVS ####################################################
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.newBugReports TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countReportsAssigned TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countReportsUnassigned TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countReportedBugs TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countProgressBugs TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countSolvedBugs TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countLowPriority TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countMedPriority TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countHighPriority TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countEmergencyPriority TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countCosmeticSev TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countMinorSev TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countMajorSev TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.countCriticalSev TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;

GRANT EXECUTE ON PROCEDURE BugTrackerPrime.submitReport TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.updateReport TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.getDevs TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.getUsers TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.getEmails TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.getPatch TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setPatch TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.getPatchUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.setSearchDetailsByKeywords TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;

### INSERT TEST DATA HERE ####

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (5, 'nathan','Nathan', 'Humphries', 'nathan@btp.com', 'pass123', current_date());
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'nathan'),'Admin', 100 ,0,'Active');
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'nathan'), 'Admin');

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (1, 'mbevs','Michael', 'Beavis', 'm@btp.com', 'pass123', current_date());
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'mbevs'), 'User');
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'mbevs'),'User', -32 ,0,'Active');

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (4, 'jR','Jacob', 'Rigney', 'jr@btp.com', 'pass123', current_date());
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'jR'), 'Triage');
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'jR'),'Triage', 10 ,0,'Active');

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (3, 'DavetheDev','Dave', 'User', 'buser@btp.com', 'pass123', current_date());
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'DavetheDev'), 'Developer');
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'DavetheDev'),'Developer', 15 ,0,'Active');

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (2, 'brad','Brad', 'McAlpine', 'buser@btp.com', 'pass123', current_date());
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'brad'), 'Reporter');
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'brad'),'Reporter', 103 ,0,'Active');

INSERT INTO SuperUser(AccessRights, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (5, 'buser','Bob', 'User', 'buser@btp.com', 'pass123', current_date());
INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'buser'), 'SysAdmin');
INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'buser'),'SysAdmin', 11 ,0,'Active');

#INSERTS A BUG AND A BUG REPORT
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Bug 1', 'Bug Tracker Prime', 'Database', 'v0.4', 'Windows');

INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Bug 2', 'Bug Tracker Prime', 'Database', 'v0.4', 'Windows');

INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Bug 3', 'Bug Tracker Prime', 'Database', 'v0.4', 'Windows');


#############
INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'this is a short description', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Bug 1'), 'Reported',
							 'This is a resolution for bug 1', 'Some Keywords here', 'Medium', 'Minor', 2, 3,
								'This is Bug 1, its a very nice bug and just wants to be your friend');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'PROGRESSING THIS IS GREAT', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Bug 2'), 'Progressing',
							 'This is a resolution for bug 2', 'Some Keywords here', 'Medium', 'Minor', 2, 3,
								'This is Bug 2, Bug 2 is not nice, it likes darkness, moist moist areas and the smell of burning rubber');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'THIS HAS BEEN SOLVED!!!', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Bug 3'), 'Solved',
							 'This is a resolution for bug 3', 'Some Keywords here', 'Medium', 'Minor', 2, 3,
								'This is Bug 3, it is also a very very nice bug. It likes sunshine, beaches and playing in the sun');

INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('BT Crash', 'Bug Tracker Prime', 'Login Screen', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'BT Crashes on start up', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'BT Crash'), 'Progressing',
							 ' ', 'crash on start up bt ', 'High', 'Major', 1, 5,
								'The bug crashes the program on start up with no errors to be found');
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('BT Hang', 'Bug Tracker Prime', 'Search Bug', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'BT Hangs on searching for bug', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'BT Hang'), 'Progressing',
							 ' ', 'Search hangs finding bug report', 'Emergency', 'Critical', 1, 5,
								'Cannot search any reports currently as the program will freeze and need to be alt F4d');
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('BT Lag', 'Bug Tracker Prime', 'Generate Reports', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'BT lags in generate reports', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'BT Lag'), 'Progressing',
							 ' ', 'lag slow bt generate reports', 'Medium', 'Minor', 1, 5,
								'Program slows to a crawl when trying to generate reports');
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('BT Lag 2', 'Bug Tracker Prime', 'Create BugReport', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'Lags creating new Bug Report', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'BT Lag 2'), 'Progressing',
							 ' ', 'lag create report bugs', 'Medium', 'Minor', 1, 5,
								'Program slows to a crawl when trying to create a bug report');
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('BT No Launch', 'Bug Tracker Prime', 'On Launch', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, AssignedTo, LongDescription)
VALUES (current_date(), 'BT Doesnt launch', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'BT No Launch'), 'Progressing',
							 ' ', 'lag create report bugs', 'Emergency', 'Critical', 1, 5,
								'Clicking on the program does nothing');
                
                
                
####JACOB
#1
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Rep0x1', 'Bug Tracker Prime', 'Login Screen', 'v1.0', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'BT Crashes on start up', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Rep0x1'), 'Reported',
							 ' ', 'crash on start up bt ', 'High', 'Major', 3,
								'The bug crashes the program on start up with no errors to be found');
#2                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Rep0x2', 'Adobe PDF Reader', 'Save PDF option', 'v2.5', 'OS X');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'Doesnt save file as pdf', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Rep0x2'), 'Reported',
							 ' ', 'Adobe PDF Save File', 'High', 'Major', 3,
								'Attempts to export as pdf results in a corrupted file.');
#3       
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Rep0x3', 'Google Chrome', 'Tab Creation', 'v8.6', 'Windows 8');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'can not create new tab', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Rep0x3'), 'Reported',
							 ' ', 'google chrome tab crash', 'High', 'Critical', 3,
								'Attempts to open a new tab results in a crash');

#4
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Rep0x4', 'Overwatch', 'Main Menu', 'v2.3', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'UI does not work', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Rep0x4'), 'Reported',
							 ' ', 'Overwatch UI Main Menu Broken', 'High', 'Critical', 3,
								'None of the buttons in the main menu appear to be working after recent patch');

#5
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Rep0x5', 'Discord', 'VoIP', 'v0.8', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'VoIP is lagging', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Rep0x5'), 'Reported',
							 ' ', 'Discord VoIP Lag Lagging', 'Medium', 'Minor', 3,
								'Sometimes VoIP lags, difficult to hear.');
                
###BRAD
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Permissions broken', 'BTP', 'User DB', 'v0.6', 'Windows 10');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'Permissions not updating', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Permissions broken'), 'Solved',
							 'We needed to actually write code, apparently.', 'user permissions', 'Low', 'Cosmetic', 4,
								'Changing user permissions is not updating the display fields when viewing user profile. It just displays System Admin regardless...');

INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Add Patch broken', 'Bug Tracker Prime', 'Patch', 'v0.6', 'Windows 11');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'Visibility issues', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Add Patch not working'), 'Solved',
							 'Turns out that the patch was for eyes and not CSCI222 major project components', 'patch fails', 'High', 'Major', 4,
								'I cant see the screen with a patch on it');
                
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Changing email?', 'Bug Tracker Prime', 'DB', 'v923.1', 'Windows 98');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'I have ruined everything', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Changing email?'), 'Solved',
							 'Submitted patch works great. Issue was in data validation statements. Thanks!', 'password email change', 'Medium', 'Minor', 4,
								'When I try to update my email address on file, I recieve a message saying my password is incorrect. I know it is correct as I use it to sign in!');               
                
        
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Reports crash BTP', 'Bug Tracker Prime', 'Reports', 'v0.2', 'Mac OS X 10.1');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'Program freezing up at report screen', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Reports crash BTP'), 'Solved',
							 'Needed to add an extra function to report generation logic.', 'Reports freeze', 'Emergency', 'Critical', 4,
								'LongDescript');  
                
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Login trouble', 'Bug Tracker Prime', 'Login', 'v0.6', 'Windows Vista');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus,
											 Resolution, Keywords, Priority, BugSeverity, ReporterID, LongDescription)
VALUES (current_date(), 'Getting logged in to the wrong account?', current_date(), (SELECT BugID FROM Bug WHERE BugName = 'Login trouble'), 'Solved',
							 'The patch has sorted it all out, good stuff guys.', 'login account', 'High', 'Major', 4,
								'For some reason, when I try to log in with my usual details, I am being given access to the wrong account? Very strange.');            

                                



INSERT INTO Comments(CreationTimestamp, UserID, CommentText, BugReportID) VALUES (current_date(), 3, 'This is a comment', 2);

INSERT INTO Comments(CreationTimestamp, UserID, CommentText, BugReportID) VALUES (current_date(), 3, 'Another Comment', 3);
