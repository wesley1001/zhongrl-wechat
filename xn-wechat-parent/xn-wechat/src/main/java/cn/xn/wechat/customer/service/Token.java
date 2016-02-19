package cn.xn.wechat.customer.service;



public class Token {
    public Token(String accessToken, Long expire) {
        this.accessToken = accessToken;
        this.expire = expire;
    }
	String accessToken;
	Long expire;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Long getExpire() {
		return expire;
	}
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	
    public boolean isExpired() {
        return System.currentTimeMillis() > expire;
    }
}
