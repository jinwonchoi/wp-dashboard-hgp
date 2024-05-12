package com.gencode.issuetool.ctrl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {
	@Value("${keti.web.url}") String ketiWebUrl;
//  @GetMapping("/chatsimul")
//  public String index(Model model) {
//      model.addAttribute("eventName", "FIFA 2018");
//      return "index";
//  }
	@GetMapping("/keti/index")
	public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
		attributes.addFlashAttribute("flashAttribute","redirectWithRedirectView");
		attributes.addAttribute("attribute","redirectWithRedirectView");
		//return new RedirectView("/wpdashboard/fmon/keti-page.html");
		//return new RedirectView("http://dt.rozetatech.com:8080");
		return new RedirectView(ketiWebUrl);
	}
	
	
}