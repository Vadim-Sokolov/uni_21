package thyme.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import thyme.model.Auditorium;
import thyme.service.AuditoriumService;
import thyme.model.dto.AuditoriumDTO;

@RestController
public class AuditoriumControllerRest {

	private AuditoriumService auditoriumService;

	public AuditoriumControllerRest(AuditoriumService auditoriumService) {
		this.auditoriumService = auditoriumService;
	}

	@PostMapping("/rest/auditoriums")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adds new auditorium to database",
	notes = "All fields must be filled in", response = Auditorium.class)
	public Auditorium addAuditorium(@RequestBody AuditoriumDTO auditoriumDTO) {
		return auditoriumService.addAuditorium(auditoriumDTO);
	}

	@PutMapping("/rest/auditoriums/{id}")
	@ApiOperation(value = "Updates auditorium in database",
	notes = "All fields must be filled in", response = Auditorium.class)
	public Auditorium updateAuditorium(@RequestBody AuditoriumDTO auditoriumDTO, @PathVariable("id") int id) {
		return auditoriumService.updateAuditorium(auditoriumDTO, id);
	}

	@GetMapping("/rest/auditoriums")
	@ApiOperation(value = "Get list of all auditoriums")
	public List<Auditorium> getAuditoriums() {
		return auditoriumService.getAuditoriums();
	}

	@GetMapping("/rest/auditoriums/{id}")
	@ApiOperation(value = "Find auditorium by id", response = AuditoriumDTO.class)
	public AuditoriumDTO getAuditorium(@PathVariable("id") int id) {
		return auditoriumService.getAuditorium(id);
	}

	@DeleteMapping("/rest/auditoriums/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete auditorium")
	public void deleteAuditorium(
			@ApiParam(value = "Id value for auditorium to be deleted", required = true) 
			@PathVariable("id") int id) {
		auditoriumService.deleteAuditorium(id);
	}
}
