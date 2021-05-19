package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Faculty;
import thyme.model.dto.FacultyDTO;
import thyme.repository.FacultyRepository;
import thyme.service.dtoconverter.FacultyDtoConverter;

@Service
public class FacultyService {

	private FacultyRepository facultyRepository;
	private FacultyDtoConverter facultyDtoConverter;
	private static final String INVALID_FACULTY_ID = "Invalid faculty Id";

	public FacultyService(FacultyRepository facultyRepository, FacultyDtoConverter facultyDtoConverter) {
		this.facultyRepository = facultyRepository;
		this.facultyDtoConverter = facultyDtoConverter;
	}

	public Faculty addFaculty(FacultyDTO facultyDTO) {
		Faculty facultyToSave = facultyDtoConverter.toEntity(facultyDTO);
		return facultyRepository.save(facultyToSave);
	}

	public Faculty updateFaculty(FacultyDTO facultyDTO, int id) {
		Faculty facultyToSave = facultyDtoConverter.toEntity(facultyDTO);
		facultyToSave.setId(id);
		if (facultyRepository.findById(id).isEmpty()) {
			throw new ServiceException(INVALID_FACULTY_ID);
		} else {
			return facultyRepository.save(facultyToSave);
		}
	}

	public List<Faculty> getFacultys() {
		return facultyRepository.findAll();
	}

	public FacultyDTO getFaculty(int id) {
		Optional<Faculty> optional = facultyRepository.findById(id);
		if (optional.isPresent()) {
			return facultyDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_FACULTY_ID);
		}
	}

	public void deleteFaculty(int id) {
		facultyRepository.deleteById(id);
	}

}
