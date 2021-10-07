package bkdn.pbl6.main.models;

public class ApiResponse {

	private Boolean success;
	private Object value;

	public ApiResponse(Boolean succes) {
		this(succes, null);
	}

	public ApiResponse(Boolean success, Object value) {
		this.success = success;
		this.value = value;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
