select *
from student;

select *
from student
where age > 21
  and age < 26;

select name
from student;

select *
from student
where name like '%o%';

select *
from student
where age < id;

select *
from student
order by age;

select *
from student
order by age desc;
