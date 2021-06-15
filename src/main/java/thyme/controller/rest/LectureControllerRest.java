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
import thyme.model.Lecture;
import thyme.service.LectureService;
import thyme.model.dto.LectureDTO;

@RestController
public class LectureControllerRest {

	private LectureService lectureService;

	public LectureControllerRest(LectureService lectureService) {
		this.lectureService = lectureService;
	}

	@PostMapping("/rest/lectures")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Method adds new lecture to database", notes = "All fields must be filled in", response = Lecture.class)
	public Lecture addLecture(@RequestBody LectureDTO lectureDTO) {
		return lectureService.addLecture(lectureDTO);
	}

	@PutMapping("/rest/lectures/{id}")
	@ApiOperation(value = "Method updates lecture in database", notes = "All fields must be filled in", response = Lecture.class)
	public Lecture updateLecture(@RequestBody LectureDTO lectureDTO, @PathVariable("id") int id) {
		return lectureService.updateLecture(lectureDTO, id);
	}

	@GetMapping("/rest/lectures")
	@ApiOperation(value = "Method returns list of all lectures from database")
	public List<Lecture> getLectures() {
		return lectureService.getLectures();
	}

	@GetMapping("/rest/lectures/{id}")
	@ApiOperation(value = "Method finds and returns lecture by id from database", response = LectureDTO.class)
	public LectureDTO getLecture(@PathVariable("id") int id) {
		return lectureService.getLecture(id);
	}

	@DeleteMapping("/rest/lectures/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Method deletes lecture from database")
	public void deleteLecture(@PathVariable("id") int id) {
		lectureService.deleteLecture(id);
	}
}
