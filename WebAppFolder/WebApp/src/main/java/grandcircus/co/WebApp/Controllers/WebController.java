package grandcircus.co.WebApp.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.ArrayUtils;

import grandcircus.co.WebApp.Models.DayEvent;
import grandcircus.co.WebApp.Models.Event;
import grandcircus.co.WebApp.Services.DaysOfTheYearService;
import grandcircus.co.WebApp.Services.EventService;

@Controller
public class WebController {

	@Autowired
	private EventService eventService;

	@Autowired
	private DaysOfTheYearService dayService;

//	//Home page after we complete MVP
//	@RequestMapping("/login")
//	public String displayLogin(Model model) {
//
//		return "login";
//	}
//	
//	//Logout page after we complete MVP
//	@RequestMapping("/logout")
//	public String logout() {
//		return "redirect:/login";
//	}

	@RequestMapping("/")
	public String homeRedirect() {
		return "redirect:/monthly-calendar";
	}

	// Month will be default page
	@RequestMapping("/monthly-calendar")
	public String displayMonth(@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer day, Model model) {
		// If a date is not provided as is the case when "/" is visited, it will show
		// current month on calendar
		if (year == null || month == null) {
			LocalDate currentDate = LocalDate.now();
			month = currentDate.getMonthValue();
			year = currentDate.getYear();
			day = currentDate.getDayOfMonth();
		}

		// find the number of days in the currently views month
		int numDaysInMonth = numDaysInMonth(month, year);

		// determines the day num of the current day, so that we can determine how many
		// days to backpedal in order to point at sunday
		int dayOffset = calculateDayOfWeek(1, month, year);

		// Array stores the values of each gridpoint on the calendar...
		// either a 0 if the gridpoint is not occupied, otherwise the day
		// number
		int arraySize = ((numDaysInMonth + dayOffset) > 35) ? 42 : 35;
		List<Integer> dayNums = new ArrayList<Integer>(arraySize);
		List<Event[]> dailyEvents = new ArrayList<Event[]>(arraySize);

		// Set the curDay pointer as the first day of this week
		LocalDateTime curDay = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime curDayEndTime = LocalDateTime.of(year, month, 1, 23, 59);
		
		//Move the first day to be the first one on the grid
		while(dayOffset-- > 0) {
			curDay = curDay.minusDays(1);
			curDayEndTime = curDayEndTime.minusDays(1);
		}

		// Used for printing the correct day numbers of this week on the jsp
		for (int i = 0; i < arraySize; i++) {
			dailyEvents.add(eventService.getEventsByTimeRange(curDay.toString(), curDayEndTime.toString()));
			dayNums.add(curDay.getDayOfMonth());
			curDay = curDay.plusDays(1);
			curDayEndTime = curDayEndTime.plusDays(1);
		}

		// monthStr String is for easier output on the jsp
		String monthStr = monthNumToString(month);

		// Variables for switching between different months on the view
		int prevYear = year;
		int prevMonth = month - 1;
		int nextYear = year;
		int nextMonth = month + 1;

		if (prevMonth < 1) {
			prevMonth = 12;
			prevYear--;
		} else if (nextMonth > 12) {
			nextMonth = 1;
			nextYear++;
		}

		if(day != null)	model.addAttribute("dayEvents", dailyEvents.get(day));

		// Info used and displayed on monthly calendar
		model.addAttribute("dailyEvents", dailyEvents);
		model.addAttribute("prevYear", prevYear);
		model.addAttribute("nextYear", nextYear);
		model.addAttribute("prevMonth", prevMonth);
		model.addAttribute("nextMonth", nextMonth);
		model.addAttribute("dayNums", dayNums);
		model.addAttribute("year", year);
		model.addAttribute("monthStr", monthStr);
		model.addAttribute("monthNum", month);

		return "month";
	}

