package thyme.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.thyme.StudentControllerThyme;
import thyme.model.Student;
import thyme.model.dto.StudentDTO;
import thyme.service.GroupService;
import thyme.service.ServiceException;
import thyme.service.StudentService;
import thyme.service.dtoconverter.GroupDtoConverter;
import thyme.service.dtoconverter.StudentDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentControllerTest {

	@Autowired
	private StudentControllerThyme studentControllerThyme;

	@Autowired
	private StudentService studentService;

	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	@Autowired
	private StudentDtoConverter studentDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS student CASCADE;");
		statement.execute("create TABLE student" + "(id serial primary key," + "student_card_number VARCHAR (200),"
				+ "firstname VARCHAR (200), lastname VARCHAR (200), group_id int);");

		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('aboo', 'May', 'Fair', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('zaboo', 'June', 'Bay', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('magoo', 'July', 'Slim', 1);");

		connection.close();
	}

	@Test
	void addStudentTest() {

		// Given
		Student expected = new Student();
		expected.setId(4);
		expected.setFirstName("testFirstName");
		expected.setLastName("testLastName");
		expected.setStudentCardNumber("testCardNumber");
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));

		StudentDTO a = new StudentDTO();
		a.setFirstName("testFirstName");
		a.setLastName("testLastName");
		a.setStudentCardNumber("testCardNumber");
		a.setGroupId(1);

		// When
		studentControllerThyme.saveStudent(a);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(4));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateStudentTest() {
		// Given
		Student expected = new Student();
		expected.setId(1);
		expected.setFirstName("Loop");
		expected.setLastName("Hole");
		expected.setStudentCardNumber("Carrr");
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));

		StudentDTO a = new StudentDTO();
		a.setId(1);
		a.setFirstName("Loop");
		a.setLastName("Hole");
		a.setStudentCardNumber("Carrr");
		a.setGroupId(1);

		// When
		studentControllerThyme.saveStudent(a);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteStudentTest() {
		// Given
		StudentDTO beforeDeletion = studentService.getStudent(3);

		// When
		studentControllerThyme.deleteStudent(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> studentService.getStudent(3));
	}
}
