package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;

public class SemesterPlanner {
	
	public static List<Course> generateSemester(List<String> completedCourses, List<Course> allCourses, int maxUnits){
		List<Course> selectedCourses = new ArrayList<>();
		int totalUnits = 0;
		
		//can take courses, not guided yet but just fixed to add up to max Units for now v1
		for(Course course: allCourses) {
			boolean canTake = PrerequisiteChecker.canTakeCourse(course, completedCourses);
			if(canTake) {
				if(totalUnits + course.getUnits() <= maxUnits) {
				selectedCourses.add(course);
				totalUnits += course.getUnits();
				}
			}
		}
		
		return selectedCourses;
	}
}
