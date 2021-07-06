package thyme.controller.system;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import thyme.controller.rest.LectureControllerRest;
import thyme.model.Lecture;
import thyme.model.dto.LectureDTO;
import thyme.service.AuditoriumService;
import thyme.service.CourseService;
import thyme.service.GroupService;
import thyme.service.LectureService;
import thyme.service.ServiceException;
import thyme.service.TeacherService;
import thyme.service.dtoconverter.AuditoriumDtoConverter;
import thyme.service.dtoconverter.CourseDtoConverter;
import thyme.service.dtoconverter.GroupDtoConverter;
import thyme.service.dtoconverter.LectureDtoConverter;
import thyme.service.dtoconverter.TeacherDtoConverter;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LectureSystemTest {

	@Autowired
	private LectureControllerRest lectureController;

	@Autowired
	private LectureService lectureService;
	@Autowired
	private AuditoriumService auditoriumService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private LectureDtoConverter lectureDtoConverter;
	@Autowired
	private AuditoriumDtoConverter auditoriumDtoConverter;
	@Autowired
	private CourseDtoConverter courseDtoConverter;
	@Autowired
	private GroupDtoConverter groupDtoConverter;
	@Autowired
	private TeacherDtoConverter teacherDtoConverter;

	@Test
	void addLectureTest() {

		// Given
		Lecture expected = new Lecture();
		expected.setId(7);
		expected.setAuditorium(auditoriumDtoConverter.toEntity(auditoriumService.getAuditorium(1)));
		expected.setCourse(courseDtoConverter.toEntity(courseService.getCourse(1)));
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));
		expected.setTeacher(teacherDtoConverter.toEntity(teacherService.getTeacher(1)));
		expected.setTime(LocalTime.of(12, 00));

		LectureDTO lectureToAdd = new LectureDTO();
		lectureToAdd.setAuditoriumId(1);
		lectureToAdd.setCourseId(1);
		lectureToAdd.setGroupId(1);
		lectureToAdd.setTeacherId(1);
		lectureToAdd.setTime(LocalTime.of(12, 00));

		// When
		lectureController.addLecture(lectureToAdd);

		Lecture actual = lectureDtoConverter.toEntity(lectureService.getLecture(7));

		// Then
		assertEquals(expected, actual);

	}

	@Test
	void updateLectureTest() {
		// Given
		Lecture expected = new Lecture();
		expected.setId(1);
		expected.setAuditorium(auditoriumDtoConverter.toEntity(auditoriumService.getAuditorium(1)));
		expected.setCourse(courseDtoConverter.toEntity(courseService.getCourse(1)));
		expected.setGroup(groupDtoConverter.toEntity(groupService.getGroup(1)));
		expected.setTeacher(teacherDtoConverter.toEntity(teacherService.getTeacher(1)));
		expected.setTime(LocalTime.of(14, 00));

		LectureDTO lectureToUpdate = new LectureDTO();
		lectureToUpdate.setId(1);
		lectureToUpdate.setAuditoriumId(1);
		lectureToUpdate.setCourseId(1);
		lectureToUpdate.setGroupId(1);
		lectureToUpdate.setTeacherId(1);
		lectureToUpdate.setTime(LocalTime.of(14, 00));

		// When
		lectureController.updateLecture(lectureToUpdate, 1);

		Lecture actual = lectureDtoConverter.toEntity(lectureService.getLecture(1));

		// Then
		assertEquals(expected, actual);
	}

	@Test
	void deleteLectureTest() {
		// Given
		LectureDTO beforeDeletion = lectureService.getLecture(3);

		// When
		lectureController.deleteLecture(3);

		// Then
		assertNotNull(beforeDeletion);
		assertThrows(ServiceException.class, () -> lectureService.getLecture(3));
	}

	@Test
	void getLecturesTest() {
		// Given
		List<Lecture> list = lectureController.getLectures();

		// When

		// Then
		assertTrue(list.size() >= 2);
	}

	@Test
	void getOneTest() {
		// Given
		LectureDTO expected = new LectureDTO();
		expected.setId(2);
		expected.setAuditoriumId(1);
		expected.setCourseId(1);
		expected.setGroupId(1);
		expected.setTeacherId(1);
		expected.setTime(LocalTime.of(10, 00));

		// When
		LectureDTO actual = lectureController.getLecture(2);

		// Then
		assertEquals(expected, actual);
	}
}
