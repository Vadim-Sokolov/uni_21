package thyme.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import thyme.service.AuditoriumService;

@WebMvcTest(AuditoriumController.class)
public class AuditoriumMockTest {

	@MockBean
	private AuditoriumService service;

	@Autowired
	private MockMvc mockMvc;

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
	void getAuditoriumsTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("auditorium/auditoriums"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("auditoriumList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("auditorium/new-auditorium"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("auditoriumDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void showUpdateFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums/updateForm").param("id", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("auditorium/update-auditorium"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("auditorium"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
