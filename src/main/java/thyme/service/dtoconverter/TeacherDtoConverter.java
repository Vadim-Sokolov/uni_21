package thyme.service.dtoconverter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import thyme.model.Faculty;
import thyme.model.Teacher;
import thyme.model.dto.TeacherDTO;
import thyme.repository.FacultyRepository;

@Component
public class TeacherDtoConverter {

	private FacultyRepository facultyRepository;

	public TeacherDtoConverter(FacultyRepository facultyRepository) {
		this.facultyRepository = facultyRepository;
	}

	public Teacher toEntity(TeacherDTO teacherDTO) {
		Teacher teacher = new Teacher();
		if (teacherDTO.getId() != null) {
			teacher.setId(teacherDTO.getId());
		}
		teacher.setFirstName(teacherDTO.getFirstName());
		teacher.setLastName(teacherDTO.getLastName());

		Optional<Faculty> facultySearchResult = facultyRepository.findById(teacherDTO.getFacultyId());
		if (facultySearchResult.isPresent()) {
			teacher.setFaculty(facultySearchResult.get());
		}
		return teacher;
	}

	public TeacherDTO toDTO(Teacher teacher) {
		TeacherDTO teacherDTO = new TeacherDTO();
		teacherDTO.setId(teacher.getId());
		teacherDTO.setFirstName(teacher.getFirstName());
		teacherDTO.setLastName(teacher.getLastName());
		teacherDTO.setFacultyId(teacher.getFaculty().getId());
		return teacherDTO;
	}
}
