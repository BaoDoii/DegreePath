package com.degreepath.degreepath;

import java.util.List;

public class Course {
	private String code;
	private String name;
	private int units;
	private String prerequisites[];
	private String minGrade;
	private String category;
	private String workload;
	private String terms;
	
	//getters
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getUnits() {
		return this.units;
	}
	
	public String[] getPrerequisites() {
		
		String prereq[] = null;
		for(int i = 0; i < prerequisites.length; i++ ) {
			prereq[i] += prerequisites[i];
		}
		
		return prereq;
	}
	
	public String getMinGrade() {
		return this.minGrade;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getWorkLoad() {
		return this.workload;
	}
	
	public String[] getTerms() {
		String termsList[] = null;
		for(int i = 0; i < terms.length(); i++ ) {
			termsList[i] += terms[i];
		}
		
		return termsList;
	}
	
	//setters
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUnits(int units) {
		this.units = units;
	}
	
	public void setPrerequisites(String[] prerequisites) {

		this.prerequisites = prerequisites;
	}
	
	public void setMinGrade(String minGrade) {
		this.minGrade = minGrade;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setWorkload(String workload) {
		this.workload = workload;
	}
	
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	//toString
}
