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
import thyme.model.Faculty;
import thyme.service.FacultyService;
import thyme.model.dto.FacultyDTO;

@RestController
public class FacultyControllerRest {

	private FacultyService facultyService;

	public FacultyControllerRest(FacultyService facultyService) {
		this.facultyService = facultyService;
	}

	@PostMapping("/rest/faculties")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adds new faculty to database",
	notes = "All fields must be filled in", response = Faculty.class)
	public Faculty addFaculty(@RequestBody FacultyDTO facultyDTO) {
		return facultyService.addFaculty(facultyDTO);
	}

	@PutMapping("/rest/faculties/{id}")
	@ApiOperation(value = "Updates faculty in database",
	notes = "All fields must be filled in", response = Faculty.class)
	public Faculty updateFaculty(@RequestBody FacultyDTO facultyDTO, @PathVariable("id") int id) {
		return facultyService.updateFaculty(facultyDTO, id);
	}

	@GetMapping("/rest/faculties")
	@ApiOperation(value = "Get list of all facultys")
	public List<Faculty> getFacultys() {
		return facultyService.getFacultys();
	}

	@GetMapping("/rest/faculties/{id}")
	@ApiOperation(value = "Find faculty by id", response = FacultyDTO.class)
	public FacultyDTO getFaculty(@PathVariable("id") int id) {
		return facultyService.getFaculty(id);
	}

	@DeleteMapping("/rest/faculties/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete faculty")
	public void deleteFaculty(@PathVariable("id") int id) {
		facultyService.deleteFaculty(id);
	}
}
