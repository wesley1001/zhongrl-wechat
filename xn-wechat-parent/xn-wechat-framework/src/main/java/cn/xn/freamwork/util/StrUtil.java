package cn.xn.freamwork.util;


public class StrUtil {

	public static String fuzzyBankCard(String card) {
		if (card == null) {
			return "";
		}
		char[] cardChar = card.toCharArray();

		char[] cardCharNew = new char[cardChar.length];

		for (int i = 0; i < cardChar.length; i++) {

			if (i < 3 || i >= cardChar.length - 4) {
				cardCharNew[i] = cardChar[i];
			}
			else {
				cardCharNew[i] = '*';
			}
		}
		return new String(cardCharNew);
	}


	public static String fuzzyMobile(String mobile) {
		return fuzzyBankCard(mobile);

	}


	public static String fuzzy(String card) {
		return fuzzy(card, 1, 0);
	}

	public static String fuzzyName(String card) {
		return fuzzy(card, 0, 1);
	}

	public static String fuzzy(String card, int before, int after) {
		if (card == null) {
			return "";
		}
		char[] cardChar = card.toCharArray();
		if (cardChar.length <= before + after) {
			return card;
		}

		else if (before < cardChar.length / 2) {
			before = cardChar.length / 2;
		}

		char[] cardCharNew = new char[cardChar.length];

		for (int i = 0; i < cardChar.length; i++) {

			if (i < before || i >= cardChar.length - after) {
				cardCharNew[i] = cardChar[i];
			}
			else {
				cardCharNew[i] = '*';
			}
		}
		return new String(cardCharNew);
	}

	/**
	 * 把页面转换成为当前访问的Offset
	 *
	 * @param page
	 * @return
	 */
	public static int page2Offset(int page, int size) {
		if (size <= 1) {
			size = SIZE_DEFAULT;
		}
		if (page <= 0) {
			page = 1;
		}
		else {
			page = page - 1;
		}
		return page * size;
	}

	public static final int SIZE_DEFAULT = 10;

	public static int page2Offset(int page) {
		return page2Offset(page, SIZE_DEFAULT);
	}

	/**
	 * 创建指定数量的随机字符串
	 *
	 * @param numberFlag 是否是数字
	 * @param length
	 * @return
	 */
	public static String genSmsString(boolean numberFlag, int length) {
		String retStr;
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	public static String genSmsString(int length) {
		  return genSmsString(true,length);
	}

	public static String genSmsString() {
		  return genSmsString(true,6);
	}

    /**获取卡位数*/
	public static String getTailnumber(String bankCardNo, int length) {
		if (bankCardNo == null || bankCardNo.length() < length) return "";
		return bankCardNo.substring(bankCardNo.length() - length,bankCardNo.length());
	}

	public static void main(String[] args) {

	}
}
