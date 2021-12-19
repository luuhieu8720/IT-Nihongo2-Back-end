package bkdn.pbl6.main.models;

import bkdn.pbl6.main.entities.ReportEntity;

public class Report {

	private String id;

	private String userName;

	private String tutorName;

	private Integer score;

	private String comment;

	public Report() {
	}

	public Report(String id, String userName, String tutorName, Integer score, String comment) {
		this.id = id;
		this.userName = userName;
		this.tutorName = tutorName;
		this.score = score;
		this.comment = comment;
	}

	public Report(ReportEntity reportEntity) {
		insert(reportEntity);
	}

	public void insert(ReportEntity reportEntity) {
		this.id = reportEntity.getId();
		this.score = reportEntity.getScore();
		this.comment = reportEntity.getComment();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
