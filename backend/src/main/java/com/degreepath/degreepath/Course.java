package com.degreepath.degreepath;

import java.util.List;

public class Course {
	//fields to match JSON structure
	private String code;
	private String name;
	private int units;
	private List<String> prerequisites;
	private String minGrade;
	private String category;
	private String workload;
	private List<String> terms;
	
	//constructor
	public Course() {}
	
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
	
	public List<String> getPrerequisites() {
		
		return this.prerequisites;
	}
	
	public String getMinGrade() {
		return this.minGrade;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getWorkload() {
		return this.workload;
	}
	
	public List<String> getTerms() {
		return this.terms;
	}
	
	//setters
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUnits(int units) {
		if(units < 1) {
			throw new IllegalArgumentException("Units can't be 0 or less...");
		}
		this.units = units;
	}
	
	public void setPrerequisites(List<String> prerequisites) {

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
	
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
	
	//toString
	public String toString() {
		return code + " - " + name + " (" + units + " units)";
	}
}
