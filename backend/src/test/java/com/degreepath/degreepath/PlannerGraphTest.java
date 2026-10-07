package com.degreepath.degreepath;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlannerGraphTest {
	
	//helper to make a course quickly
	private Course course(String code, int units, String... prereqs) {
		Course c = new Course();
		c.setCode(code);
		c.setName(code);
		c.setUnits(units);
		c.setPrerequisites(new ArrayList<>(Arrays.asList(prereqs)));
		return c;
	}
	
	@Test
	void countsIndirectUnlocks() {
		//A -> B -> C, so A unlocks 2 courses, B unlocks 1, C unlocks 0
		DependencyGraph g = new DependencyGraph(List.of(course("A", 3), course("B", 3, "A"), course("C", 3, "B")));
		assertEquals(2, g.countUnlocked("A"));
		assertEquals(1, g.countUnlocked("B"));
		assertEquals(0, g.countUnlocked("C"));
	}
	
	@Test
	void countsSharedCourseOnce() {
		//D needs both B and C, A unlocks B and C, which both lead to D (D should only count once)
		DependencyGraph g = new DependencyGraph(List.of(course("A", 3), course("B", 3, "A"),
				course("C", 3, "A"), course("D", 3, "B", "C")));
		assertEquals(3, g.countUnlocked("A"));
	}
	
	@Test
	void detectsCycles() {
		assertTrue(new DependencyGraph(List.of(course("A", 3, "B"), course("B", 3, "A"))).hasCycle());
		//cycle hiding next to an unrelated course
		assertTrue(new DependencyGraph(List.of(course("X", 3), course("A", 3, "C"),
				course("B", 3, "A"), course("C", 3, "B"))).hasCycle());
	}
	
	@Test
	void selfPrereqIsACycle() {
		assertTrue(new DependencyGraph(List.of(course("A", 3, "A"))).hasCycle());
	}
	
	@Test
	void diamondIsNotACycle() {
		DependencyGraph g = new DependencyGraph(List.of(course("A", 3), course("B", 3, "A"),
				course("C", 3, "A"), course("D", 3, "B", "C")));
		assertFalse(g.hasCycle());
	}
	
	@Test
	void findsUnknownPrereqs() {
		assertEquals(List.of("TYPO"), new DependencyGraph(List.of(course("A", 3, "TYPO"))).findUnknownPrereqs());
		assertTrue(new DependencyGraph(List.of(course("A", 3), course("B", 3, "A"))).findUnknownPrereqs().isEmpty());
	}
	
	@Test
	void nullPrereqsDontCrash() {
		Course c = new Course();
		c.setCode("A");
		c.setUnits(3);
		//prerequisites left as null on purpose
		DependencyGraph g = new DependencyGraph(List.of(c));
		assertEquals(0, g.countUnlocked("A"));
		assertFalse(g.hasCycle());
	}
	
	@Test
	void picksCourseThatUnlocksTheMost() {
		//X is first in the list but unlocks nothing, A starts a chain
		List<Course> all = List.of(course("X", 3), course("A", 3), course("B", 3, "A"), course("C", 3, "B"));
		List<Course> plan = SemesterPlanner.generateSemester(new ArrayList<>(), all, 3, 0);
		assertEquals(1, plan.size());
		assertEquals("A", plan.get(0).getCode());
	}
	
	@Test
	void skipsCoursesWithUnmetPrereqs() {
		List<Course> all = List.of(course("A", 3), course("B", 3, "A"));
		List<Course> plan = SemesterPlanner.generateSemester(new ArrayList<>(), all, 12, 0);
		assertEquals(1, plan.size());
		assertEquals("A", plan.get(0).getCode());
	}
	
	@Test
	void addsGEsAndStaysUnderMaxUnits() {
		List<Course> all = List.of(course("A", 3), course("B", 3), course("C", 3));
		List<Course> plan = SemesterPlanner.generateSemester(new ArrayList<>(), all, 9, 1);
		int total = 0;
		for(Course c : plan) {
			total += c.getUnits();
		}
		assertTrue(total <= 9);
		//2 real courses + 1 GE
		assertEquals(3, plan.size());
		assertEquals("GE1", plan.get(2).getCode());
	}
	
	@Test
	void multiSemesterPlanIsValidAndComplete() {
		List<Course> curriculum = List.of(course("M1", 3), course("C1", 3), course("C2", 3, "C1", "M1"),
				course("C3", 3, "C2"), course("C4", 3, "C2"), course("C5", 3, "C3", "C4"));
		List<String> completed = new ArrayList<>();
		List<List<Course>> semesters = new ArrayList<>();
		//same loop /multiplan uses
		for(int i = 0; i < 5; i++) {
			List<Course> semester = SemesterPlanner.generateSemester(completed, curriculum, 6, 0);
			if(semester.size() == 0) {
				break;
			}
			semesters.add(semester);
			for(Course c : semester) {
				completed.add(c.getCode());
			}
		}
		assertTrue(PrerequisiteChecker.validatePlan(new ArrayList<>(), semesters).isEmpty());
		assertEquals(6, completed.size());
	}
	
	@Test
	void validatePlanFlagsPrereqTakenInSameSemester() {
		List<List<Course>> bad = List.of(List.of(course("B", 3, "A"), course("A", 3)));
		assertEquals(1, PrerequisiteChecker.validatePlan(new ArrayList<>(), bad).size());
	}
}
