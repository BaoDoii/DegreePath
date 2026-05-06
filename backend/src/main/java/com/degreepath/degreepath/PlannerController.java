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
	
	@GetMapping("/health")
	public String health() {
		return "ok";
	}
	
	@GetMapping("/courses")
	public List<Course> getCourses(){
		return allCourses;
	}
	
	@PostMapping("/plan") //POST  //RequestBody = take Json from POST Request body, converts JSON into  Map, give back as request parameter
	public Map<String, Object> generatePlan(@RequestBody Map<String, Object> request){
		//extract the completed courses from request map
		List<String> completedCourses = (List<String>)request.get("completed");
		//extract map units from request map
		int maxUnits = (int) request.get("maxUnits");
		
		//extract numGEs and default to 0 if not provided
		int numGEs = request.containsKey("numGEs") ? (int) request.get("numGEs"): 0;
		
		//call SemesterPlanner to generate plan
		List<Course> semesterPlan = SemesterPlanner.generateSemester(completedCourses, allCourses, maxUnits, numGEs);
		//calculate total units
		int totalUnits = 0;
		for(Course course: semesterPlan) {
			totalUnits += course.getUnits();
		}
		//build response map to return
		Map<String, Object> response = Map.of(
				"courses", semesterPlan,
				"totalUnits", totalUnits,
				"completed", completedCourses,
				"maxUnits", maxUnits
				);
		
		return response;
	}
	
//----visual diagram of /plan
//List<String> completedCourses = (List<String>) request.get("completed");
// completedCourses = ["MATH130", "CS101"]

//	int maxUnits = (int) request.get("maxUnits");  // Note: should be "maxUnits" not "units"
//	// maxUnits = 8
//	```
//
//	---
//
//	## Visual Diagram:
//	```
//	Student/Frontend
//	    ↓ (sends JSON)
//	{
//	  "completed": ["MATH130"],
//	  "maxUnits": 8
//	}
//	    ↓
//	@PostMapping("/plan")
//	    ↓
//	@RequestBody converts JSON → Map
//	    ↓
//	request = {"completed": [...], "maxUnits": 8}
//	    ↓
//	You extract: request.get("completed")
//	    ↓
//	completedCourses = ["MATH130"]
//	    ↓
//	Call: SemesterPlanner.generateSemester(allCourses, completedCourses, 8)
//	    ↓
//	Returns: [CS101, CS211]
//	    ↓
//	You return JSON response
//	    ↓
//	Student/Frontend receives it
	
	
	
	@PostMapping("/multiplan")
	public Map<String, Object> generateMultiPlan(@RequestBody Map<String, Object> request){
		//exact input
		List<String> completedCourses = ((List<String>) request.get("completed"));
		int maxUnits = (int) request.get("maxUnits");
		int numGEs = request.containsKey("numGEs") ? (int) request.get("numGEs") : 0;
		int numberOfSemesters = request.containsKey("numberOfSemesters") ? (int) request.get("numberOfSemesters") : 0;
		
		//data structures
		List<String> currentlyCompleted =  new ArrayList<>(completedCourses);
		List<Map<String,Object>> allSemesters = new ArrayList<>();
		
		//loop semester planning
		if(numberOfSemesters > 0) {
			for(int i = 0; i < numberOfSemesters; i++) {								
				List<Course> semesterPlan = SemesterPlanner.generateSemester(currentlyCompleted, allCourses, maxUnits, numGEs);
				//if no courses stop
				if(semesterPlan.size() == 0) {
					break;
				}
				//calc total units
				int totalUnits = 0;
				for(Course course: semesterPlan) {
					totalUnits += course.getUnits();
				}
				
				//build semester object
				Map<String, Object> semester = Map.of(
					"semesterNumber",i+1,
					"courses", semesterPlan,
					"totalUnits", totalUnits
				);
				
				//add to results
				allSemesters.add(semester);
				
				//update completed for next semester
				for(Course course: semesterPlan) {
					currentlyCompleted.add(course.getCode());
				}
				
			}
		}
		//return all semesters
		return Map.of(
		"semesters", allSemesters,
		"initialCompleted", completedCourses,
		"numberOfSemesters", allSemesters.size()
		);		
	}
}
