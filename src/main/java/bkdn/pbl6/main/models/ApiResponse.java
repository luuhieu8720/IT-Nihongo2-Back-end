package bkdn.pbl6.main.models;

public class ApiResponse {

	private String success;
	private Object value;

	public ApiResponse(String success, Object value) {
		super();
		this.success = success;
		this.value = value;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[" + (success != null ? "success=" + success + "," : "") + (value != null ? "value=" + value : "")
				+ "]";
	}

}
