package thyme.service.dtoconverter;

import org.springframework.stereotype.Component;

import thyme.model.Course;
import thyme.model.dto.CourseDTO;

@Component
public class CourseDtoConverter {

	public Course toEntity(CourseDTO courseDTO) {
		Course course = new Course();
		course.setId(courseDTO.getId());
		course.setName(courseDTO.getName());
		course.setNumberOfWeeks(courseDTO.getNumberOfWeeks());
		course.setDescription(courseDTO.getDescription());
		return course;
	}

	public CourseDTO toDTO(Course course) {
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setId(course.getId());
		courseDTO.setName(course.getName());
		courseDTO.setNumberOfWeeks(course.getNumberOfWeeks());
		courseDTO.setDescription(course.getDescription());
		return courseDTO;
	}
}
