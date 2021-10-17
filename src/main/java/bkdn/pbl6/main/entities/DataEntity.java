package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bkdn.pbl6.main.models.Data;

@Document(collection = "data")
public class DataEntity {

	@Id
	private String id;

	private String telephone;

	private String address;

	private Boolean male;

	private String avatar;

	private String office;

	private String specialty;

	private String degree;

	private String studentId;

	public DataEntity() {
	}

	public DataEntity(String id, String telephone, String address, Boolean male, String avatar, String office,
			String specialty, String degree, String studentId) {
		this.id = id;
		this.telephone = telephone;
		this.address = address;
		this.male = male;
		this.avatar = avatar;
		this.office = office;
		this.specialty = specialty;
		this.degree = degree;
		this.studentId = studentId;
	}

	public DataEntity(Data data) {
		this.telephone = data.getTelephone();
		this.address = data.getAddress();
		this.male = data.getMale();
		this.avatar = data.getAvatar();
		this.office = data.getOffice();
		this.specialty = data.getSpecialty();
		this.degree = data.getDegree();
		this.studentId = data.getStudentId();
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

	public Boolean getMale() {
		return male;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
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

}
