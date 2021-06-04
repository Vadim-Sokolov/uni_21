package thyme.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.thyme.CourseControllerThyme;
import thyme.model.Course;
import thyme.model.dto.CourseDTO;
import thyme.service.CourseService;
import thyme.service.ServiceException;
import thyme.service.dtoconverter.CourseDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseControllerTest {

	@Autowired
	private CourseControllerThyme courseControllerThyme;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CourseDtoConverter courseDtoConverter;

	@BeforeAll
	public static void init() throws ClassNotFoundException, SQLException {

		DbConnector dbc = new DbConnector();
		Connection connection = dbc.getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DROP TABLE IF EXISTS course CASCADE;");
		statement.execute("create TABLE course" + "(id serial primary key," + "name VARCHAR (200),"
				+ "number_of_weeks int, description text);");

		statement.execute("insert into course (name, number_of_weeks, description) values ('Course1', 25, 'boo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course2', 30, 'hoo');");
		statement.execute("insert into course (name, number_of_weeks, description) values ('Course3', 35, 'omm');");

		connection.close();
	}

	@Test
	void addCourseTest() {

		// Given
		Course expected = new Course();
		expected.setId(4);
		expected.setName("testCourse");
		expected.setNumberOfWeeks(100);
		expected.setDescription("weeee");

		CourseDTO a = new CourseDTO();
		a.setName("testCourse");
		a.setNumberOfWeeks(100);
		a.setDescription("weeee");

		// When
		courseControllerThyme.saveCourse(a);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(4));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateCourseTest() {
		// Given
		Course expected = new Course();
		expected.setId(1);
		expected.setName("A2");
		expected.setNumberOfWeeks(80);
		expected.setDescription("weeee");

		CourseDTO a = new CourseDTO();
		a.setId(1);
		a.setName("A2");
		a.setNumberOfWeeks(80);
		a.setDescription("weeee");

		// When
		courseControllerThyme.saveCourse(a);

		Course actual = courseDtoConverter.toEntity(courseService.getCourse(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteCourseTest() {
		// Given
		CourseDTO beforeDeletion = courseService.getCourse(3);

		// When
		courseControllerThyme.deleteCourse(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> courseService.getCourse(3));
	}
}
