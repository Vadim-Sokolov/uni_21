package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Auditorium;
import thyme.model.dto.AuditoriumDTO;
import thyme.repository.AuditoriumRepository;
import thyme.service.dtoconverter.AuditoriumDtoConverter;

@Service
public class AuditoriumService {

	private AuditoriumRepository auditoriumRepository;
	private AuditoriumDtoConverter auditoriumDtoConverter;
	private static final String INVALID_AUDITORIUM_ID = "Invalid auditorium Id";

	public AuditoriumService(AuditoriumRepository auditoriumRepository, AuditoriumDtoConverter auditoriumDtoConverter) {
		this.auditoriumRepository = auditoriumRepository;
		this.auditoriumDtoConverter = auditoriumDtoConverter;
	}

	public Auditorium addAuditorium(AuditoriumDTO auditoriumDTO) {
		Auditorium auditoriumToSave = auditoriumDtoConverter.toEntity(auditoriumDTO);
		return auditoriumRepository.save(auditoriumToSave);
	}

	public Auditorium updateAuditorium(AuditoriumDTO auditoriumDTO, int id) {
		Auditorium auditoriumToSave = auditoriumDtoConverter.toEntity(auditoriumDTO);
		auditoriumToSave.setId(id);
		if (auditoriumRepository.findById(id).isEmpty()) {
			throw new ServiceException(INVALID_AUDITORIUM_ID);
		} else {
			return auditoriumRepository.save(auditoriumToSave);
		}
	}

	public List<Auditorium> getAuditoriums() {
		return auditoriumRepository.findAll();
	}

	public Optional<Auditorium> getAuditorium(int id) {
		return auditoriumRepository.findById(id);
	}

	public void deleteAuditorium(int id) {
		auditoriumRepository.deleteById(id);
	}

}
