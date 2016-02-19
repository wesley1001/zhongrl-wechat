package cn.xn.wechat.web.util;

/**
 * 密码强度
 *
 * @version 1.0.0
 */

public class PasswordStrength {
	private String pwd;
	private int score;
	private int length;
	private int uppercase;
	private int lowercase;
	private int number;
	private int symbol;

	public PasswordStrength(String pwd) {
		if(pwd==null)
			throw new IllegalArgumentException();
		this.pwd = pwd;
		analysis();
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getUppercase() {
		return uppercase;
	}

	public void setUppercase(int uppercase) {
		this.uppercase = uppercase;
	}

	public int getLowercase() {
		return lowercase;
	}

	public void setLowercase(int lowercase) {
		this.lowercase = lowercase;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSymbol() {
		return symbol;
	}

	public void setSymbol(int symbol) {
		this.symbol = symbol;
	}

	public void analysis() {
		if (pwd == null) {
			return;
		}

		length = pwd.length();


		char[] length = pwd.toCharArray();

		for (char c : length) {
			if (c >= 'a' && c <= 'z') {
				lowercase++;
			}
			else if (c >= 'A' && c <= 'Z') {
				uppercase++;
			}
			else if (c >= '0' && c <= '9') {
				number++;
			}
			else {
				symbol++;
			}
		}


		score = lengthAnalysis() +
				letterAnalysis() +
				numberAnalysis() +
				symbolAnalysis() + awardAnalysis();

	}

	public int getScore() {
		return score;
	}

	public String getStrength() {
		if (score > 80) {
			return "high";
		}
		if (60 <= score && score <= 80) {
			return "middle";
		}
		return "low";
	}

	/**
	 * 四、符号:
	 * 0 分: 没有符号
	 * 10 分: 1 个符号
	 * 25 分: 大于 1 个符号
	 */
	private int symbolAnalysis() {
		if (symbol == 0) {
			return 0;
		}

		if (symbol == 1) {
			return 10;
		}

		return 25;

	}

	/**
	 * 密码分析
	 * 二、字母:
	 * 0 分: 没有字母
	 * 10 分: 全都是小（大）写字母
	 * 20 分: 大小写混合字母
	 *
	 * @return
	 */
	private int letterAnalysis() {
		if (uppercase + lowercase == 0) {
			return 0;
		}
		else {
			if (uppercase == 0 || lowercase == 0) {
				return 10;
			}
			return 20;
		}


	}

	/**
	 * 一、密码长度:
	 * 5 分: 小于等于 4 个字符
	 * 10 分: 5 到 7 字符
	 * 25 分: 大于等于 8 个字符
	 *
	 * @return
	 */
	private int lengthAnalysis() {
		if (length <= 4) {
			return 5;
		}
		else if (5 >= length && length <= 7) {
			return 10;
		}
		else {
			return 25;
		}

	}


	/**
	 * 三、数字:
	 * 0 分: 没有数字
	 * 10 分: 1 个数字
	 * 20 分: 大于等于 3 个数字
	 *
	 * @return
	 */
	private int numberAnalysis() {
		if (number <= 0) {
			return 0;
		}
		if (number >= 1 && number < 3) {
			return 10;
		}
		else {
			return 25;
		}

	}

	/**
	 * 五、奖励:
	 * 2 分: 字母和数字
	 * 3 分: 字母、数字和符号
	 * 5 分: 大小写字母、数字和符号
	 *
	 * @return
	 */
	private int awardAnalysis() {
		if (lowercase > 0 && uppercase > 0 && number > 0 && symbol > 0) {
			return 5;
		}
		if (lowercase + uppercase > 0 && number > 0 && symbol > 0) {
			return 3;
		}
		if (lowercase + uppercase > 0 && number > 0) {
			return 2;
		}
		return 0;
	}

    public String getStringScore() {
        return String.valueOf(score);
	}
}

