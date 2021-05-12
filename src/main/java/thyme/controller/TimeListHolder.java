package thyme.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TimeListHolder {

	static List<TimeOption> timeList = null;

	static {
		timeList = new ArrayList<>();
		timeList.add(new TimeOption(LocalTime.of(9, 0)));
		timeList.add(new TimeOption(LocalTime.of(10, 0)));
		timeList.add(new TimeOption(LocalTime.of(11, 0)));
		timeList.add(new TimeOption(LocalTime.of(12, 0)));
		timeList.add(new TimeOption(LocalTime.of(13, 0)));
		timeList.add(new TimeOption(LocalTime.of(14, 0)));
		timeList.add(new TimeOption(LocalTime.of(15, 0)));
		timeList.add(new TimeOption(LocalTime.of(16, 0)));
		timeList.add(new TimeOption(LocalTime.of(17, 0)));
	}

	public List<TimeOption> getTimeList() {
		return timeList;
	}
}
