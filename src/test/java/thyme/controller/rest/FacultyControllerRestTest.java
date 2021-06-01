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
import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.FacultyDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FacultyControllerRestTest {

	@Autowired
	private FacultyControllerRest facultyController;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS faculty CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("CREATE TABLE faculty" + "(id serial primary key," + "faculty_name VARCHAR (200));");
		System.out.println("TABLE CREATED");

		statement.execute("insert into faculty (faculty_name) values ('Faculty1');");
		statement.execute("insert into faculty (faculty_name) values ('Faculty2');");
		statement.execute("insert into faculty (faculty_name) values ('Faculty3');");
		System.out.println("FACULTIES INSERTED");

		connection.close();
	}

	@Test
	void addFacultyTest() {

		// Given
		Faculty expected = new Faculty();
		expected.setId(4);
		expected.setName("testFaculty");

		FacultyDTO a = new FacultyDTO();
		a.setName("testFaculty");

		// When
		facultyController.addFaculty(a);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(4));

		System.out.println(actual);

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateFacultyTest() {
		// Given
		Faculty expected = new Faculty();
		expected.setId(1);
		expected.setName("A2");

		FacultyDTO a = new FacultyDTO();
		a.setId(1);
		a.setName("A2");

		// When
		facultyController.updateFaculty(a, 1);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(1));

		System.out.println(actual);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteFacultyTest() {
		// Given
		FacultyDTO beforeDeletion = facultyService.getFaculty(3);

		// When
		facultyController.deleteFaculty(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> facultyService.getFaculty(3));
	}
	@Test
	void getFacultysTest() {
		// Given
		List<Faculty> list = facultyController.getFacultys();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		FacultyDTO expected = new FacultyDTO();
		expected.setId(2);
		expected.setName("Faculty2");

		// When
		FacultyDTO actual = facultyController.getFaculty(2);

		// Then
		assertEquals(expected, actual);
	}
}
