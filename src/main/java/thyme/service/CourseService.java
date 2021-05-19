package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Course;
import thyme.model.dto.CourseDTO;
import thyme.repository.CourseRepository;
import thyme.service.dtoconverter.CourseDtoConverter;

@Service
public class CourseService {

	private CourseRepository courseRepository;
	private CourseDtoConverter courseDtoConverter;
	private static final String INVALID_COURSE_ID = "Invalid course Id";

	public CourseService(CourseRepository courseRepository, CourseDtoConverter courseDtoConverter) {
		this.courseRepository = courseRepository;
		this.courseDtoConverter = courseDtoConverter;
	}

	public Course addCourse(CourseDTO courseDTO) {
		Course courseToSave = courseDtoConverter.toEntity(courseDTO);
		return courseRepository.save(courseToSave);
	}

	public Course updateCourse(CourseDTO courseDTO, int id) {
		Course courseToSave = courseDtoConverter.toEntity(courseDTO);
		courseToSave.setId(id);
		if (courseRepository.findById(id).isEmpty()) {
			throw new ServiceException(INVALID_COURSE_ID);
		} else {
			return courseRepository.save(courseToSave);
		}
	}

	public List<Course> getCourses() {
		return courseRepository.findAll();
	}

	public CourseDTO getCourse(int id) {
		Optional<Course> optional = courseRepository.findById(id);
		if (optional.isPresent()) {
			return courseDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_COURSE_ID);
		}
	}

	public void deleteCourse(int id) {
		courseRepository.deleteById(id);
	}

}
