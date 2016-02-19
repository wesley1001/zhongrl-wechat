package cn.xn.wechat.web.model;

import java.io.Serializable;


public class BiScanCode implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 816531046221105591L;

	private int Id;
	
	private String qrCodeName;
	
	private int number;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getQrCodeName() {
		return qrCodeName;
	}

	public void setQrCodeName(String qrCodeName) {
		this.qrCodeName = qrCodeName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "BiScanCode [Id=" + Id + ", qrCodeName=" + qrCodeName
				+ ", number=" + number + "]";
	}
	
	
	
}
