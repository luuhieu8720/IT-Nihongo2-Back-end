package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.models.Post;

@Document(collection = "post")
public class PostEntity {

	@Id
	private String id;

	private String idUser;

	private String title;

	private String details;

	private Integer salary;

	private String course;

	private String time;

	private String day;

	private String city;

	private String district;

	private String ward;

	private Boolean invalid;

	private Gender gender;

	public PostEntity() {
	}

	public PostEntity(String id, String idUser, String title, String details, Integer salary, String course,
			String time, String day, String city, String district, String ward, Boolean invalid, Gender gender) {
		this.id = id;
		this.idUser = idUser;
		this.title = title;
		this.details = details;
		this.salary = salary;
		this.course = course;
		this.time = time;
		this.day = day;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.invalid = invalid;
		this.gender = gender;
	}

	public PostEntity(Post post) {
		this.title = post.getTitle();
		this.details = post.getDetails();
		this.salary = post.getSalary();
		this.course = post.getCourse();
		this.time = post.getTime();
		this.day = post.getDay();
		this.city = post.getCity();
		this.district = post.getDistrict();
		this.ward = post.getWard();
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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
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
