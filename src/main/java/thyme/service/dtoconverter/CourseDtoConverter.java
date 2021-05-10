package thyme.service.dtoconverter;

import org.springframework.stereotype.Component;

import thyme.model.Course;
import thyme.model.dto.CourseDTO;

@Component
public class CourseDtoConverter {

	public Course toEntity(CourseDTO courseDTO) {
		Course course = new Course();
		course.setName(courseDTO.getName());
		course.setNumberOfWeeks(courseDTO.getNumberOfWeeks());
		course.setDescription(courseDTO.getDescription());
		return course;
	}

}
