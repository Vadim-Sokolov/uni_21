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

import thyme.controller.thyme.TeacherController;
import thyme.service.TeacherService;

@WebMvcTest(TeacherController.class)
class TeacherMockMvcTest {

	@MockBean
	private TeacherService teacherService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS teacher CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("create TABLE teacher" + "(id serial primary key," + "first_name VARCHAR (200),"
				+ "last_name VARCHAR (200), faculty_id int);");
		System.out.println("TABLE CREATED");

		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Jip', 'Loch', 1);");
		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Skip', 'Dub', 1);");
		statement.execute("insert into teacher (first_name, last_name, faculty_id) values ('Bob', 'Step', 1);");
		System.out.println("TEACHERS INSERTED");

		connection.close();
	}

	@Test
	void getTeachersTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("teacher/teachers"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("teacherList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/teachers/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("teacher/new-teacher"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("teacherDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
