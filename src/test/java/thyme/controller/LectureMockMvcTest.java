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

import thyme.service.LectureService;

@WebMvcTest(LectureController.class)
class LectureMockMvcTest {

	@MockBean
	private LectureService lectureService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS lecture CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute("create TABLE lecture" + "(id serial primary key," + "course_id int, auditorium_id int,"
				+ "teacher_id int, group_id int, time time);");
		System.out.println("TABLE CREATED");

		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '09:00');");
		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '10:00');");
		statement.execute(
				"insert into lecture (course_id, auditorium_id, teacher_id, group_id, time) values (1, 1, 1, 1, '11:00');");
		System.out.println("LECTURES INSERTED");

		connection.close();
	}

	@Test
	void getLecturesTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/lectures")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("lecture/lectures"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("lectureList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/lectures/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("lecture/new-lecture"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("lectureDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
