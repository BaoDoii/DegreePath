package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;

public class SemesterPlanner {
	
	public static List<Course> generateSemester(List<String> completedCourses, List<Course> allCourses, int maxUnits){
		List<String> selectedCourses = null;
		int totalUnits = 0;
		
		//printing out completed courses and heading
		System.out.println("=== Semester Plan Test===");
		System.out.println("\nCompleted Courses: [");
		for(String completed: completedCourses) {
			System.out.println(completed + " ");
		}
		System.out.println("]\nMax Units: "+ maxUnits);
		
		//can take courses, not guided yet but just fixed to add up to max Units for now v1
		System.out.println("\nCan take:");
		for(Course course: allCourses) {
			boolean canTake = PrerequisiteChecker.canTakeCourse(course, completedCourses);
			if(canTake) {
				if(totalUnits < maxUnits) {
				System.out.println("\n- "+ course.getCode() + " (" + course.getUnits() + " Units)");
				selectedCourses.add(course.getName());
				totalUnits += course.getUnits();
				}
			}
		}
		
		//cant take courses
		System.out.println("\n\n Cannot Take:");
		for(Course course: allCourses) {
			boolean canTake = PrerequisiteChecker.canTakeCourse(course, completedCourses);
			if(!canTake) {
				System.out.println("\n- " + course.getName() + " (Needs " + course.getPrerequisites() +" first");
			}
		}
		
		return new ArrayList<>();
	}
}
