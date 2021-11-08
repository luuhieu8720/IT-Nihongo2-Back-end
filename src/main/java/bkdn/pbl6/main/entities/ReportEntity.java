package bkdn.pbl6.main.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "report")
public class ReportEntity {

	@Id
	private String id;

	private String idUser;

	private String idTutor;

	private Integer score;

	private String comment;

	public ReportEntity() {
	}

	public ReportEntity(String id, String idUser, String idTutor, Integer score, String comment) {
		this.id = id;
		this.idUser = idUser;
		this.idTutor = idTutor;
		this.score = score;
		this.comment = comment;
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

	public String getIdTutor() {
		return idTutor;
	}

	public void setIdTutor(String idTutor) {
		this.idTutor = idTutor;
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
