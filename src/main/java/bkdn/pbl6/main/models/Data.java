package bkdn.pbl6.main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.DataEntity;
import bkdn.pbl6.main.enums.Gender;
import bkdn.pbl6.main.enums.Role;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private String username;

	private String email;

	private String name;

	private String telephone;

	private String address;

	private Gender gender;

	private String avatar;

	private String specialty;

	private String degree;

	private String studentId;

	private Role role;

	public Data() {
	}

	public Data(String username, String email, String name, String telephone, String address, Gender gender,
			String avatar, String specialty, String degree, String studentId, Role role) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.telephone = telephone;
		this.address = address;
		this.gender = gender;
		this.avatar = avatar;
		this.specialty = specialty;
		this.degree = degree;
		this.studentId = studentId;
		this.role = role;
	}

	public Data(AccountEntity accountEntity, DataEntity dataEntity) {
		insertData(accountEntity);
		insertData(dataEntity);
	}

	public Data(AccountEntity accountEntity) {
		insertData(accountEntity);
	}

	public Data(DataEntity dataEntity) {
		insertData(dataEntity);
	}

	public void insertData(AccountEntity accountEntity) {
		this.username = accountEntity.getUsername();
		this.email = accountEntity.getEmail();
		this.name = accountEntity.getName();
		this.role = accountEntity.getRole();
	}

	public void insertData(DataEntity dataEntity) {
		this.telephone = dataEntity.getTelephone();
		this.address = dataEntity.getAddress();
		this.gender = dataEntity.getGender();
		this.avatar = dataEntity.getAvatar();
		this.specialty = dataEntity.getSpecialty();
		this.degree = dataEntity.getDegree();
		this.studentId = dataEntity.getStudentId();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
