package com.degreepath.degreepath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//libraries to import
import org.springframework.boot.CommandLineRunner; // runs code when app starts
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;//reading JSON
import com.fasterxml.jackson.core.type.TypeReference; //tells jackson complex type structure for Map<String, List<Course>>)

import java.io.InputStream;
import java.util.Map;
import java.util.List;


@SpringBootApplication
public class DegreepathApplication {

	public static void main(String[] args) {
		SpringApplication.run(DegreepathApplication.class, args);
	}

@Bean
CommandLineRunner run() {
	return args -> {
		System.out.println("Loading courses from JSON...");

		//object mapper to read json / translate json -> java obj
		ObjectMapper mapper = new ObjectMapper();
		
		//get file as an input stream
		InputStream inputStream = getClass().getResourceAsStream("/data/courses.json");
		
		//parse JSON into Map<String, List<Course>>
		//so mapper is the translator, and readvalue arguments is where is the data, what should the JSON become
		//..turn it into a map where keys are Strings and values are lists of Course objects
		//ObjectMapper = tool that converts JSON ↔ Java
		//readValue(where, what) = read from where, convert to what type
		//getResourceAsStream() = find files in your project's resources folder
		Map<String, List<Course>> data = mapper.readValue(inputStream, new TypeReference<Map<String, List<Course>>>(){});

		//extract courses from the map
		List<Course> courses = data.get("courses");
		
		System.out.println("\n=== COURSES LOADED ===");
		for(Course course : courses) {
			System.out.println(course);
		}
		
		System.out.println("Total courses: " + courses.size());
		
		//=============TEST PREREQUISITE CHECKING====================
		System.out.println("\n===TESTING PREREQUISITE CHECKER===");
		//simulate student who completed math130 and cs101
		List<String> completedCourses = List.of("MATH130");
		System.out.println("Student has completed: " + completedCourses);
		
		//test each course
		for(Course course : courses) {
			boolean canTake = PrerequisiteChecker.canTakeCourse(course, completedCourses);
			System.out.println(course.getCode() + ": " + (canTake ? " Can Take" : " Cannot take"));
		}
		
	};
	
}}

//## Visual Explanation of the Whole Flow
//```
//Step 1: Create the translator
//ObjectMapper mapper = new ObjectMapper();
//        ↓
//[mapper is ready to convert JSON ↔ Java]
//
//Step 2: Find the file
//InputStream inputStream = getClass().getResourceAsStream("/data/courses.json");
//        ↓
//[inputStream is pointing to your courses.json file]
//
//Step 3: Translate JSON → Java
//Map<String, List<Course>> data = mapper.readValue(inputStream, new TypeReference<...>() {});
//        ↓
//[mapper reads the JSON from inputStream]
//        ↓
//[sees structure: {"courses": [...]}]
//        ↓
//[creates a Map with key "courses" and value List<Course>]
//        ↓
//[fills in Course objects using your setters with the Jackson by matching setter functions to strings of value in json,
//in general jackson matches json key to setter names, creates Course object using empty constructor since it sees List<Course> in type,
//then fills each obj by calling setters w values from json
//        ↓
//[returns the complete Map]
//
//Step 4: Extract what we need
//List<Course> courses = data.get("courses");
//        ↓
//[Get the list from the "courses" key]
//
//Step 5: Use it!
//for (Course course : courses) {
//    System.out.println(course);
//}
