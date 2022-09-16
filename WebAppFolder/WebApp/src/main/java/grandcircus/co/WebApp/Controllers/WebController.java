package grandcircus.co.WebApp.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String displayMonth(@RequestParam(required = false) String date,
			@RequestParam(required = false) String dayDate, Model model) {
		// If a date is not provided as is the case when "/" is visited, it will show
		// current month on calendar
		LocalDate today;
		if (date == null) {
			today = LocalDate.now();
		} else {
			today = LocalDate.parse(date);
		}
		date = today.toString();

		LocalDate sidebarDate;
		if (dayDate == null) {
			sidebarDate = LocalDate.now();
		} else {
			sidebarDate = LocalDate.parse(dayDate);
		}
		// Used for generating data for DaysOfYearApi call on jsp
		if (dayDate == null) {
			dayDate = LocalDate.now().toString();
		}
		String dayMonthString = dayDate.substring(5, 7);
		String dayDayString = dayDate.substring(8, 10);
			String dayEventName = "";
			String dayEventUrl = "";
			DayEvent[] dayEvents = dayService.getSpecificDateEvents("2022", dayMonthString, dayDayString);
					for (DayEvent d : dayEvents) {
						dayEventName = d.getName();
						dayEventUrl = d.getUrl();
					}
			model.addAttribute("dayEventName", dayEventName);
			model.addAttribute("dayEventUrl", dayEventUrl);

		// find the number of days in the currently viewed month
		int numDaysInMonth = numDaysInMonth(today.getMonthValue(), today.getYear());

		// determines the day num of the current day, so that we can determine how many
		// days to backpedal in order to point at sunday
		int dayOffset = calculateDayOfWeek(1, today.getMonthValue(), today.getYear());

		// Array stores the values of each gridpoint on the calendar...
		// either a 0 if the gridpoint is not occupied, otherwise the day
		// number
		int arraySize = ((numDaysInMonth + dayOffset) > 35) ? 42 : 35;
		ArrayList<LocalDate> dates = new ArrayList<LocalDate>(arraySize);
		HashMap<String, ArrayList<Event>> events;

		// Move the first day to be the first one on the grid
		today = today.minusDays(today.getDayOfMonth() + dayOffset - 1);

		// Used for printing the correct day numbers of this week on the jsp
		for (int i = 0; i < arraySize; i++) {
			dates.add(today);
			today = today.plusDays(1);
		}
		
		events = eventService.getEventsByTimeRange(dates.get(0).toString(), dates.get(dates.size() - 1).toString());
//		for(String s : events.keySet()) {
//			ArrayList<Event> cur = events.get(s);
//			System.out.print(s = ": ");
//			for(Event e : cur) {
//				System.out.print(e.getEventName() + ", ");
//			}
//			System.out.println();
//		}

		// Set today to the first day of this month
		// today = today.minusDays(numDaysInMonth);
		today = LocalDate.parse(date);
		model.addAttribute("curMonthDate", today);
		model.addAttribute("curMonthString", monthNumToString(today.getMonthValue()));
		// Set today to last month's date
		today = today.minusMonths(1);
		model.addAttribute("prevMonthDate", today.toString());
		// Set today to next month's date
		today = today.plusMonths(2);
		model.addAttribute("nextMonthDate", today.toString());

		// Day numbers to be printed
		model.addAttribute("dates", dates);
		model.addAttribute("events", events);
		// Set today to curDay for daily info section
		today = LocalDate.parse(date);
		model.addAttribute("curDayDate", sidebarDate);
		model.addAttribute("curDayMonthString", monthNumToString(sidebarDate.getMonthValue()));

		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).toString().equals(dayDate)) {
				model.addAttribute("dayEvents", events.get(dayDate));
			}
		}

		return "month";	
	}

	@RequestMapping("/weekly-calendar")
	public String displayWeek(Model model, @RequestParam(required = false) String date) {

		// If page is entered with no params (clicking on weekly view instead of either
		// of the arrows)
		LocalDate today;
		if (date == null) {
			today = LocalDate.now();
		} else {
			today = LocalDate.parse(date);
		}
		date = today.toString();
		
		// Used for generating data for DaysOfYearApi call on jsp
		String dayMonthString = monthToString(today.getMonthValue());
		String dayDayString = dayToString(today.getDayOfMonth());
		String dayEventName = "";
		String dayEventUrl = "";
		DayEvent[] dayEvents = dayService.getSpecificDateEvents("2022", dayMonthString, dayDayString);
				for (DayEvent d : dayEvents) {
					dayEventName = d.getName();
					dayEventUrl = d.getUrl();
				}
		model.addAttribute("dayEventName", dayEventName);
		model.addAttribute("dayEventUrl", dayEventUrl);
		
		// Stores the numbers to be printed for the current week
		List<LocalDate> dates = new ArrayList<LocalDate>(7);
		HashMap<String, ArrayList<Event>> events;

		// determines the day num of the current day, so that we can determine how many
		// days to backpedal in order to point at sunday
		int dayOffset = calculateDayOfWeek(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
		today = today.minusDays(dayOffset);

		// Used for printing the correct day numbers of this week on the jsp
		for (int i = 0; i < 7; i++) {
			dates.add(today);
			today = today.plusDays(1);
		}
		events = eventService.getEventsByTimeRange(dates.get(0).toString(), dates.get(dates.size() - 1).toString());
		
	

		// Set today to the first day of this week
		today = today.minusDays(7);
		model.addAttribute("curWeekDate", today);
		model.addAttribute("curWeekMonthString", monthNumToString(today.getMonthValue()));
		// Set today to next weeks date
		today = today.plusDays(7);
		model.addAttribute("nextWeekDate", today.toString());
		// Set today to last weeks date
		today = today.minusDays(14);
		model.addAttribute("prevWeekDate", today.toString());
		// Day numbers to be printed
		model.addAttribute("dates", dates);
		model.addAttribute("events", events);
		// Set today to curDay for daily info section
		today = LocalDate.parse(date);
		model.addAttribute("curDayDate", today);
		model.addAttribute("curDayMonthString", monthNumToString(today.getMonthValue()));
		// Day, Month, and Year Values to pass to DaysOfTheYearAPI link
		if (dayMonthString.length() < 2) {
			dayMonthString = "0" + dayMonthString;
		}
		if (dayDayString.length() < 2) {
			dayDayString = "0" + dayDayString;
		}

		model.addAttribute("dayMonthString", dayMonthString);
		model.addAttribute("dayDayString", dayDayString);

		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).toString().equals(date)) {
				model.addAttribute("dayEvents", events.get(dates.get(i).toString()));
			}
		}

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
		List<String> employees = new ArrayList<>(Arrays.asList(addEmployees.split(", ")));
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
	public String displayDayApiEvent(Model model, @RequestParam(required = false) String date,
			@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer day) {

		// If page is entered with no params (clicking on weekly view instead of either
		// of the arrows)
		LocalDate today;
		if (date == null) {
			today = LocalDate.of(2022, month, day);

		} else {
			today = LocalDate.parse(date);
		}
		date = today.toString();

		// Stores the numbers to be printed for the current week
		List<LocalDate> dates = new ArrayList<LocalDate>(7);
		HashMap<String, ArrayList<Event>> events;

		// determines the day num of the current day, so that we can determine how many
		// days to backpedal in order to point at sunday
		int dayOffset = calculateDayOfWeek(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
		today = today.minusDays(dayOffset);

		// Used for printing the correct day numbers of this week on the jsp
		for (int i = 0; i < 7; i++) {
			dates.add(today);
			today = today.plusDays(1);
		}
		events = eventService.getEventsByTimeRange(dates.get(0).toString(), dates.get(6).toString());
		
		// Used for generating data for DaysOfYearApi call on jsp
		String dayMonthString = monthToString(month);
		String dayDayString = dayToString(day);
		String dayEventName = "";
		String dayEventUrl = "";
		DayEvent[] dayEvents = dayService.getSpecificDateEvents("2022", dayMonthString, dayDayString);
		for (DayEvent d : dayEvents) {
			dayEventName = d.getName();
			dayEventUrl = d.getUrl();
		}
		model.addAttribute("dayEventName", dayEventName);
		model.addAttribute("dayEventUrl", dayEventUrl);

		// Set today to the first day of this week
		today = today.minusDays(7);
		model.addAttribute("curWeekDate", today);
		model.addAttribute("curWeekMonthString", monthNumToString(today.getMonthValue()));
		// Set today to next weeks date
		today = today.plusDays(7);
		model.addAttribute("nextWeekDate", today.toString());
		// Set today to last weeks date
		today = today.minusDays(14);
		model.addAttribute("prevWeekDate", today.toString());
		// Day numbers to be printed
		model.addAttribute("dates", dates);
		model.addAttribute("events", events);
		// Set today to curDay for daily info section
		today = LocalDate.parse(date);
		model.addAttribute("curDayDate", today);
		model.addAttribute("curDayMonthString", monthNumToString(today.getMonthValue()));
		// Day, Month, and Year Values to pass to DaysOfTheYearAPI link
		if (dayMonthString.length() < 2) {
			dayMonthString = "0" + dayMonthString;
		}
		if (dayDayString.length() < 2) {
			dayDayString = "0" + dayDayString;
		}
		// to pass into links on JSP
		model.addAttribute("dayMonthString", dayMonthString);
		model.addAttribute("dayDayString", dayDayString);

		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).toString().equals(date)) {
				model.addAttribute("dayEvents", events.get(dates.get(i).toString()));
			}
		}

		return "week";
	}

	// helper method to make sure month is always 2 digits (required by API call)
	public String monthToString(Integer month) {
		if (month.toString().length() == 1) {
			String newMonth = "0" + month;
			return newMonth;
		}
		return month.toString();
	}

	// helper method to make sure day is always 2 digits (required by API call)
	public String dayToString(Integer day) {
		if (day.toString().length() == 1) {
			String newDay = "0" + day;
			return newDay;
		}
		return day.toString();
	}

	@RequestMapping("/schedule-finder")
	public String displayScheduleFinder() {
		return "schedule-finder";
	}

	@GetMapping("/schedule-finder1")
	public String displayTimes(@RequestParam String start, @RequestParam String end,
			@RequestParam String employees, Model model) {

		//call all events to be able to search for conflicts
		Event[] events = eventService.getAllEvents();
		
		//parse employees to search as an arraylist
		List<String> parsedEmployeeList = new ArrayList<>(Arrays.asList(employees.split(", ")));
		
		//instantiate initial messages, to be filled below upon conflict
		StringBuilder conflictMessage = new StringBuilder();
		String conflictMessageString = "";

		
		//turning the start and end time Strings into LDT format
		LocalDateTime searchStart = LocalDateTime.parse(start);
		LocalDateTime searchEnd = LocalDateTime.parse(end);
		
		//search each event for conflict
		for (Event e : events) {
			//get start/end of event time (String) and convert to LDT
			LocalDateTime eventStart = LocalDateTime.parse(e.getStart());
			LocalDateTime eventEnd = LocalDateTime.parse(e.getEnd());
			
			//get employee list from event
			List<String> employeeList = e.getEmployees();
			
			//calculate duration of event
			Long d = ChronoUnit.MINUTES.between(eventStart, eventEnd);
			Long duration = (long) ((d.doubleValue()) / 60);
			
			//set suggested start and end times to 5 min before/after each event
			LocalDateTime suggestStart1 = eventStart.plusHours(-duration);
			LocalDateTime suggestEnd1 = eventStart.plusMinutes(-5);
			LocalDateTime suggestStart2 = eventEnd.plusMinutes(5);
			LocalDateTime suggestEnd2 = eventEnd.plusHours(duration);
			
			//check if start/end times conflict with event times on calendar
			//these are all different time search conditions
			if ((searchStart.isAfter(eventStart) && (searchEnd.isBefore(eventEnd))) || 
					(searchStart.isEqual(eventStart) && searchEnd.isEqual(eventEnd)) ||
					searchStart.isEqual(eventStart) || searchStart.isEqual(eventEnd) ||
					searchEnd.isEqual(eventStart) || searchEnd.isEqual(eventEnd) ||
					(searchEnd.isAfter(eventStart) && searchEnd.isBefore(eventEnd)) ||
					(searchStart.isBefore(eventStart) && searchEnd.isAfter(eventEnd)) ||
					(searchStart.isAfter(eventStart) && searchStart.isBefore(eventEnd)) ||
					(searchStart.isEqual(eventEnd) && searchEnd.isAfter(eventEnd))) {
				
				for (int i = 0; i < parsedEmployeeList.size(); i++) {
				
					for (String name : employeeList) {
						
						//if there is a conflict, check to see if there is an employee who matches the conflict
						if (parsedEmployeeList.get(i).equalsIgnoreCase(name)) {
							conflictMessage.append("This time conflicts with " + parsedEmployeeList.get(i) + "'s event named " + e.getEventName() + " on " + eventStart.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "<br>");
							conflictMessage.append("1. Suggested Start Time: " + suggestStart1.format(DateTimeFormatter.ofPattern("hh:mm a")) + " Suggested End Time: " + suggestEnd1.format(DateTimeFormatter.ofPattern("hh:mm a")) + "<br>");
							conflictMessage.append("2. Suggested Start Time: " + suggestStart2.format(DateTimeFormatter.ofPattern("hh:mm a"))+ " Suggested End Time: " + suggestEnd2.format(DateTimeFormatter.ofPattern("hh:mm a")) + "<br><br>");
							conflictMessageString = conflictMessage.toString();

						}
						
					}
				}
			} 

	}
		//return data to the view
		model.addAttribute("conflictMessage", conflictMessage);
		model.addAttribute("conflictMessageString", conflictMessageString);
		model.addAttribute("searchEnd",searchEnd);
		model.addAttribute("searchStart", searchStart);
		model.addAttribute("employees", employees);
		

		return "/schedule-finder";
	}
}