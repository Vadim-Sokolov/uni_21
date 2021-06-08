package thyme.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.DbConnector;
import thyme.model.Teacher;
import thyme.model.dto.TeacherDTO;
import thyme.service.FacultyService;
import thyme.service.ServiceException;
import thyme.service.TeacherService;
import thyme.service.dtoconverter.FacultyDtoConverter;
import thyme.service.dtoconverter.TeacherDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TeacherControllerRestTest {

	@Autowired
	private TeacherControllerRest teacherController;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;
	@Autowired
	private TeacherDtoConverter teacherDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS teacher CASCADE;");
		statement.execute("create TABLE teacher" + "(id serial primary key," + "first_name VARCHAR (200),"
				+ "last_name VARCHAR (200), faculty_id int);");

		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Jip', 'Loch', 1);");
		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Skip', 'Dub', 1);");
		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Bob', 'Step', 1);");

		connection.close();
	}

	@Test
	void addTeacherTest() {

		// Given
		Teacher expected = new Teacher();
		expected.setId(4);
		expected.setFirstName("testFirstName");
		expected.setLastName("testLastName");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		TeacherDTO a = new TeacherDTO();
		a.setFirstName("testFirstName");
		a.setLastName("testLastName");
		a.setFacultyId(1);

		// When
		teacherController.addTeacher(a);

		Teacher actual = teacherDtoConverter.toEntity(teacherService.getTeacher(4));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateTeacherTest() {
		// Given
		Teacher expected = new Teacher();
		expected.setId(1);
		expected.setFirstName("Top");
		expected.setLastName("Job");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		TeacherDTO a = new TeacherDTO();
		a.setId(1);
		a.setFirstName("Top");
		a.setLastName("Job");
		a.setFacultyId(1);

		// When
		teacherController.updateTeacher(a, 1);

		Teacher actual = teacherDtoConverter.toEntity(teacherService.getTeacher(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteTeacherTest() {
		// Given
		TeacherDTO beforeDeletion = teacherService.getTeacher(3);

		// When
		teacherController.deleteTeacher(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> teacherService.getTeacher(3));
	}

	@Test
	void getTeachersTest() {
		// Given
		List<Teacher> list = teacherController.getTeachers();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		TeacherDTO expected = new TeacherDTO();
		expected.setId(2);
		expected.setFirstName("Skip");
		expected.setLastName("Dub");
		expected.setFacultyId(1);

		// When
		TeacherDTO actual = teacherController.getTeacher(2);

		// Then
		assertEquals(expected, actual);
	}
}
