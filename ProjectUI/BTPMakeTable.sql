CREATE TABLE SuperUser (
	UserID int NOT NULL AUTO_INCREMENT,
    	SecLevel int NOT NULL,
	UserName varchar(30),
	FName varchar(60),
	LName varchar(60),
	Email varchar(40),
	Password varchar(16),
	JoinedDate date,
	PRIMARY KEY(UserID)
);

CREATE TABLE RegisteredUser (
	UserID int NOT NULL,
	UserReputation ENUM('New', 'OK', 'Good', 'Great', 'AWESOME!', 'Bad'),
	BugReport int,
	AccountStatus ENUM('Active', 'Suspended', 'Deleted'),
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID)
		ON DELETE CASCADE
);

CREATE TABLE SysAdminUser (
	UserID int NOT NULL,
	Role ENUM('Triage', 'Developer', 'Reviewer'),
	AssignedBug int,
	CommentID int,
	AttachmentID int,
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID)
		ON DELETE CASCADE
);

CREATE TABLE Attachments (
	AttachmentID int NOT NULL,
	CreationTimestamp date,
	ShortDescription varchar(200),
	FileName varchar(50),
	FileType varchar(50),
	FileSize int,
	AttacherID int,
	PRIMARY KEY (AttachmentID),
	FOREIGN KEY (AttacherID)
		REFERENCES SysAdminUser(UserID)
);

CREATE TABLE Comments (
	CommentID int,
	UserID int,
	CreationTimestamp date,
	CommentText varchar(500),
	AttachmentID int,
	PRIMARY KEY (CommentID),
	FOREIGN KEY (UserID)
		REFERENCES SuperUser(UserID),
	FOREIGN KEY (AttachmentID)
		REFERENCES Attachments(AttachmentID)
);

CREATE TABLE Bug (
	BugID int NOT NULL AUTO_INCREMENT,
	BugName varchar(50),
	BugReportID int,
	Product varchar(50),
	Component varchar(50),
	Version varchar(10),
	OperatingSystem varchar(50),
	PRIMARY KEY (BugID)
);


CREATE TABLE BugReports (
	BugReportID int NOT NULL AUTO_INCREMENT,
	CreationTimestamp datetime,
	ShortDescription varchar(200),
	DeltaTimestamp datetime,
	BugID int,
	BugStatus ENUM('Reported', 'Progressing', 'Solved'),
	Resolution varchar(500),
	Keywords varchar(50),
	Priority ENUM('Low', 'Medium', 'High', 'Emergency'),
	BugSeverity ENUM ('Critical', ' Major', 'Minor', 'Cosmetic'),
	ReporterID int,
	AssignedTo int,
	CommentID int,
	PRIMARY KEY (BugReportID),
	FOREIGN KEY (BugID)
		REFERENCES Bug(BugID),
	FOREIGN KEY (ReporterID)
		REFERENCES RegisteredUser(UserID),
	FOREIGN KEY (AssignedTo)
		REFERENCES SysAdminUser(UserID),
	FOREIGN KEY (CommentID)
		REFERENCES Comments(CommentID)
);
