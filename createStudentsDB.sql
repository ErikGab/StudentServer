CREATE TABLE tblStudentCourse
(fldStudentId integer,
fldCourseId integer,
fldStartDate date,
fldStatus varchar(10) DEFAULT "na",
fldGrade varchar(2),
fldComment varchar(120),
CHECK (fldStatus IN ("ongoing", "complete", "aborted", null)),
CHECK (fldGrade IN ("ig", "g", "vg", null)),
PRIMARY KEY (fldStudentId, fldCourseId, fldStartDate));

CREATE TABLE tblCourse
(fldCourseId INTEGER PRIMARY KEY AUTOINCREMENT,
fldName varchar (10),
fldDescription nvarchar(40),
FOREIGN KEY (fldCourseId) REFERENCES tblStudentCourse(fldCourseId));

CREATE TABLE tblStudent
(fldStudentId INTEGER PRIMARY KEY AUTOINCREMENT,
fldName nvarchar(20),
fldSurName nvarchar(40),
fldBirthdate date,
fldPostAddress nvarchar(20),
fldStreetAdress nvarchar(40),
FOREIGN KEY (fldStudentId) REFERENCES tblStudentCourse(fldStudentId));

CREATE TABLE tblStudentPhone
(fldPhoneId INTEGER PRIMARY KEY AUTOINCREMENT,
fldStudentId integer,
fldNumber varchar(15),
fldType varchar(10),
fldPrimary varchar(5),
CHECK (fldPrimary IN ("primary", null)),
CHECK (fldType IN ("mobile", "home", null)),
FOREIGN KEY (fldPhoneId) REFERENCES tblStudent(fldStudentId));

CREATE VIEW vwAllStudents
AS
SELECT  s.fldStudentId,
        s.fldName,
        s.fldSurName,
        ((strftime('%Y', 'now') - strftime('%Y', s.fldBirthdate)) - (strftime('%m-%d', 'now') < strftime('%m-%d', s.fldBirthdate))) AS "age",
        s.fldPostAddress,
        s.fldStreetAdress,
        sp.fldNumber
FROM tblStudent AS s LEFT JOIN tblStudentPhone AS sp
ON s.fldStudentId = sp.fldStudentId
GROUP BY s.fldStudentId
ORDER BY s.fldName;


CREATE VIEW vwAllActiveStudents
AS
SELECT  s.fldStudentId,
        s.fldName,
        s.fldSurName,
        ((strftime('%Y', 'now') - strftime('%Y', s.fldBirthdate)) - (strftime('%m-%d', 'now') < strftime('%m-%d', s.fldBirthdate))) AS "age",
        s.fldPostAddress,
        s.fldStreetAdress,
        sp.fldNumber
FROM tblStudent AS s LEFT JOIN tblStudentPhone AS sp
ON s.fldStudentId = sp.fldStudentId
JOIN tblStudentCourse as sc
ON s.fldStudentId = sc.fldStudentId
WHERE sc.fldStatus = "ongoing"
GROUP BY s.fldStudentId
ORDER BY s.fldName;

CREATE VIEW vwNrOfOngoingCourses
AS
SELECT  s.fldStudentId,
        s.fldName,
        COUNT(sc.fldCourseId)
FROM tblStudent AS s LEFT JOIN tblStudentCourse as sc
ON s.fldStudentId = sc.fldStudentId
WHERE sc.fldStatus = "ongoing"
GROUP BY s.fldStudentId
ORDER BY s.fldName;

CREATE VIEW vwNrOfCompletedCourses
AS
SELECT  s.fldStudentId,
        s.fldName,
        COUNT(sc.fldCourseId)
FROM tblStudent AS s LEFT JOIN tblStudentCourse as sc
ON s.fldStudentId = sc.fldStudentId
WHERE sc.fldStatus = "complete"
GROUP BY s.fldStudentId
ORDER BY s.fldName;


