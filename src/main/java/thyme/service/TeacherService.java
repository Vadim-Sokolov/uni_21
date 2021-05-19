package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Teacher;
import thyme.model.dto.TeacherDTO;
import thyme.repository.FacultyRepository;
import thyme.repository.TeacherRepository;
import thyme.service.dtoconverter.TeacherDtoConverter;

@Service
public class TeacherService {

	private TeacherRepository teacherRepository;
	private FacultyRepository facultyRepository;
	private TeacherDtoConverter teacherDtoConverter;

	private static final String INVALID_TEACHER_ID = "Invalid teacher Id";
	private static final String INVALID_FACULTY_ID = "Invalid faculty Id";

	public TeacherService(TeacherRepository teacherRepository, FacultyRepository facultyRepository,
			TeacherDtoConverter teacherDtoConverter) {
		this.teacherRepository = teacherRepository;
		this.facultyRepository = facultyRepository;
		this.teacherDtoConverter = teacherDtoConverter;
	}

	public Teacher addTeacher(TeacherDTO teacherDTO) {
		Teacher teacherToSave = teacherDtoConverter.toEntity(teacherDTO);
		if (facultyRepository.findById(teacherDTO.getFacultyId()).isEmpty()) {
			throw new ServiceException(INVALID_FACULTY_ID);
		} else {
			return teacherRepository.save(teacherToSave);
		}
	}

	public Teacher updateTeacher(TeacherDTO teacherDTO, int teacherId) {
		Teacher teacherToSave = teacherDtoConverter.toEntity(teacherDTO);
		teacherToSave.setId(teacherId);
		if (teacherRepository.findById(teacherId).isEmpty()) {
			throw new ServiceException(INVALID_TEACHER_ID);
		} else if (facultyRepository.findById(teacherDTO.getFacultyId()).isEmpty()) {
			throw new ServiceException(INVALID_FACULTY_ID);
		} else {
			return teacherRepository.save(teacherToSave);
		}
	}

	public List<Teacher> getTeachers() {
		return teacherRepository.findAll();
	}

	public TeacherDTO getTeacher(int id) {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if (optional.isPresent()) {
			return teacherDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_TEACHER_ID);
		}
	}

	public void deleteTeacher(int id) {
		teacherRepository.deleteById(id);
	}

}
