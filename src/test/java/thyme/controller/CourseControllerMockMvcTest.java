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

import thyme.controller.thyme.CourseControllerThyme;
import thyme.service.CourseService;

@WebMvcTest(CourseControllerThyme.class)
class CourseControllerMockMvcTest {

	@MockBean
	private CourseService courseService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS course CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("create TABLE course" + "(id serial primary key," + "name VARCHAR (200),"
				+ "number_of_weeks int, description text);");
		System.out.println("TABLE CREATED");

		statement.execute("insert into course (name, number_of_weeks, description) values ('Course1', 25, 'boo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course2', 30, 'hoo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course3', 35, 'omm');");
		System.out.println("COURSES INSERTED");

		connection.close();
	}
	
	@Test
	void getCoursesTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("course/courses"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("courseList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/courses/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("course/new-course"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("courseDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
