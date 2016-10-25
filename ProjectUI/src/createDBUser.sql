USE BugTrackerPrime;
DROP USER 'newUser'@'localhost';
CREATE USER 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT INSERT, SELECT ON BugTrackerPrime.SuperUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT INSERT, SELECT ON BugTrackerPrime.RegisteredUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
GRANT EXECUTE ON PROCEDURE BugTrackerPrime.insertNewUser TO 'newUser'@'localhost' IDENTIFIED BY 'pass123';
FLUSH PRIVILEGES;