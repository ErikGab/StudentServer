CREATE TABLE tblStudent
(fldStudentId SERIAL,
fldName varchar(20),
fldSurName varchar(40),
fldBirthdate date,
fldPostAddress varchar(20),
fldStreetAddress varchar(40),
PRIMARY KEY (fldStudentId));

CREATE TABLE tblStudentPhone
(fldPhoneId SERIAL,
fldStudentId integer,
fldNumber varchar(15),
fldType varchar(10),
fldPrimary varchar(5),
CHECK (fldPrimary IN ('primary', null)),
CHECK (fldType IN ('mobile', 'home', null)),
PRIMARY KEY (fldPhoneId),
FOREIGN KEY (fldStudentId) REFERENCES tblStudent(fldStudentId));

CREATE TABLE tblSubject
(fldSubjectId SERIAL,
fldName varchar (10),
fldPoints integer,
fldDescription varchar(40),
PRIMARY KEY (fldSubjectId));

CREATE TABLE tblCourse
(fldCourseId SERIAL,
fldStartDate date,
fldEndDate date,
fldSubjectId integer,
CHECK (fldStartDate > '2014-01-01'),
CHECK (fldEndDate > fldStartDate),
PRIMARY KEY (fldCourseId),
FOREIGN KEY (fldSubjectId) REFERENCES tblSubject(fldSubjectId));

CREATE TABLE tblStudentCourse
(fldStudentId integer,
fldCourseId integer,
fldStatus varchar(10) DEFAULT 'na',
fldGrade varchar(4),
fldComment varchar(120),
CHECK (fldStatus IN ('ongoing', 'complete', 'aborted', null)),
CHECK (fldGrade IN ('ig', 'g', 'vg', null)),
PRIMARY KEY (fldStudentId, fldCourseId),
FOREIGN KEY (fldCourseId) REFERENCES tblCourse(fldCourseId),
FOREIGN KEY (fldStudentId) REFERENCES tblStudent(fldStudentId));


CREATE VIEW vwGetStudentsByCourse
AS
SELECT  sc.fldCourseId,
        s.fldStudentId,
        s.fldName,
        s.fldSurName
FROM tblStudentCourse AS sc LEFT JOIN tblStudent AS s
ON s.fldStudentId = sc.fldStudentId;

CREATE VIEW vwGetStudentsByAllCourse
AS
SELECT  s.fldStudentId,
        s.fldName,
        s.fldSurName
FROM tblStudentCourse AS sc LEFT JOIN tblStudent AS s
ON s.fldStudentId = sc.fldStudentId;

CREATE VIEW vwGetStudent
AS
SELECT  s.fldStudentId,
        s.fldName,
        s.fldSurName,
        (EXTRACT(YEAR from AGE(s.fldBirthdate))) AS age,
        s.fldPostAddress,
        s.fldStreetAddress
FROM tblStudent AS s LEFT JOIN tblStudentPhone AS sp
ON s.fldStudentId = sp.fldStudentId
GROUP BY s.fldStudentId
ORDER BY s.fldName;

CREATE VIEW vwGetStudentForCourse
AS
SELECT  sc.fldCourseId,
        s.fldStudentId,
        s.fldName,
        s.fldSurName,
        sc.fldStatus,
        sc.fldGrade
FROM tblStudentCourse AS sc LEFT JOIN tblStudent AS s
ON s.fldStudentId = sc.fldStudentId;

CREATE VIEW vwGetCourse
AS
SELECT  c.fldCourseId,
        c.fldStartDate,
        c.fldEndDate,
        (s.fldName || '_' || EXTRACT(YEAR from c.fldStartDate)) as name,
        s.fldPoints,
        s.fldDescription
FROM tblCourse AS c LEFT JOIN tblSubject AS s
ON c.fldSubjectId = s.fldSubjectId
ORDER BY c.fldCourseId;

CREATE VIEW vwGetCoursesForStudent
AS
SELECT  sc.fldStudentId,
        sc.fldCourseId,
        c.name as name,
        sc.fldStatus,
        sc.fldGrade
FROM tblStudentCourse as sc
LEFT JOIN vwGetCourse as c
ON sc.fldCourseId = c.fldCourseId;

CREATE VIEW vwGetCoursesByYear
AS
SELECT  c.fldCourseId,
        (s.fldName || '_' || EXTRACT(YEAR from c.fldStartDate)) as name,
        EXTRACT(YEAR from c.fldStartDate) as year
FROM tblCourse AS c LEFT JOIN tblSubject AS s
ON c.fldSubjectId = s.fldSubjectId
ORDER BY c.fldCourseId;







INSERT INTO tblSubject (fldName, fldPoints, fldDescription)
VALUES
('JAVA-101', 40, 'Programming with Java'),
('JAVA-102', 40, 'Programming with Java'),
('DB-101',   60, 'Introduction to Databases'),
('Bash-101', 15, 'Introduction to Bash'),
('C-101',    60, 'Programming with C'),
('C-102',    40, 'Programming with C++'),
('C-103',    40, 'Programming with C#');


INSERT INTO tblCourse (fldStartDate, fldEndDate, fldSubjectId)
VALUES
('2014-02-22', '2014-11-14', 5),
('2015-02-21', '2015-10-25', 1),
('2015-02-21', '2015-11-17', 3),
('2015-02-21', '2015-05-02', 4),
('2015-02-21', '2015-11-17', 5),
('2015-02-21', '2015-10-25', 6),
('2016-02-18', '2016-10-18', 1),
('2016-02-18', '2016-10-18', 2),
('2016-02-18', '2016-11-12', 3),
('2016-02-18', '2016-04-27', 4),
('2016-02-18', '2016-11-12', 5),
('2016-02-18', '2016-10-18', 6),
('2016-02-18', '2016-10-18', 7),
('2017-02-23', '2017-10-19', 1),
('2017-02-23', '2017-10-19', 2),
('2017-02-23', '2017-11-11', 3),
('2017-02-23', '2017-05-02', 4),
('2017-02-23', '2017-11-11', 5),
('2017-02-23', '2017-10-19', 6),
('2017-02-23', '2017-10-19', 7);
