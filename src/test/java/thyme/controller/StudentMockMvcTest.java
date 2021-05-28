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

import thyme.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentMockMvcTest {

	@MockBean
	private StudentService studentService;

	@Autowired
	private MockMvc mockMvc;

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
	void getStudentsTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("student/students"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("studentList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("student/new-student"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("studentDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
