package cn.xn.wechat.activity.json;

public class JsonMessage {

	private int code;
	private String msg;
	private Object data;

	public JsonMessage(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JsonMessage() {

	}

	public JsonMessage(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static JsonMessage getSuccessJson(Object data) {
		return new JsonMessage(0, "成功", data);
	}

	public static JsonMessage getErrorJson(String msg) {
		return new JsonMessage(-1, msg);
	}

	public static JsonMessage getErrorJson(int code,String msg) {
		return new JsonMessage(code, msg);
	}
}