CREATE VIEW vwGradeOverview
AS
SELECT  fldStudentId, COUNT(fldGrade), fldGrade
FROM tblStudentCourse
WHERE fldGrade IS NOT NULL
GROUP BY fldGrade, fldStudentId;


INSERT INTO tblCourse (fldName, fldDescription)
VALUES
("JAVA-101", "Programming with Java"),
("JAVA-102", "Programming with Java"),
("DB-101",   "Introduction to Databases"),
("Bash-101", "Introduction to Bash"),
("C-101",    "Programming with C"),
("C-102",    "Programming with C++"),
("C-103",    "Programming with C#");


INSERT INTO tblStudent (fldName, fldSurName, fldBirthdate, fldPostAddress, fldStreetAdress)
VALUES
("Hampus","Andersson", "1987-03-05","Rävlanda",  "Bullgången 34"),
("Lina",  "Svensson",  "1985-05-07","Styrsö",    "Bryggstigen 4"),
("Malte", "Duggesson", "1981-01-11","Mölnlycke", "Vörtvägen 7"),
("Ture",  "Sventon",   "1979-12-02","Kungsbacka","Semelgången 1"),
("Ida",   "Augustsson","1992-08-08","Surte",     "Kebabvägen 55"),
("Greta", "Andersson", "1989-07-03","Öltorp",    "Mesksmasket 19"),
("Folke", "Falconsson","1957-11-02","Bärslanda", "Sexpacket 28"),
("Gärd",  "Ninjasson", "1997-03-05","Torslanda", "Kustvägen 4"),
("Nisse", "Andersson", "1987-03-05","Rävlanda",  "Bullgången 34"),
("Tony",  "Iommi",     "1948-02-19","Rifftorp",  "Basgången 666"),
("Ian",   "Anderson",  "1947-08-10","Norje",     "Festivalängen 1");


INSERT INTO tblStudentPhone (fldStudentId, fldNumber, fldType, fldPrimary)
VALUES
(1,"0701-1234567","mobile","primary"),
(2,"0708-9876543","mobile","primary"),
(3,"0706-5647389","mobile", null),
(4,"0701-3489654","mobile", "primary"),
(4,"052-819474"  ,"home",   null),
(5,"0706-2315367", null,    null),
(7,"031-9876511", "home",   "primary"),
(7,"0708-1122333","mobile", null),
(8,"0701-7668883","mobile", "primary"),
(9,"0710-1290784","mobile", "primary"),
(11,"0510-212112","home",   null),
(11,"0703-987654","mobile", null);


INSERT INTO tblStudentCourse (fldStudentId, fldCourseId, fldStartDate, fldStatus, fldGrade, fldComment)
VALUES
(1,1,"2016-03-12","complete", "g",  null),
(1,2,"2017-03-12","ongoing",  null, null),
(2,1,"2016-03-12","complete", "vg", null),
(2,2,"2017-03-12","ongoing",  null, null),
(3,1,"2017-03-12","ongoing",  null, null),
(3,3,"2017-08-20",null,       null, null),
(4,1,"2015-03-12","complete", "vg", null),
(4,2,"2015-08-20","complete", "g",  null),
(4,3,"2017-03-12","ongoing",  null, null),
(4,4,"2017-03-11","ongoing",  null, null),
(5,4,"2017-03-11","ongoing",  null, null),
(6,4,"2017-03-11","ongoing",  null, null),
(7,1,"2016-03-12","complete", "vg", null),
(7,4,"2017-03-11","ongoing",  null, null),
(8,5,"2016-03-12","complete", "g",  null),
(8,6,"2017-03-11","ongoing",  null, null),
(9,5,"2015-03-12","complete", "g",  null),
(9,6,"2016-03-11","complete", "ig", null),
(10,5,"2013-03-12","complete", "vg", null),
(10,6,"2014-03-11","complete", "vg", null),
(10,7,"2015-03-11","complete", "vg", null),
(11,5,"2013-03-12","complete", "vg", null),
(11,6,"2014-03-11","complete", "vg", null),
(11,7,"2015-03-11","complete", "vg", null);
