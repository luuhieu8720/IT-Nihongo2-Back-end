package bkdn.pbl6.main.entities;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.models.Post;
import bkdn.pbl6.main.models.Timetable;

@Document(collection = "post")
public class PostEntity {

	@Id
	private String id;

	private String idUser;

	private String title;

	private String details;

	private String salary;

	private String course;

	private ArrayList<Timetable> time;

	private String location;

	private Boolean invalid;

	private Gender gender;

	public PostEntity() {
	}

	public PostEntity(String id, String idUser, String title, String details, String salary, String course,
			ArrayList<Timetable> time, String location, Boolean invalid, Gender gender) {
		this.id = id;
		this.idUser = idUser;
		this.title = title;
		this.details = details;
		this.salary = salary;
		this.course = course;
		this.time = time;
		this.location = location;
		this.invalid = invalid;
		this.gender = gender;
	}

	public PostEntity(Post post) {
		this.title = post.getTitle();
		this.details = post.getDetails();
		this.salary = post.getSalary();
		this.course = post.getCourse();
		this.time = post.getTime();
		this.location = post.getLocation();
		this.invalid = post.getInvalid();
		this.gender = post.getGender();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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
