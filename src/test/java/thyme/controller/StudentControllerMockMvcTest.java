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

import thyme.controller.thyme.StudentControllerThyme;
import thyme.model.dto.LectureDTO;
import thyme.model.dto.StudentDTO;
import thyme.service.StudentService;

@WebMvcTest(StudentControllerThyme.class)
class StudentControllerMockMvcTest {

	@MockBean
	private StudentService studentService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS student CASCADE;");
		statement.execute("create TABLE student" + "(id serial primary key," + "student_card_number VARCHAR (200),"
				+ "firstname VARCHAR (200), lastname VARCHAR (200), group_id int);");

		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('aboo', 'May', 'Fair', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('zaboo', 'June', 'Bay', 1);");
		statement.execute(
				"insert into student (student_card_number, firstname, lastname, group_id) values ('magoo', 'July', 'Slim', 1);");

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
	
	@Test
	void showUpdateFormTest() throws Exception {
		
		StudentDTO expected = new StudentDTO();
		expected.setId(1);
		
		when(studentService.getStudent(1)).thenReturn(expected);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/updateForm?id=1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("student/update-student"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("student"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	void deleteStudentTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/students/delete?id=1"))
				.andExpect(MockMvcResultMatchers.status().isFound())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
