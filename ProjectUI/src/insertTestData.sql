USE BugTrackerPrime;

#INSERTS A REGISTERED USER
INSERT INTO SuperUser(SecLevel, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (0, 'auser','Alex', 'User', 'auser@btp.com', 'pass123', CURDATE());

INSERT INTO RegisteredUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'auser'), 'New',0,'Active');

#INSERTS A SYSADMIN USER
INSERT INTO SuperUser(SecLevel, UserName, FName, LName, Email, Password, JoinedDate)
VALUES (0, 'buser','Bob', 'User', 'buser@btp.com', 'pass123', CURDATE());

INSERT INTO SysAdminUser VALUES ((SELECT UserID FROM SuperUser WHERE UserName = 'buser'), 'Reviewer');

#INSERTS A BUG AND A BUG REPORT
INSERT INTO Bug(BugName, Product, Component, Version, OperatingSystem)
VALUES ('Bug 1', 'Bug Tracker Prime', 'Database', 'v0.4', 'Windows');

INSERT INTO BugReports(CreationTimestamp, ShortDescription, DeltaTimestamp, BugID, BugStatus, Resolution, Keywords, Priority, BugSeverity)
VALUES (CURDATE(), 'this is a short description', NOW(), (SELECT BugID FROM Bug WHERE BugName = 'Bug 1'), 'Reported', 'This is a resolution', 'Some Keywords here', 'Medium', 'Minor');