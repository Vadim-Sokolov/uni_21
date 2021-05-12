package thyme.controller;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class TimeOption {

	@DateTimeFormat(pattern = "HH:mm")
	public LocalTime localTime;

	public TimeOption(LocalTime localTime) {
		super();
		this.localTime = localTime;
	}

	public LocalTime getLocaltime() {
		return localTime;
	}

	public void setLocaltime(LocalTime localTime) {
		this.localTime = localTime;
	}
}
