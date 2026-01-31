package com.degreepath.degreepath;

import org.springframework.web.bind.annotation.*; //they're built-in tools for handling HTTP requests.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.util.ArrayList;

@RestController //annotation that means this class handles web requests and returns JSON data to show SpringBoot it has web endpoints / web api
@RequestMapping("/api") //All endpoints in this class start with /api
public class PlannerController {
	private List<Course> allCourses; //load this from JSON
	
	//this runs when controller created
	public PlannerController() {
		try {
			//load courses from JSON
			ObjectMapper mapper = new ObjectMapper();
			InputStream inputStream = getClass().getResourceAsStream("/data/courses.json");
			Map<String, List<Course>> data = mapper.readValue(inputStream, new TypeReference<Map<String,List<Course>>>(){});
			allCourses = data.get("courses");
			System.out.println("Loaded " + allCourses.size() + " courses");
			
		}catch (Exception e) {
			System.err.println("Error loading courses: " + e.getMessage());
			allCourses = new ArrayList<>();
		}
	}
	
	@GetMapping("/test")
	public String test() {
		return "DegreePath API is working!";
	}
	
	@GetMapping("/courses")
	public List<Course> getCourses(){
		return allCourses;
	}
	
}
