package com.degreepath.degreepath;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Queue;
import java.util.LinkedList;

public class DependencyGraph {
	
	//course code -> Course object
	private Map<String, Course> courses = new HashMap<>();
	//course code -> codes of the courses that need it as a prereq (arrow goes from the prereq to the course it unlocks)
	private Map<String, List<String>> dependents = new HashMap<>();
	
	public DependencyGraph(List<Course> allCourses) {
		for(Course c : allCourses) {
			courses.put(c.getCode(), c);
		}
		
		//build the arrows, skip courses with no prereqs list
		for(Course c : allCourses) {
			List<String> prereqs = c.getPrerequisites();
			if(prereqs == null) {
				continue;
			}
			for(String prereq : prereqs) {
				if(!dependents.containsKey(prereq)) {
					dependents.put(prereq, new ArrayList<>());
				}
				dependents.get(prereq).add(c.getCode());
			}
		}
	}
	
	//courses that need this one, empty list if none
	private List<String> getDependents(String code) {
		List<String> list = dependents.get(code);
		if(list == null) {
			return new ArrayList<>();
		}
		return list;
	}
	
	//BFS: start at a course and walk outward, count every course it unlocks (direct + indirect)
	public int countUnlocked(String code) {
		Set<String> seen = new HashSet<>();
		Queue<String> queue = new LinkedList<>();
		queue.add(code);
		
		while(!queue.isEmpty()) {
			String current = queue.poll();
			for(String next : getDependents(current)) {
				//only visit each course once
				if(!seen.contains(next)) {
					seen.add(next);
					queue.add(next);
				}
			}
		}
		return seen.size();
	}
	
	//prereq codes that aren't a course in the data (could be a typo in courses.json, or a course that just isn't in the list)
	public List<String> findUnknownPrereqs() {
		Set<String> unknown = new TreeSet<>();
		for(Course c : courses.values()) {
			if(c.getPrerequisites() == null) {
				continue;
			}
			for(String prereq : c.getPrerequisites()) {
				if(!courses.containsKey(prereq)) {
					unknown.add(prereq);
				}
			}
		}
		return new ArrayList<>(unknown);
	}
	
	//cycle detection (Kahn's algorithm): keep removing courses that have no prereqs left waiting
	//if some courses never get removed they're stuck in a cycle (A needs B, B needs A)
	public boolean hasCycle() {
		//how many prereqs each course is still waiting on
		Map<String, Integer> waiting = new HashMap<>();
		for(Course c : courses.values()) {
			int count = 0;
			if(c.getPrerequisites() != null) {
				for(String prereq : c.getPrerequisites()) {
					//ignore prereqs that aren't in the data, nothing to wait on
					if(courses.containsKey(prereq)) {
						count++;
					}
				}
			}
			waiting.put(c.getCode(), count);
		}
		
		//start with courses that aren't waiting on anything
		Queue<String> ready = new LinkedList<>();
		for(String code : waiting.keySet()) {
			if(waiting.get(code) == 0) {
				ready.add(code);
			}
		}
		
		int removed = 0;
		while(!ready.isEmpty()) {
			String code = ready.poll();
			removed++;
			//this course is "done", so everything that needs it waits on one less
			for(String next : getDependents(code)) {
				waiting.put(next, waiting.get(next) - 1);
				if(waiting.get(next) == 0) {
					ready.add(next);
				}
			}
		}
		
		return removed != courses.size();
	}
}
