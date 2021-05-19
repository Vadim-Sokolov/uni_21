package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Student;
import thyme.model.dto.StudentDTO;
import thyme.repository.GroupRepository;
import thyme.repository.StudentRepository;
import thyme.service.dtoconverter.StudentDtoConverter;

@Service
public class StudentService {

	private StudentRepository studentRepository;
	private GroupRepository groupRepository;
	private StudentDtoConverter studentDtoConverter;

	private static final String INVALID_STUDENT_ID = "Invalid student Id";
	private static final String INVALID_GROUP_ID = "Invalid group Id";

	public StudentService(StudentRepository studentRepository, GroupRepository groupRepository,
			StudentDtoConverter studentDtoConverter) {
		this.studentRepository = studentRepository;
		this.groupRepository = groupRepository;
		this.studentDtoConverter = studentDtoConverter;
	}

	public Student addStudent(StudentDTO studentDTO) {
		Student studentToSave = studentDtoConverter.toEntity(studentDTO);
		if (groupRepository.findById(studentDTO.getGroupId()).isEmpty()) {
			throw new ServiceException(INVALID_GROUP_ID);
		} else {
			return studentRepository.save(studentToSave);
		}
	}

	public Student updateStudent(StudentDTO studentDTO, int studentId) {
		Student studentToSave = studentDtoConverter.toEntity(studentDTO);
		studentToSave.setId(studentId);
		if (studentRepository.findById(studentId).isEmpty()) {
			throw new ServiceException(INVALID_STUDENT_ID);
		} else if (groupRepository.findById(studentDTO.getGroupId()).isEmpty()) {
			throw new ServiceException(INVALID_GROUP_ID);
		} else {
			return studentRepository.save(studentToSave);
		}
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public StudentDTO getStudent(int id) {
		Optional<Student> optional = studentRepository.findById(id);
		if (optional.isPresent()) {
			return studentDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_STUDENT_ID);
		}
	}

	public void deleteStudent(int id) {
		studentRepository.deleteById(id);
	}

}
