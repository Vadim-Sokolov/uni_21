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
class StudentControllerRestTest {

	@Autowired
	private StudentControllerRest studentController;

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
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS student CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("create TABLE student" + "(id serial primary key," + "student_card_number VARCHAR (200),"
				+ "firstname VARCHAR (200), lastname VARCHAR (200), group_id int);");
		System.out.println("TABLE CREATED");

		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('aboo', 'May', 'Fair', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('zaboo', 'June', 'Bay', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('magoo', 'July', 'Slim', 1);");
		System.out.println("STUDENTS INSERTED");

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
		studentController.addStudent(a);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(4));

		System.out.println(actual);

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
		studentController.updateStudent(a, 1);

		Student actual = studentDtoConverter.toEntity(studentService.getStudent(1));

		System.out.println(actual);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteStudentTest() {
		// Given
		StudentDTO beforeDeletion = studentService.getStudent(3);

		// When
		studentController.deleteStudent(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> studentService.getStudent(3));
	}
	@Test
	void getStudentsTest() {
		// Given
		List<Student> list = studentController.getStudents();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		StudentDTO expected = new StudentDTO();
		expected.setId(2);
		expected.setFirstName("June");
		expected.setLastName("Bay");
		expected.setStudentCardNumber("zaboo");
		expected.setGroupId(1);

		// When
		StudentDTO actual = studentController.getStudent(2);

		// Then
		assertEquals(expected, actual);
	}
}
