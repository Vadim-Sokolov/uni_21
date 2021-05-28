package thyme.service.dtoconverter;

import org.springframework.stereotype.Component;

import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;

@Component
public class FacultyDtoConverter {

	public Faculty toEntity(FacultyDTO facultyDTO) {
		Faculty faculty = new Faculty();
		if (facultyDTO.getId() != null) {
			faculty.setId(facultyDTO.getId());
		}
		faculty.setName(facultyDTO.getName());
		return faculty;
	}

	public FacultyDTO toDTO(Faculty faculty) {
		FacultyDTO facultyDTO = new FacultyDTO();
		facultyDTO.setId(faculty.getId());
		facultyDTO.setName(faculty.getName());
		return facultyDTO;
	}
}
