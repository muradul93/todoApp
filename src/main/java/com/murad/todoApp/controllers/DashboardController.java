package com.murad.todoApp.controllers;

import com.murad.todoApp.services.DashboardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@Api(tags = "dashboard", value = "dashboard")
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;;

	@GetMapping({ "", "/" })
	public String dashboard(Model model) {
		log.info("Get ToDo Dashboard");
		model.addAttribute("statusWiseCountList", dashboardService.getTodoCountGroupByStatus());
		return "/dashboard";
	}

}

