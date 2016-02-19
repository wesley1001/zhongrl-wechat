package cn.xn.wechat.activity.resp;

/**
 * 我的原料和剩余原料
 */
public class MaterialResp {

	private String userId;
	private Integer material;
	private Integer surplusMaterial;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMaterial() {
		return material;
	}

	public void setMaterial(Integer material) {
		this.material = material;
	}

	public Integer getSurplusMaterial() {
		return surplusMaterial;
	}

	public void setSurplusMaterial(Integer surplusMaterial) {
		this.surplusMaterial = surplusMaterial;
	}

}
