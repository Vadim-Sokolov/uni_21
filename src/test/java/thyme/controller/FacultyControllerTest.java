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

import thyme.controller.thyme.FacultyControllerThyme;
import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.FacultyDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FacultyControllerTest {

	@Autowired
	private FacultyControllerThyme facultyControllerThyme;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS faculty CASCADE;");
		statement.execute("CREATE TABLE faculty" + "(id serial primary key," + "faculty_name VARCHAR (200));");

		statement.execute("insert into faculty (faculty_name) values ('Faculty1');");
		statement.execute("insert into faculty (faculty_name) values ('Faculty2');");
		statement.execute("insert into faculty (faculty_name) values ('Faculty3');");

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
		facultyControllerThyme.saveFaculty(a);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(4));

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
		facultyControllerThyme.saveFaculty(a);

		Faculty actual = facultyDtoConverter.toEntity(facultyService.getFaculty(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteFacultyTest() {
		// Given
		FacultyDTO beforeDeletion = facultyService.getFaculty(3);

		// When
		facultyControllerThyme.deleteFaculty(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> facultyService.getFaculty(3));
	}

}
