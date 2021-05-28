package thyme.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.model.dto.AuditoriumDTO;

import thyme.service.AuditoriumService;
import thyme.service.ServiceException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AuditoriumControllerTest {

	@Autowired
	private AuditoriumController controller;
	@Autowired 
	private AuditoriumService service;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		System.out.println("Statement created");
		statement.execute("DROP TABLE IF EXISTS auditorium CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute(
				"create TABLE auditorium" + "(id serial primary key," + "name VARCHAR (200)," + "capacity int);");
		System.out.println("TABLE CREATED");

		statement.execute("insert into auditorium (name, capacity) values ('A1', 25);");
		System.out.println("A1 inserted");
		statement.execute("insert into auditorium (name, capacity) values ('B1', 30);");
		System.out.println("B1 inserted");
		statement.execute("insert into auditorium (name, capacity) values ('C1', 50);");

		connection.close();
	}

	@Test
	void addAuditoriumTest() {

		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(4);
		expected.setName("testAuditorium");
		expected.setCapacity(100);

		AuditoriumDTO a = new AuditoriumDTO();
		a.setName("testAuditorium");
		a.setCapacity(100);

		// When
		controller.saveAuditorium(a);

		AuditoriumDTO actual = service.getAuditorium(4);

		System.out.println(actual.toString());

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void updateAuditoriumTest() {
		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(1);
		expected.setName("A2");
		expected.setCapacity(80);

		AuditoriumDTO forUpdate = new AuditoriumDTO();
		forUpdate.setId(1);
		forUpdate.setName("A2");
		forUpdate.setCapacity(80);

		// When
		controller.saveAuditorium(forUpdate);

		AuditoriumDTO actual = service.getAuditorium(1);

		System.out.println(actual);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteAuditoriumTest() {
		// Given
		AuditoriumDTO beforeDeletion = service.getAuditorium(3);

		// When
		controller.deleteAuditorium(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> service.getAuditorium(3));
	}
}
