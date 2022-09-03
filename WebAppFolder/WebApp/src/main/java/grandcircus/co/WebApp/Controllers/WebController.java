package grandcircus.co.WebApp.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//Month will be default page
	@RequestMapping("/monthly-calendar")
	public String displayMonth(@RequestParam(required=false) Integer year, @RequestParam(required=false) Integer month, Model model) {
		//If a date is not provided as is the case when "/" is visited, it will show current month on calendar
		if(year == null || month == null) {
			LocalDate currentDate = LocalDate.now();
			month = currentDate.getMonthValue();
			year = currentDate.getYear();
		}
		
		//monthStr String is for easier output on the jsp
		String monthStr = "";
		switch(month) {
			case 1:  monthStr = "January";
					 break;	
			case 2:  monthStr = "February";
					 break;
			case 3:  monthStr = "March";
					 break;
			case 4:  monthStr = "April";
					 break;
			case 5:  monthStr = "May";
					 break;
			case 6:  monthStr = "June";
					 break;
			case 7:  monthStr = "July";
					 break;
			case 8:  monthStr = "August";
					 break;
			case 9:  monthStr = "September";
					 break;
			case 10: monthStr = "October";
					 break;
			case 11: monthStr = "November";	
					 break;
			case 12: monthStr = "December";
					 break;
			default: monthStr = "Invalid month";
					 break;
		}
		
		//find the number of days in the currently views month
		int numDaysInMonth = numDaysInMonth(month, year);
				
		//startDay is offset determined by the start day of the month
		int startDay = calculateDayOfWeek(1, month, year);
		
		//Array stores the values of each gridpoint on the calendar...
		//either an empty string if the gridpoint is not occupied, otherwise the day number
		String[] dayNums;
		ArrayList<Event[]> dailyEvents;
		String dayStartTime;
		String dayEndTime;
		
		if(numDaysInMonth + startDay > 35) {
			dayNums = new String[42];
			dailyEvents = new ArrayList<Event[]>(42);
		}
		else {
			dayNums = new String[35];
			dailyEvents = new ArrayList<Event[]>(42);
		}
		Arrays.fill(dayNums, 0, dayNums.length, "");
		
		//Used for printing the correct day
		int dayNum = 1;
		for(int i = startDay; i < (numDaysInMonth + startDay); i++) {
			dayStartTime = year + "-" + month + "-" + dayNum + "T00:00:00";
			dayEndTime = year + "-" + month + "-" + dayNum + "T23:59:59";
			dailyEvents.add(eventService.getEventsByTimeRange(dayStartTime, dayEndTime));
			
			dayNums[i] = dayNum + "";
			dayNum++;
		}
		
		//Add first few days of next month to fill in whitespace
		dayNum = 1;
		for(int i = (numDaysInMonth + startDay); i < dayNums.length; i++) {
			dayNums[i] = dayNum + "";
			dayNum++;
		}
		
		//Add last few days of previous month to fill in whitespace
		if(month <= 1) dayNum = numDaysInMonth(12, year-1); 
		else dayNum = numDaysInMonth(month - 1, year);
		
		for(int i = startDay - 1; i >= 0; i--) {
			dayNums[i] = dayNum + "";
			dayNum--;
		}
		
		// This is a representation of the dayNums array that is sent to the JSP -->
		//["", "", "", "",  1,  2,  3,
		//  4,  5,  6,  7,  8,  9, 10,
		// 11, 12, 13, 14, 15, 16, 17,
		// 18, 19, 20, 21, 22, 23, 24,
		// 25, 26, 27, 28, 29, 30, ""]
		
		//Variables for switching between differnt months on the view
		int prevYear = year;
		int prevMonth = month - 1;
		int nextYear = year;
		int nextMonth = month + 1;
		
		if(prevMonth < 1) {
			prevMonth = 12;
			prevYear--;;
		}
		else if(nextMonth > 12) {
			nextMonth = 1;
			nextYear++;
		}
				
		//Info used and displayed on monthly calendar
		model.addAttribute("dailyEvents", dailyEvents);
		model.addAttribute("prevYear", prevYear);
		model.addAttribute("nextYear", nextYear);
		model.addAttribute("prevMonth", prevMonth);
		model.addAttribute("nextMonth", nextMonth);
		model.addAttribute("dayNums", new ArrayList<String>(Arrays.asList(dayNums)));
		model.addAttribute("year", year);
		model.addAttribute("monthStr", monthStr);
		model.addAttribute("monthNum", month);
		
		return "month";
	}
	
	/**
	 * Helper method for printing dynamic calendar. Determines the day of the week that the date is one
	 * 
	 * @param day [1-31]
	 * @param month [1-12]
	 * @param year (4 digit format eg. 2022)
	 * @return the number of the day the given date is on (0 = sunday, 1 = monday, etc....)
	 */
	public int calculateDayOfWeek(int day, int month, int year) {
		//Source:
		//https://artofmemory.com/blog/how-to-calculate-the-day-of-the-week/
		
		int[] monthCodes = {0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5};
		int yearCode, monthCode, centuryCode = 6, dateNum, leapYearCode;
		yearCode = ((year % 100) + ((year % 100)/4)) % 7;
		monthCode = monthCodes[month - 1];
		switch(year / 100) {
			case 17: centuryCode = 4;
					 break;
			case 18: centuryCode = 2;
					 break;
			case 19: centuryCode = 0;
					 break;
			case 20: centuryCode = 6;
			 		 break;
			case 21: centuryCode = 4;
			 		 break;
			case 22: centuryCode = 2;
			 		 break;
			case 23: centuryCode = 0;
	 		 		 break;	
		}
		dateNum = day;
		if(((year % 4 == 0 && year % 100 != 0)|| year % 400 == 0) && (month == 0 || month == 1))
			leapYearCode = -1;
		else 
			leapYearCode = 0;
		
		return (yearCode + monthCode + centuryCode + dateNum + leapYearCode) % 7;
	}
	
	/**
	 * Return the number of days in the given month
	 * @param monthNum the number of the month [1-12]
	 * @param year the year in gregorian calendar (eg. 2022)
	 * @return the number of days in the given month
	 */
	public int numDaysInMonth(int monthNum, int year) {
		int leapYearCode = 0;
		int[] numDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		//Checks if its a leap year
		if(((year % 4 == 0 && year % 100 != 0)|| year % 400 == 0) && (monthNum == 2))
			leapYearCode = 1;
		
		return numDays[monthNum - 1] + leapYearCode;
	}

	@RequestMapping("/weekly-calendar")
	public String displayWeek(Model model, @RequestParam(required=false) Integer month, @RequestParam(required=false) Integer weekNum,
			@RequestParam(required=false) Integer year) {

		//If a date is not provided as is the case when "/" is visited, it will show current month on calendar
				if(year == null || month == null) {
					LocalDate currentDate = LocalDate.now();
					month = currentDate.getMonthValue();
					year = currentDate.getYear();
				}
				//Calculate the current week of the year
				Date date = new Date();
				GregorianCalendar cal = new GregorianCalendar();
				cal.setFirstDayOfWeek(GregorianCalendar.SUNDAY);
				cal.setMinimalDaysInFirstWeek(1);
				cal.setTime(date);
				int weekOfYear = cal.get(GregorianCalendar.WEEK_OF_YEAR);
				
				//monthStr String is for easier output on the jsp
				String monthStr = "";
				switch(month) {
					case 1:  monthStr = "January";
							 break;	
					case 2:  monthStr = "February";
							 break;
					case 3:  monthStr = "March";
							 break;
					case 4:  monthStr = "April";
							 break;
					case 5:  monthStr = "May";
							 break;
					case 6:  monthStr = "June";
							 break;
					case 7:  monthStr = "July";
							 break;
					case 8:  monthStr = "August";
							 break;
					case 9:  monthStr = "September";
							 break;
					case 10: monthStr = "October";
							 break;
					case 11: monthStr = "November";	
							 break;
					case 12: monthStr = "December";
							 break;
					default: monthStr = "Invalid month";
							 break;
				}
				
				//find the number of days in the currently views month
				int numDaysInMonth = numDaysInMonth(month, year);
						
				//startDay is offset determined by the start day of the month
				int startDay = calculateDayOfWeek(1, month, year);
				
				//Array stores the values of each gridpoint on the calendar...
				//either an empty string if the gridpoint is not occupied, otherwise the day number
				String[] dayNums = new String[7];
				Arrays.fill(dayNums, 0, dayNums.length, "");
				
				//Used for printing the correct day
				int dayNum = 1;
				for(int i = startDay; i < (startDay+3); i++) {
					dayNums[i] = dayNum + "";
					dayNum++;
				}
				
				//Add last few days of previous month to fill in whitespace
				if(month <= 1) dayNum = numDaysInMonth(12, year-1); 
				else dayNum = numDaysInMonth(month - 1, year);
				
				for(int i = startDay - 1; i >= 0; i--) {
					dayNums[i] = dayNum + "";
					dayNum--;
				}	
	
				//Variables for switching between differnt months on the view
				int prevYear = year;
				int prevMonth = month - 1;
				int nextYear = year;
				int nextMonth = month + 1;
				int nextWeek = weekOfYear + 1;
				int prevWeek = weekOfYear - 1;
				
				if(prevMonth < 1) {
					prevMonth = 12;
					prevYear--;;
				}
				else if(nextMonth > 12) {
					nextMonth = 1;
					nextYear++;
				}
				if (prevWeek < 1) {
					prevMonth = 12;
					prevYear--;;
				}
				if (nextWeek > 53) {
					nextMonth = 1;
					nextYear++;
				}
						
				//Info used and displayed on monthly calendar
				model.addAttribute("currentWeek", weekOfYear);
				model.addAttribute("prevWeek", prevWeek);
				model.addAttribute("nextWeek", nextWeek);
				model.addAttribute("prevYear", prevYear);
				model.addAttribute("nextYear", nextYear);
				model.addAttribute("prevMonth", prevMonth);
				model.addAttribute("nextMonth", nextMonth);
				model.addAttribute("dayNums", new ArrayList<String>(Arrays.asList(dayNums)));
				model.addAttribute("year", year);
				model.addAttribute("monthStr", monthStr);
				model.addAttribute("monthNum", month);
		
		return "week";
	}
	
	@PostMapping("/submitEvent")
	public String submitEvent(
			@RequestParam String eventName, @RequestParam Date start, @RequestParam Date end, 
			@RequestParam String addEmployees, Model model) {
		String str = addEmployees;
		List<String> employees = new ArrayList<>(Arrays.asList(str.split(",")));
//		model.addAttribute("eventName", eventName);
//		model.addAttribute("start", start);
//		model.addAttribute("end", end);
//		model.addAttribute("addEmployees", addEmployees);
//		model.addAttribute("employees", employees);
		return "month";
	}

	@RequestMapping("/find-time")
	public String displayFindTime(Model model) {

		return "find-time";
	}
	
	@RequestMapping("/test")
	public String displayTest(Model model) {
		System.out.println(dayService.dayEventDataResponse().getDayEvents());
	
		return "redirect:/monthly-calendar";
	}
	

}

