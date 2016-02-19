package cn.xn.wechat.activity.exception;
/**
 * 兑换产品异常
 */
public class ChangePrizeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	private String msg;

	public ChangePrizeException(Throwable cause) {
		super(cause);
	}

	public ChangePrizeException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public ChangePrizeException(int code, String msg) {
		this(msg);
		this.code = code;
	}

	public ChangePrizeException(int code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.msg = msg;
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

}
