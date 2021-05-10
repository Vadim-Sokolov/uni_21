package thyme.service.dtoconverter;

import org.springframework.stereotype.Component;

import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;

@Component
public class FacultyDtoConverter {

	public Faculty toEntity(FacultyDTO facultyDTO) {
		Faculty faculty = new Faculty();
		faculty.setName(facultyDTO.getName());
		return faculty;
	}
}
