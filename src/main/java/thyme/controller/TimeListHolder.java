package thyme.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TimeListHolder {

	static List<TimeOption> timeList = null;

	static {
		timeList = new ArrayList<>();
		timeList.add(new TimeOption("09:00"));
		timeList.add(new TimeOption("10:00"));
		timeList.add(new TimeOption("11:00"));
		timeList.add(new TimeOption("12:00"));
		timeList.add(new TimeOption("13:00"));
		timeList.add(new TimeOption("14:00"));
		timeList.add(new TimeOption("15:00"));
		timeList.add(new TimeOption("16:00"));
		timeList.add(new TimeOption("17:00"));
	}

	public List<TimeOption> getTimeList() {
		return timeList;
	}
}
