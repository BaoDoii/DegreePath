package com.degreepath.degreepath;

import java.util.List;

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
}
