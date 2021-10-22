package bkdn.pbl6.main.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bkdn.pbl6.main.entities.PostEntity;
import bkdn.pbl6.main.enums.Gender;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

	private String id;

	private String username;

	private String title;

	private String details;

	private String salary;

	private String course;

	private ArrayList<Timetable> time;

	private String location;

	private Boolean invalid;

	private Gender gender;

	public Post() {
	}

	public Post(String id, String username, String title, String details, String salary, String course,
			ArrayList<Timetable> time, String location, Boolean invalid, Gender gender) {
		this.id = id;
		this.username = username;
		this.title = title;
		this.details = details;
		this.salary = salary;
		this.course = course;
		this.time = time;
		this.location = location;
		this.invalid = invalid;
		this.gender = gender;
	}

	public Post(PostEntity postEntity) {
		this.id = postEntity.getId();
		this.title = postEntity.getTitle();
		this.details = postEntity.getDetails();
		this.salary = postEntity.getSalary();
		this.course = postEntity.getCourse();
		this.time = postEntity.getTime();
		this.location = postEntity.getLocation();
		this.invalid = postEntity.getInvalid();
		this.gender = postEntity.getGender();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public ArrayList<Timetable> getTime() {
		return time;
	}

	public void setTime(ArrayList<Timetable> time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
