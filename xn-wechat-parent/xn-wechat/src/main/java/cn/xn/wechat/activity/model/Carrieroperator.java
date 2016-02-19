package cn.xn.wechat.activity.model;


public enum Carrieroperator {
	
	DX("DX10","中国电信"),
	LT("LT50","中国联通"),
	YD("YD10","中国移动");
	
	 Carrieroperator(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	
	private String desc;
	
	public static String getDescByCode(String code){
		Carrieroperator[] str = Carrieroperator.values();
		for(Carrieroperator c :str ){
			if(code.equals(c.getCode())){
				return c.getDesc();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		 
	}
	
	public static void main(String[] args) {
		System.out.println(getDescByCode("DX10"));
	}
}
