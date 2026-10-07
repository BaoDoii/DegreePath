package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;

public class PrerequisiteChecker {
	
	public static boolean canTakeCourse(Course course, List<String> completedCourses) {
		//get course prereqs, if null or empty return true, check entire prereq list
		List<String> prerequisites = course.getPrerequisites();
		if(prerequisites == null || prerequisites.isEmpty()) {
			return true;
		}
		for(String prereq: prerequisites) {
			//if commpleted courses do not contain prereq then return false
			if(!completedCourses.contains(prereq)) {
				return false;
			}
		}
		return true;
	}
	
	//checks a whole multi-semester plan, prereqs have to be done in an EARLIER semester (not the same one)
	//returns a list of problems, empty list = plan is valid
	public static List<String> validatePlan(List<String> initiallyCompleted, List<List<Course>> semesters) {
		List<String> problems = new ArrayList<>();
		List<String> completed = new ArrayList<>(initiallyCompleted);
		
		for(int i = 0; i < semesters.size(); i++) {
			//check every course in this semester against what was finished BEFORE it
			for(Course c : semesters.get(i)) {
				if(!canTakeCourse(c, completed)) {
					problems.add(c.getCode() + " in semester " + (i + 1) + " is missing prerequisites");
				}
			}
			//then mark this semester's courses as done for the next one
			for(Course c : semesters.get(i)) {
				completed.add(c.getCode());
			}
		}
		return problems;
	}
}
