package thyme.model.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class LectureDTO {

	private Integer id;
	private Integer courseId;
	private Integer auditoriumId;
	private Integer teacherId;
	private Integer groupId;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getAuditoriumId() {
		return auditoriumId;
	}

	public void setAuditoriumId(Integer auditoriumId) {
		this.auditoriumId = auditoriumId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
}
