package grandcircus.co.WebApp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String displayMonth(Model model) {

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

