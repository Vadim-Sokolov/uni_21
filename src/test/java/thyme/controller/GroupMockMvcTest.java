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

import thyme.controller.thyme.GroupController;
import thyme.service.GroupService;

@WebMvcTest(GroupController.class)
class GroupMockMvcTest {

	@MockBean
	private GroupService groupService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		System.out.println("Connection obtained");
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS groups CASCADE;");
		System.out.println("TABLE DROPPED");
		statement.execute(
				"create TABLE groups" + "(id serial primary key," + "group_name VARCHAR (200)," + "faculty_id int);");
		System.out.println("TABLE CREATED");

		statement.execute("insert into groups (group_name, faculty_id) values ('Group1', 1);");
		statement.execute("insert into groups (group_name, faculty_id) values ('Group2', 1);");
		statement.execute("insert into groups (group_name, faculty_id) values ('Group3', 1);");
		System.out.println("GROUPS INSERTED");

		connection.close();
	}

	@Test
	void getGroupsTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/groups")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("group/groups"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("groupList"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void showNewFormTest() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/groups/newForm"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("group/new-group"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("groupDTO"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