	@RequestMapping("/weekly-calendar")
	public String displayWeek(Model model, @RequestParam(required = false) Integer month,
			@RequestParam(required = false) Integer day, @RequestParam(required = false) Integer year) {
		
		// If page is entered with no params (clicking on weekly view instead of either
		// of the arrows)
		if (year == null || month == null || day == null) {
			LocalDateTime todaysDate = LocalDateTime.now();
			year = todaysDate.getYear();
			month = todaysDate.getMonthValue();
			day = todaysDate.getDayOfMonth();
		}
		
		//Stores the numbers to be printed for the current week
		int[] dayNums = new int[7];
		List<Event[]> dayEvents = new ArrayList<Event[]>(7);
		
		//determines the day num of the current day, so that we can determine how many days to backpedal in order to point at sunday
		int dayOffset = calculateDayOfWeek(day, month, year);
		
		//Set the curDay pointer as the first day of this week
		LocalDateTime curDay = LocalDateTime.of(year, month, day - dayOffset, 0, 0);
		LocalDateTime curDayEndTime = LocalDateTime.of(year, month, day - dayOffset, 23, 59);

		// Used for printing the correct day numbers of this week on the jsp 
		for (int i = 0; i < 7; i++) {
			dayEvents.add(eventService.getEventsByTimeRange(curDay.toString(), curDayEndTime.toString()));
			dayNums[i] = curDay.getDayOfMonth();
			curDay = curDay.plusDays(1);
			curDayEndTime = curDayEndTime.plusDays(1);
		}

		
		//Set curDay to the first day of this week
		curDay = LocalDateTime.of(year, month, day - dayOffset, 0, 0);
		model.addAttribute("curWeekDay", curDay.getDayOfMonth());
		model.addAttribute("curWeekMonthString", monthNumToString(curDay.getMonthValue()));
		model.addAttribute("curWeekMonth", curDay.getMonthValue());
		model.addAttribute("curWeekYear", curDay.getYear());
		//Set curDay to next weeks date
		curDay = curDay.plusDays(7);
		model.addAttribute("nextWeekDay", curDay.getDayOfMonth());
		model.addAttribute("nextWeekMonth", curDay.getMonthValue());
		model.addAttribute("nextWeekYear", curDay.getYear());
		//Set curDay to last weeks date
		curDay = curDay.minusDays(14);
		model.addAttribute("prevWeekDay", curDay.getDayOfMonth());
		model.addAttribute("prevWeekMonth", curDay.getMonthValue());
		model.addAttribute("prevWeekYear", curDay.getYear());
		//Day numbers to be printed
		model.addAttribute("dayNums", dayNums);
		model.addAttribute("dayEvents", dayEvents);
		
		return "week";
	}

	@RequestMapping("/delete/{id}")
	public String deleteEvent(@PathVariable("id") String id) {
		eventService.deleteEvent(id);
		return "redirect:/";
	}

	@PostMapping("/submitEvent")
	public String submitEvent(@RequestParam String eventName, @RequestParam String start, @RequestParam String end,
			@RequestParam String addEmployees) {
		List<String> employees = new ArrayList<>(Arrays.asList(addEmployees.split(",")));
		LocalDateTime startTime = LocalDateTime.parse(start);
		LocalDateTime endTime = LocalDateTime.parse(end);

		Long d = ChronoUnit.MINUTES.between(startTime, endTime);
		double duration = ((d.doubleValue()) / 60);

		Event event = new Event(start, end, eventName, employees, duration);
		eventService.addNewEvent(event);

		return "redirect:/";
	}

	@RequestMapping("/find-time")
	public String displayFindTime(Model model) {

		return "find-time";
	}

