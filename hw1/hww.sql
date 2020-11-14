
/* Q1) Find which building is most used for courses?*/

SELECT building
FROM section
GROUP BY building
ORDER BY COUNT(building) DESC
LIMIT 1;

/* Q2) Find which building is second most used for courses */

SELECT building
FROM section
GROUP BY building
ORDER BY COUNT(building) DESC
LIMIT 1,1;

/* Q3) Show which instructor is advising how many students in each department */

WITH tmp(ID,dept_name,n) as 
(
    SELECT i_ID as ID,dept_name as dept_name,count(dept_name) as N
    FROM advisor as v INNER JOIN student as w
    on v.s_ID = w.ID
    GROUP BY i_ID,dept_name
)
SELECT ID,name,T.dept_name,IFNULL(N,0) as numberofStudent
FROM instructor LEFT JOIN tmp as T USING(ID);

/* Q4) Find the names of students who took the courses that were offered in Painter building in 2009. */

SELECT name FROM
(
    SELECT ID
    FROM takes INNER JOIN section as S USING(course_id,year)
    WHERE year=2009 AND building = 'Painter'
) as tmp INNER JOIN student USING(ID);

/* Q5) Find the names of instructors who taught the prerequisite course of the courses that Williams took in 2009 */

WITH T(course_id) as
(
    SELECT prereq_id as course_id FROM
    (
        SELECT course_id
        FROM student INNER JOIN takes USING(ID)
        WHERE name= "williams" and year=2009
    ) as tmp INNER JOIN prereq USING(course_id)
)
SELECT name
FROM instructor INNER JOIN
(SELECT ID FROM teaches INNER JOIN T USING(course_ID)) as S USING(ID);

/* Q6) compute the average GPA of students in 'comp. sci' department.show the student ID,name,and GPA. */

DELIMITER $$

create function GradeToDeci(g varchar(2))
    returns Float(10,2) 
    begin
        declare It Float(10,2); 
        if g like 'A%' then
            SET It = 4.0;
        elseif g like "B%" then
            set It = 3.0;
        elseif g like "C%" then
            set It = 2.0;
        elseif g like "D%" then
            set It = 1.0;
        else
            set It = 0.0;
        end if;

        if g like '%+' then
            set It = It + 0.3;
        elseif g like '%-' then
            set It = It - 0.3;
        end if;

        return It;
    end $$  

create function GPA_calc(idt varchar(5))
    returns Float(10,2)
    begin
        declare GPA Float(10,2);
        select sum(GradeToDeci(S.grade))/count(S.grade) into GPA
        from
        (select grade from takes where ID=idt) as S;

        return GPA;
    end $$

DELIMITER ;

select ID,name,IFNULL(GPA_calc(ID),0) as GPA
from student
where dept_name = "Comp. Sci.";

drop function GradeToDeci;
drop function GPA_calc;

/* Q7) Create a trigger that rejects a course registration if a student tries to register for a course but its classroom is full */

DELIMITER $$

CREATE FUNCTION isfull(course varchar(8),y decimal(4,0),s varchar(6),se varchar(8))
    returns INT(5)
    BEGIN
        declare ret INT(5);
        SELECT capacity - curnum INTO ret
        FROM classroom
        INNER JOIN
        (
            SELECT room_number,count(room_number) as curnum
            FROM takes INNER JOIN section using(course_id,year,semester,sec_id)
            WHERE course_id = course and year=y and semester = s and sec_id = se
            GROUP BY room_number
        ) as S using(room_number);
       
        return ret;
    end $$    

CREATE TRIGGER fullcap BEFORE INSERT ON takes
for each row
    begin
        if isfull(new.course_id,new.year,new.semester,new.sec_id) = 0 then
            SIGNAL SQLSTATE '02000'
            SET MESSAGE_TEXT = 'Warning : There are no remaining capacity for this course';

        END IF;
    END $$

delimiter ;

/* Q8) Create a trigger that adds a new advising relationship into 'advisor' table when a new student is added to 'student' table matching to student department's most paid instructor */





/*
# DROP ALL FUN and TRIGGER

DROP FUNCTION GradeToDeci;
DROP FUNCTION GPA_calc;
DROP FUNCTION isfull;
DROP TRIGGER fullcap;

*/
















