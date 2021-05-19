package thyme.service.dtoconverter;

import org.springframework.stereotype.Component;

import thyme.model.Auditorium;
import thyme.model.dto.AuditoriumDTO;

@Component
public class AuditoriumDtoConverter {

	public Auditorium toEntity(AuditoriumDTO auditoriumDTO) {
		Auditorium auditorium = new Auditorium();
		auditorium.setName(auditoriumDTO.getName());
		auditorium.setCapacity(auditoriumDTO.getCapacity());
		return auditorium;
	}

	public AuditoriumDTO toDTO(Auditorium auditorium) {
		AuditoriumDTO auditoriumDTO = new AuditoriumDTO();
		auditoriumDTO.setId(auditorium.getId());
		auditoriumDTO.setCapacity(auditorium.getCapacity());
		return auditoriumDTO;
	}
}
