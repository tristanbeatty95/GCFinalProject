package grandcircus.co.WebApp.Controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
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
		
		//Array stores the values of each gridpoint on the calendar...
		//either an empty string if the gridpoint is not occupied, otherwise the day number
		String[] dayNums = new String[35];
		Arrays.fill(dayNums, 0, dayNums.length, "");
		int numDaysInMonth = numDaysInMonth(month, year);
		
		//startDay is offset determined by the start day of the month
		int startDay = calculateDayOfWeek(1, month, year);
		
		//Used for printing the correct day
		int dayPointer = 1;
		for(int i = startDay; i < (numDaysInMonth + startDay-1); i++) {
			dayNums[i] = dayPointer + "";
			dayPointer++;
		}
		
		// This is a representation of the dayNums array that is sent to the JSP -->
		//["", "", "", "",  1,  2,  3,
		//  4,  5,  6,  7,  8,  9, 10,
		// 11, 12, 13, 14, 15, 16, 17,
		// 18, 19, 20, 21, 22, 23, 24,
		// 25, 26, 27, 28, 29, 30, ""]
				
		//Info used and displayed on monthly calendar
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
		if(((year % 4 == 0 && year % 100 != 0)|| year % 400 == 0) && (monthNum == 1))
			leapYearCode = 1;
		
		return numDays[monthNum - 1] + leapYearCode;
	}

	@RequestMapping("/weekly-calendar")
	public String displayWeek(Model model, @RequestParam String month) {
		//Create String Array that represents each week day
		String[] dayNums = new String[7];
		
		//Calculate the current day to know which week to default to
		LocalDate currentDate = LocalDate.now();
		int day = currentDate.getDayOfMonth();
		int month1 = currentDate.getMonthValue();
		int year = currentDate.getYear();
		
		//ASK FOR HELP HERE...not sure if this is relevant
		Arrays.fill(dayNums, day, dayNums.length, "");
		
		//currentDay is calculated
		int currentDay = calculateDayOfWeek(day, month1, year);
		
		//Used for printing the correct day number on the correct day of week
		int dayNumber = currentDay;
		
		//The following if statements place the day of the month into
		//each day of the week on the view and return an empty string
		//if the date goes out of range
		
		//if today is Sunday
		if(dayNumber == 0) {
			for(int i = dayNumber; i < 7; i++) {
				dayNums[i] = day + "";
				if(dayNums[i].equals("32")
						|| dayNums[i].equals("33")|| dayNums[i].equals("34")
						|| dayNums[i].equals("35")|| dayNums[i].equals("36")
						|| dayNums[i].equals("37")) {
					dayNums[i] = "";
				}
				dayNumber++;
				day++;
			}
		}
		//if today is Monday
				if(dayNumber == 1) {
					for(int i = dayNumber-1; i < 7; i++) {
						dayNums[i] = day-1 + "";
						if(dayNums[i].equals("0")|| dayNums[i].equals("32")
								|| dayNums[i].equals("33")|| dayNums[i].equals("34")
								|| dayNums[i].equals("35")|| dayNums[i].equals("36")) {
							dayNums[i] = "";
						}
						dayNumber++;
						day++;
					}
				}
		//if today is Tuesday
		if(dayNumber == 2) {
			for(int i = dayNumber-2; i < 7; i++) {
				dayNums[i] = day-2 + "";
				if(dayNums[i].equals("0") 
						|| dayNums[i].equals("-1")|| dayNums[i].equals("32")
						|| dayNums[i].equals("33")|| dayNums[i].equals("34")
						|| dayNums[i].equals("35")) {
					dayNums[i] = "";
				}
				dayNumber++;
				day++;
			}
		}
		//if today is Wednesday
				if(dayNumber == 3) {
					for(int i = dayNumber-3; i < 7; i++) {
						dayNums[i] = day-3 + "";
						if(dayNums[i].equals("0") 
								|| dayNums[i].equals("-1")|| dayNums[i].equals("32")
								|| dayNums[i].equals("-2")|| dayNums[i].equals("33")
								|| dayNums[i].equals("34")) {
							dayNums[i] = "";
						}
						dayNumber++;
						day++;
					}
				}
		//if today is Thursday
		if(dayNumber == 4) {
			for(int i = dayNumber-4; i < 7; i++) {
				dayNums[i] = day-4 + "";
					if(dayNums[i].equals("0") 
							|| dayNums[i].equals("-1")|| dayNums[i].equals("32")
							|| dayNums[i].equals("-2")|| dayNums[i].equals("33") 
							|| dayNums[i].equals("-3")) {
						dayNums[i] = "";
					}
				dayNumber++;
				day++;
			}
		}
		//if today is Friday
				if(dayNumber == 5) {
					for(int i = dayNumber-5; i < 7; i++) {
						dayNums[i] = day-5 + "";
							if(dayNums[i].equals("0") 
									|| dayNums[i].equals("-1")|| dayNums[i].equals("32")
									|| dayNums[i].equals("-2") 
									|| dayNums[i].equals("-3")
									|| dayNums[i].equals("-4")) {
								dayNums[i] = "";
							}
						dayNumber++;
						day++;
					}
				}
				//if today is Saturday
				if(dayNumber == 6) {
					for(int i = dayNumber-6; i < 7; i++) {
						dayNums[i] = day-6 + "";
							if(dayNums[i].equals("0") 
									|| dayNums[i].equals("-1")
									|| dayNums[i].equals("-2") 
									|| dayNums[i].equals("-3")
									|| dayNums[i].equals("-4")
									|| dayNums[i].equals("-5")) {
								dayNums[i] = "";
							}
						dayNumber++;
						day++;
					}
				}
		
		model.addAttribute("monthStr", month);
		model.addAttribute("year", year);
		model.addAttribute("dayNums", new ArrayList<String>(Arrays.asList(dayNums)));
		
		return "week";
	}
	
	@RequestMapping("/add-event")
	public String displayAddEvent(Model model) {

		return "add-event";
	}

	@RequestMapping("/find-time")
	public String displayFindTime(Model model) {

		return "find-time";
	}

}

