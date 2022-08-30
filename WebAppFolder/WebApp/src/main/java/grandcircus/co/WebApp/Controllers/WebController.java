package grandcircus.co.WebApp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class WebController {

	public String displayLogin(Model model) {

		return "login";
	}

	public String displayMonth(Model model) {

		return "month";
	}

	public String displayWeek(Model model) {

		return "week";
	}

	public String displayAddEvent(Model model) {

		return "add-event";
	}

	public String displayFindTime(Model model) {

		return "find-time";
	}

}

