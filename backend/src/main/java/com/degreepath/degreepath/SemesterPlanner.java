package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;

public class SemesterPlanner {
	
	//count how many courses depend on the course as a prereq
	private static int countDependents(Course course, List<Course> allCourses) {
		int count = 0;
		
		for(Course c: allCourses) {
			if(c.getPrerequisites().contains(course.getCode())) {
				count++;
			}
		}
		return count;
	}
	
	public static List<Course> generateSemester(List<String> completedCourses, List<Course> allCourses, int maxUnits){
		List<Course> selectedCourses = new ArrayList<>();
		List<Course> availableCourses = new ArrayList<>();
		int totalUnits = 0;
		
		//collect available courses 
		for(Course c : allCourses) {
			if(completedCourses.contains(c.getCode())) {
				continue;
			}
			
			boolean canTake = PrerequisiteChecker.canTakeCourse(c, completedCourses);
			if(canTake) {
				availableCourses.add(c);
			}
			
		}
		
		//sort by priority
		availableCourses.sort((a,b) -> {
			int aCount = countDependents(a, allCourses);
			int bCount = countDependents(b, allCourses);
			return Integer.compare(bCount,aCount);
		});
		
		
		
		//can take courses, v2  
		for(Course course: availableCourses) {
			if(totalUnits + course.getUnits() <= maxUnits) {
				selectedCourses.add(course);
				totalUnits += course.getUnits();
			}
			
		}
		
		return selectedCourses;
	}
	
}