	/**
	 * Helper method for printing dynamic calendar. Determines the day of the week
	 * that the date is one
	 * 
	 * @param day   [1-31]
	 * @param month [1-12]
	 * @param year  (4 digit format eg. 2022)
	 * @return the number of the day the given date is on (0 = sunday, 1 = monday,
	 *         etc....)
	 */
	private static int calculateDayOfWeek(int day, int month, int year) {
		// Source:
		// https://artofmemory.com/blog/how-to-calculate-the-day-of-the-week/

		int[] monthCodes = { 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };
		int yearCode, monthCode, centuryCode = 6, dateNum, leapYearCode;
		yearCode = ((year % 100) + ((year % 100) / 4)) % 7;
		monthCode = monthCodes[month - 1];
		switch (year / 100) {
		case 17:
			centuryCode = 4;
			break;
		case 18:
			centuryCode = 2;
			break;
		case 19:
			centuryCode = 0;
			break;
		case 20:
			centuryCode = 6;
			break;
		case 21:
			centuryCode = 4;
			break;
		case 22:
			centuryCode = 2;
			break;
		case 23:
			centuryCode = 0;
			break;
		}
		dateNum = day;
		if (((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) && (month == 0 || month == 1))
			leapYearCode = -1;
		else
			leapYearCode = 0;

		return (yearCode + monthCode + centuryCode + dateNum + leapYearCode) % 7;
	}

	/**
	 * Return the number of days in the given month
	 * 
	 * @param monthNum the number of the month [1-12]
	 * @param year     the year in gregorian calendar (eg. 2022)
	 * @return the number of days in the given month
	 */
	private static int numDaysInMonth(int monthNum, int year) {
		int leapYearCode = 0;
		int[] numDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		// Checks if its a leap year
		if (((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) && (monthNum == 2))
			leapYearCode = 1;

		return numDays[monthNum - 1] + leapYearCode;
	}

	private static String monthNumToString(int monthNum) {
		switch (monthNum) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return "Invalid month";
		}
	}

	@RequestMapping("/weekly-day-event")
	public String displayDayApiEvent(Model model, @RequestParam(required=false) Integer month,
										@RequestParam(required=false) Integer day,
										@RequestParam(required=false) Integer year) {
		
		// If page is entered with no params (clicking on weekly view instead of either
				// of the arrows)
				if (year == null || month == null || day == null) {
					LocalDateTime todaysDate = LocalDateTime.now();
					year = todaysDate.getYear();
					month = todaysDate.getMonthValue();
					day = todaysDate.getDayOfMonth();
				}
				
				//Stores the numbers to be printed for the current week
				int[] dayNums = new int[7];
				List<Event[]> dayEvents = new ArrayList<Event[]>(7);
				
				//determines the day num of the current day, so that we can determine how many days to backpedal in order to point at sunday
				int dayOffset = calculateDayOfWeek(day, month, year);
				
				//Set the curDay pointer as the first day of this week
				LocalDateTime curDay = LocalDateTime.of(year, month, day - dayOffset, 0, 0);
				LocalDateTime curDayEndTime = LocalDateTime.of(year, month, day - dayOffset, 23, 59);

				// Used for printing the correct day numbers of this week on the jsp 
				for (int i = 0; i < 7; i++) {
					dayEvents.add(eventService.getEventsByTimeRange(curDay.toString(), curDayEndTime.toString()));
					dayNums[i] = curDay.getDayOfMonth();
					curDay = curDay.plusDays(1);
					curDayEndTime = curDayEndTime.plusDays(1);
				}
			
				String dayMonthString = monthToString(month);
				String dayDayString = dayToString(day);
				String dayEventName = "";
				String dayEventUrl = "";
				DayEvent[] dayEvent = dayService.getSpecificDateEvents(year.toString(), dayMonthString, dayDayString);
				for (DayEvent d : dayEvent) {
					dayEventName = d.getName();
					dayEventUrl = d.getUrl();
				}
				System.out.println(dayEventName);
				System.out.println(dayEventUrl);
				
				model.addAttribute("dayEventName", dayEventName);
				model.addAttribute("dayEventUrl", dayEventUrl);

				
				//Set curDay to the first day of this week
				curDay = LocalDateTime.of(year, month, day - dayOffset, 0, 0);
				model.addAttribute("curWeekDay", curDay.getDayOfMonth());
				model.addAttribute("curWeekMonthString", monthNumToString(curDay.getMonthValue()));
				model.addAttribute("curWeekMonth", curDay.getMonthValue());
				model.addAttribute("curWeekYear", curDay.getYear());
				//Set curDay to next weeks date
				curDay = curDay.plusDays(7);
				model.addAttribute("nextWeekDay", curDay.getDayOfMonth());
				model.addAttribute("nextWeekMonth", curDay.getMonthValue());
				model.addAttribute("nextWeekYear", curDay.getYear());
				//Set curDay to last weeks date
				curDay = curDay.minusDays(14);
				model.addAttribute("prevWeekDay", curDay.getDayOfMonth());
				model.addAttribute("prevWeekMonth", curDay.getMonthValue());
				model.addAttribute("prevWeekYear", curDay.getYear());
				//Day numbers to be printed
				model.addAttribute("dayNums", dayNums);
				model.addAttribute("dayEvents", dayEvents);
		
		return "week";
	}
	
	public String monthToString(Integer month) {
		if (month.toString().length() == 1) {
			String newMonth = "0" + month ;
			return newMonth;
		}
		return month.toString();
	}
	public String dayToString(Integer day) {
		if (day.toString().length() == 1) {
			String newDay = "0" + day;
			return newDay;
		}
		return day.toString();
	}

}
