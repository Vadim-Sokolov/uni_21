package thyme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thyme.model.Lecture;
import thyme.model.dto.LectureDTO;
import thyme.repository.AuditoriumRepository;
import thyme.repository.CourseRepository;
import thyme.repository.GroupRepository;
import thyme.repository.LectureRepository;
import thyme.repository.TeacherRepository;
import thyme.service.dtoconverter.LectureDtoConverter;

@Service
public class LectureService {

	private LectureRepository lectureRepository;
	private CourseRepository courseRepository;
	private AuditoriumRepository auditoriumRepository;
	private TeacherRepository teacherRepository;
	private GroupRepository groupRepository;
	private LectureDtoConverter lectureDtoConverter;

	private static final String INVALID_LECTURE_ID = "Invalid lecture Id";
	private static final String INVALID_AUDITORIUM_ID = "Invalid auditorium Id";
	private static final String INVALID_TEACHER_ID = "Invalid teacher Id";
	private static final String INVALID_GROUP_ID = "Invalid group Id";
	private static final String INVALID_COURSE_ID = "Invalid course Id";

	public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository,
			AuditoriumRepository auditoriumRepository, TeacherRepository teacherRepository,
			GroupRepository groupRepository, LectureDtoConverter lectureDtoConverter) {
		this.lectureRepository = lectureRepository;
		this.courseRepository = courseRepository;
		this.auditoriumRepository = auditoriumRepository;
		this.teacherRepository = teacherRepository;
		this.groupRepository = groupRepository;
		this.lectureDtoConverter = lectureDtoConverter;
	}

	public Lecture addLecture(LectureDTO lectureDTO) {
		Lecture lectureToSave = lectureDtoConverter.toEntity(lectureDTO);
		if (courseRepository.findById(lectureDTO.getCourseId()).isEmpty()) {
			throw new ServiceException(INVALID_COURSE_ID);
		} else if (auditoriumRepository.findById(lectureDTO.getAuditoriumId()).isEmpty()) {
			throw new ServiceException(INVALID_AUDITORIUM_ID);
		} else if (teacherRepository.findById(lectureDTO.getTeacherId()).isEmpty()) {
			throw new ServiceException(INVALID_TEACHER_ID);
		} else if (groupRepository.findById(lectureDTO.getGroupId()).isEmpty()) {
			throw new ServiceException(INVALID_GROUP_ID);
		} else {
			return lectureRepository.save(lectureToSave);
		}
	}

	public Lecture updateLecture(LectureDTO lectureDTO, int id) {
		Lecture lectureToSave = lectureDtoConverter.toEntity(lectureDTO);
		lectureToSave.setId(id);
		if (courseRepository.findById(lectureDTO.getCourseId()).isEmpty()) {
			throw new ServiceException(INVALID_COURSE_ID);
		} else if (lectureRepository.findById(id).isEmpty()) {
			throw new ServiceException(INVALID_LECTURE_ID);
		} else if (auditoriumRepository.findById(lectureDTO.getAuditoriumId()).isEmpty()) {
			throw new ServiceException(INVALID_AUDITORIUM_ID);
		} else if (teacherRepository.findById(lectureDTO.getTeacherId()).isEmpty()) {
			throw new ServiceException(INVALID_TEACHER_ID);
		} else if (groupRepository.findById(lectureDTO.getGroupId()).isEmpty()) {
			throw new ServiceException(INVALID_GROUP_ID);
		} else {
			return lectureRepository.save(lectureToSave);
		}
	}

	public List<Lecture> getLectures() {
		return lectureRepository.findAll();
	}

	public LectureDTO getLecture(int id) {
		Optional<Lecture> optional = lectureRepository.findById(id);
		if (optional.isPresent()) {
			return lectureDtoConverter.toDTO(optional.get());
		} else {
			throw new ServiceException(INVALID_LECTURE_ID);
		}
	}

	public void deleteLecture(int id) {
		lectureRepository.deleteById(id);
	}

}
