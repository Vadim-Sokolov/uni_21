package thyme.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.DbConnector;
import thyme.model.Auditorium;
import thyme.model.dto.AuditoriumDTO;
import thyme.service.AuditoriumService;
import thyme.service.ServiceException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AuditoriumControllerRestTest {

	@Autowired
	private AuditoriumControllerRest controller;
	@Autowired
	private AuditoriumService service;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS auditorium CASCADE;");
		statement.execute(
				"create TABLE auditorium" + "(id serial primary key," + "name VARCHAR (200)," + "capacity int);");

		statement.execute("insert into auditorium (name, capacity) values ('A1', 25);");
		statement.execute("insert into auditorium (name, capacity) values ('B1', 30);");
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
		controller.addAuditorium(a);

		AuditoriumDTO actual = service.getAuditorium(4);

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
		controller.updateAuditorium(forUpdate, 1);

		AuditoriumDTO actual = service.getAuditorium(1);

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

	@Test
	void getAuditoriumsTest() {
		// Given
		List<Auditorium> list = controller.getAuditoriums();
		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(2);
		expected.setName("B1");
		expected.setCapacity(30);

		// When
		AuditoriumDTO actual = controller.getAuditorium(2);

		// Then
		assertEquals(expected, actual);
	}
}
