package thyme.controller.rest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.DbConnector;
import thyme.model.Group;
import thyme.model.dto.GroupDTO;
import thyme.service.FacultyService;
import thyme.service.GroupService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.FacultyDtoConverter;
import thyme.service.dtoconverter.GroupDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupControllerRestTest {

	@Autowired
	private GroupControllerRest groupController;

	@Autowired
	private GroupService groupService;

	@Autowired
	private FacultyService facultyService;
	@Autowired
	private FacultyDtoConverter facultyDtoConverter;
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	
	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS groups CASCADE;");
		statement.execute(
				"create TABLE groups" + "(id serial primary key," + "group_name VARCHAR (200)," + "faculty_id int);");

		statement.execute("insert into groups (group_name, faculty_id) values ('Group1', 1);");
		statement.execute("insert into groups (group_name, faculty_id) values ('Group2', 1);");
		statement.execute("insert into groups (group_name, faculty_id) values ('Group3', 1);");

		connection.close();
	}

	@Test
	void addGroupTest() {

		// Given
		Group expected = new Group();
		expected.setId(4);
		expected.setName("testGroup");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		GroupDTO a = new GroupDTO();
		a.setName("testGroup");
		a.setFacultyId(1);

		// When
		groupController.addGroup(a);

		Group actual = groupDtoConverter.toEntity(groupService.getGroup(4));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateGroupTest() {
		// Given
		Group expected = new Group();
		expected.setId(1);
		expected.setName("A2");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		GroupDTO a = new GroupDTO();
		a.setId(1);
		a.setName("A2");
		a.setFacultyId(1);

		// When
		groupController.updateGroup(a, 1);

		Group actual = groupDtoConverter.toEntity(groupService.getGroup(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteGroupTest() {
		// Given
		GroupDTO beforeDeletion = groupService.getGroup(3);

		// When
		groupController.deleteGroup(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> groupService.getGroup(3));
	}

	@Test
	void getGroupsTest() {
		// Given
		List<Group> list = groupController.getGroups();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		GroupDTO expected = new GroupDTO();
		expected.setId(2);
		expected.setName("Group2");
		expected.setFacultyId(1);

		// When
		GroupDTO actual = groupController.getGroup(2);

		// Then
		assertEquals(expected, actual);
	}

}
