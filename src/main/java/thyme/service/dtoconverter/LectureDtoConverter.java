package thyme.service.dtoconverter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import thyme.model.Auditorium;
import thyme.model.Course;
import thyme.model.Group;
import thyme.model.Lecture;
import thyme.model.Teacher;
import thyme.model.dto.LectureDTO;
import thyme.repository.AuditoriumRepository;
import thyme.repository.CourseRepository;
import thyme.repository.GroupRepository;
import thyme.repository.TeacherRepository;

@Component
public class LectureDtoConverter {

	private AuditoriumRepository auditoriumRepository;
	private CourseRepository courseRepository;
	private GroupRepository groupRepository;
	private TeacherRepository teacherRepository;

	public LectureDtoConverter(AuditoriumRepository auditoriumRepository, CourseRepository courseRepository,
			GroupRepository groupRepository, TeacherRepository teacherRepository) {
		this.auditoriumRepository = auditoriumRepository;
		this.courseRepository = courseRepository;
		this.groupRepository = groupRepository;
		this.teacherRepository = teacherRepository;
	}

	public Lecture toEntity(LectureDTO lectureDTO) {
		Lecture lecture = new Lecture();

		Optional<Auditorium> auditoriumSearchResult = auditoriumRepository.findById(lectureDTO.getAuditoriumId());
		if (auditoriumSearchResult.isPresent()) {
			lecture.setAuditorium(auditoriumSearchResult.get());
		}

		Optional<Course> courseSearchResult = courseRepository.findById(lectureDTO.getCourseId());
		if (courseSearchResult.isPresent()) {
			lecture.setCourse(courseSearchResult.get());
		}

		Optional<Group> groupSearchReslut = groupRepository.findById(lectureDTO.getGroupId());
		if (groupSearchReslut.isPresent()) {
			lecture.setGroup(groupSearchReslut.get());
		}

		Optional<Teacher> teacherSearchResult = teacherRepository.findById(lectureDTO.getTeacherId());
		if (teacherSearchResult.isPresent()) {
			lecture.setTeacher(teacherSearchResult.get());
		}
		lecture.setTime(lectureDTO.getTime());
		return lecture;
	}

	public LectureDTO toDTO(Lecture lecture) {
		LectureDTO lectureDTO = new LectureDTO();
		lectureDTO.setId(lecture.getId());
		lectureDTO.setCourseId(lecture.getCourse().getId());
		lectureDTO.setAuditoriumId(lecture.getAuditorium().getId());
		lectureDTO.setTeacherId(lecture.getTeacher().getId());
		lectureDTO.setGroupId(lecture.getGroup().getId());
		lectureDTO.setTime(lecture.getTime());
		return lectureDTO;
	}
}
