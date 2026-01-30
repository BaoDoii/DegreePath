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

		//object mapper to read json
		ObjectMapper mapper = new ObjectMapper();
		
		//get file as an input stream
		InputStream inputStream = getClass().getResourceAsStream("/data/courses.json");
		
		//parse JSON into Map<String, List<Course>>
		Map<String, List<Course>> data = mapper.readValue(inputStream, new TypeReference<List<Course>>()) {});

		//extract courses from the map
		List<Course> courses = (List<Course>) data;
		
		System.out.println("\n=== COURSES LOADED ===");
		for(Course course : courses) {
			System.out.println(course);
		}
		
		System.out.println("Total courses: " + courses.size());
	};
	
}}
