insert into auditorium (name, capacity) values ('A1', 25);
insert into auditorium (name, capacity) values ('B1', 30);
insert into auditorium (name, capacity) values ('C1', 50);

insert into course (name, number_of_weeks, description) values ('Course1', 25, 'boo');
insert into course (name, number_of_weeks, description) values ('Course2', 30, 'hoo');
insert into course (name, number_of_weeks, description) values ('Course3', 35, 'omm');

insert into faculty (faculty_name) values ('Faculty1');
insert into faculty (faculty_name) values ('Faculty2');
insert into faculty (faculty_name) values ('Faculty3');

insert into groups (group_name, faculty_id) values ('Group1', 1);
insert into groups (group_name, faculty_id) values ('Group2', 1);
insert into groups (group_name, faculty_id) values ('Group3', 1);

insert into teacher (first_name, last_name, faculty_id) values ('Jip', 'Loch', 1);
insert into teacher (first_name, last_name, faculty_id) values ('Skip', 'Dub', 1);
insert into teacher (first_name, last_name, faculty_id) values ('Bob', 'Step', 1);

insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '09:00');
insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '10:00');
insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '11:00');

insert into student (student_card_number, firstname, lastname, group_id) values ('aboo', 'May', 'Fair', 1);
insert into student (student_card_number, firstname, lastname, group_id) values ('zaboo', 'June', 'Bay', 1);
insert into student (student_card_number, firstname, lastname, group_id) values ('magoo', 'July', 'Slim', 1);

