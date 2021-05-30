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
	public Lecture addLecture(@RequestBody LectureDTO lectureDTO) {
		return lectureService.addLecture(lectureDTO);
	}

	@PutMapping("/rest/lectures/{id}")
	public Lecture updateLecture(@RequestBody LectureDTO lectureDTO, @PathVariable("id") int id) {
		return lectureService.updateLecture(lectureDTO, id);
	}

	@GetMapping("/rest/lectures")
	public List<Lecture> getLectures() {
		return lectureService.getLectures();
	}

	@GetMapping("/rest/lectures/{id}")
	public LectureDTO getLecture(@PathVariable("id") int id) {
		return lectureService.getLecture(id);
	}

	@DeleteMapping("/rest/lectures/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLecture(@PathVariable("id") int id) {
		lectureService.deleteLecture(id);
	}
}
