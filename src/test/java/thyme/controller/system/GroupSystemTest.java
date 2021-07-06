package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.GroupControllerRest;
import thyme.model.Group;
import thyme.model.dto.GroupDTO;
import thyme.service.FacultyService;
import thyme.service.GroupService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.FacultyDtoConverter;
import thyme.service.dtoconverter.GroupDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupSystemTest {

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
	
	@Test
	void addGroupTest() {

		// Given
		Group expected = new Group();
		expected.setId(7);
		expected.setName("testGroup");
		expected.setFaculty(facultyDtoConverter.toEntity(facultyService.getFaculty(1)));

		GroupDTO groupToAdd = new GroupDTO();
		groupToAdd.setName("testGroup");
		groupToAdd.setFacultyId(1);

		// When
		groupController.addGroup(groupToAdd);

		Group actual = groupDtoConverter.toEntity(groupService.getGroup(7));

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

		GroupDTO groupToUpdate = new GroupDTO();
		groupToUpdate.setId(1);
		groupToUpdate.setName("A2");
		groupToUpdate.setFacultyId(1);

		// When
		groupController.updateGroup(groupToUpdate, 1);

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
