package thyme.controller;

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

import thyme.controller.thyme.FacultyControllerThyme;
import thyme.model.dto.CourseDTO;
import thyme.model.dto.FacultyDTO;
import thyme.service.FacultyService;

@WebMvcTest(FacultyControllerThyme.class)
class FacultyControllerMockMvcTest {

	@MockBean
	private FacultyService facultyService;

	@Autowired
	private MockMvc mockMvc;

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
	void getFacultysTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facultys"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("faculty/facultys"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("facultyList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facultys/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("faculty/new-faculty"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("facultyDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void showUpdateFormTest() throws Exception {
		
		FacultyDTO expected = new FacultyDTO();
		expected.setId(1);
		expected.setName("A1");
		
		when(facultyService.getFaculty(1)).thenReturn(expected);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facultys/updateForm?id=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("faculty/update-faculty"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("faculty"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void deleteFacultyTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/facultys/delete?id=1"))
				.andExpect(MockMvcResultMatchers.status().isFound())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
