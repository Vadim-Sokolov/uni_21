package thyme.service.dtoconverter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import thyme.model.Group;
import thyme.model.Student;
import thyme.model.dto.StudentDTO;
import thyme.repository.GroupRepository;

@Component
public class StudentDtoConverter {

	private GroupRepository groupRepository;

	public StudentDtoConverter(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public Student toEntity(StudentDTO studentDTO) {
		Student student = new Student();
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setStudentCardNumber(studentDTO.getStudentCardNumber());

		Optional<Group> groupSearchResult = groupRepository.findById(studentDTO.getGroupId());
		if (groupSearchResult.isPresent()) {
			student.setGroup(groupSearchResult.get());
		}
		return student;
	}

}
