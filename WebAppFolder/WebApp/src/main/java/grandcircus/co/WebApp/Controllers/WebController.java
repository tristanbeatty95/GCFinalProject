package grandcircus.co.WebApp.Controllers;

import java.util.Calendar;
import java.util.Date;

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
		//TODO: Finish sending date information to view for dynamic month creation
		//Tim: "I was working on this but something came up, will finish this evening"
		Calendar c = Calendar.getInstance();
		Date today = new Date();
		
		int y = c.get(Calendar.YEAR); 
		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);
		
		
		
		return "redirect:/monthly-calendar";
	}
	
	//Month will be default page
	@RequestMapping("/monthly-calendar")
	public String displayMonth(@RequestParam int year, @RequestParam int month, Model model) {

		return "month";
	}

	@RequestMapping("/weekly-calendar")
	public String displayWeek(Model model) {

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

