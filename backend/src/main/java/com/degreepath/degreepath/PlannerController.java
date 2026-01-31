package com.degreepath.degreepath;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlannerController {
	private List<Course> allCourses; //load this from JSOn
	
	@GetMapping("/test")
	public String test() {
		return "DegreePath API is working!";
	}
}
