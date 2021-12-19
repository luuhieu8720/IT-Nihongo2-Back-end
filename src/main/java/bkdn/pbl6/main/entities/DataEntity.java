package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.models.Data;

@Document(collection = "data")
public class DataEntity {

	@Id
	private String id;

	private String telephone;

	private String address;

	private Gender gender;

	private String avatar;

	private String specialty;

	private String degree;

	private String studentId;

	private Integer age;

	private String dateOfBirth;

	private String experience;

	private String currentJob;

	private String teachingMethod;

	private String idAccount;

	public DataEntity() {
	}

	public DataEntity(String id, String telephone, String address, Gender gender, String avatar, String specialty,
			String degree, String studentId, Integer age, String dateOfBirth, String experience, String currentJob,
			String teachingMethod, String idAccount) {
		super();
		this.id = id;
		this.telephone = telephone;
		this.address = address;
		this.gender = gender;
		this.avatar = avatar;
		this.specialty = specialty;
		this.degree = degree;
		this.studentId = studentId;
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.experience = experience;
		this.currentJob = currentJob;
		this.teachingMethod = teachingMethod;
		this.idAccount = idAccount;
	}

	public DataEntity(Data data) {
		this.telephone = data.getTelephone();
		this.address = data.getAddress();
		this.gender = data.getGender();
		this.avatar = data.getAvatar();
		this.specialty = data.getSpecialty();
		this.degree = data.getDegree();
		this.studentId = data.getStudentId();
		this.age = data.getAge();
		this.dateOfBirth = data.getDateOfBirth();
		this.experience = data.getExperience();
		this.currentJob = data.getCurrentJob();
		this.teachingMethod = data.getTeachingMethod();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getTeachingMethod() {
		return teachingMethod;
	}

	public void setTeachingMethod(String teachingMethod) {
		this.teachingMethod = teachingMethod;
	}

	public String getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

}
