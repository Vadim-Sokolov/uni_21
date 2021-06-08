package thyme.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

import thyme.controller.thyme.AuditoriumControllerThyme;
import thyme.model.dto.AuditoriumDTO;
import thyme.service.AuditoriumService;

@WebMvcTest(AuditoriumControllerThyme.class)
class AuditoriumControllerMockMvcTest {

	@MockBean
	private AuditoriumService auditoriumService;

	@Autowired
	private MockMvc mockMvc;

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
		
		AuditoriumDTO expected = new AuditoriumDTO();
		expected.setId(1);
		expected.setName("A1");
		expected.setCapacity(100);
		
		when(auditoriumService.getAuditorium(1)).thenReturn(expected);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums/updateForm?id=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("auditorium/update-auditorium"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("auditorium"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void deleteAuditoriumTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/auditoriums/delete?id=1"))
				.andExpect(MockMvcResultMatchers.status().isFound())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
