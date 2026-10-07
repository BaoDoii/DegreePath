package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SemesterPlanner {
	
	public static List<Course> generateSemester(List<String> completedCourses, List<Course> allCourses, int maxUnits, int numGEs){
		List<Course> selectedCourses = new ArrayList<>();
		List<Course> availableCourses = new ArrayList<>();
		int totalUnits = 0;
		int getGEUnits = numGEs * 3;
		
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
		
		//build the prereq graph, then score each available course by how many courses it unlocks (direct + indirect)
		DependencyGraph graph = new DependencyGraph(allCourses);
		Map<String, Integer> unlockCounts = new HashMap<>();
		for(Course c : availableCourses) {
			unlockCounts.put(c.getCode(), graph.countUnlocked(c.getCode()));
		}
		
		//sort by priority
		availableCourses.sort((a,b) -> {
			int aCount = unlockCounts.get(a.getCode());
			int bCount = unlockCounts.get(b.getCode());
			return Integer.compare(bCount,aCount);
		});
		
		
		//can take courses, v2  
		for(Course course: availableCourses) {
			if(totalUnits + course.getUnits() <= maxUnits - getGEUnits) {
				selectedCourses.add(course);
				totalUnits += course.getUnits();
			}
			
		}
				
		for(int i = 0; i < numGEs; i++) {
			if(totalUnits + 3 <= maxUnits) {
				Course GECourse = new Course();
				GECourse.setCode("GE" + (i+1));
				GECourse.setName("GE" + (i+1));
				GECourse.setUnits(3);
				GECourse.setWorkload("Medium");
				GECourse.setCategory("General Education");
				GECourse.setPrerequisites(new ArrayList<>());
				
				selectedCourses.add(GECourse);
				totalUnits+= 3;
			}
		}
		
		return selectedCourses;
	}
	
}
