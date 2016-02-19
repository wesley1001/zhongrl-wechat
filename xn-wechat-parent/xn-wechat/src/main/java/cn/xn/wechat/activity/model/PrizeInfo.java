package cn.xn.wechat.activity.model;

import java.util.Date;

/**
 * 奖品信息
 */
public class PrizeInfo {

	private String prizeId;
	/**
	 * 奖品名称
	 */
	private String prizeName;
	/**
	 * 产品总数
	 */
	private Integer buyTotal;
	/**
	 * 兑换所需原料
	 */
	private Integer buyMaterial;
	/**
	 * 已兑换总数
	 */
	private Integer buyedTotal;
	/**
	 * 图片地址
	 */
	private String imageUrl;
	private Date createDate;
	private Date updateDate;
	private Integer remaNum;
	/**
	 * 产品状态(1=待审核(数据初始化)|2=审核通过(上架)|3=已删除
	 */
	private int status;
	/**
	 * 产品类型
	 */
	private String typeId;
	/**
	 * 奖品描述
	 */
	private String prizeDesc;
	/**
	 * 1实物物品，2虚拟物品
	 */
	private int isVirtual;
	/**
	 * 用户当前原料
	 */
	private int userMaterial;

	/**
	 * 1不可制作，2可以制作
	 */
	private int canEhange = 1;

	public int getCanEhange() {
		return canEhange;
	}

	public void setCanEhange(int canEhange) {
		this.canEhange = canEhange;
	}

	public void setRemaNum(Integer remaNum) {
		this.remaNum = remaNum;
	}

	public int getUserMaterial() {
		return userMaterial;
	}

	public void setUserMaterial(int userMaterial) {
		this.userMaterial = userMaterial;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getBuyTotal() {
		return buyTotal;
	}

	public void setBuyTotal(Integer buyTotal) {
		this.buyTotal = buyTotal;
	}

	public Integer getBuyMaterial() {
		return buyMaterial;
	}

	public void setBuyMaterial(Integer buyMaterial) {
		this.buyMaterial = buyMaterial;
	}

	public Integer getBuyedTotal() {
		return buyedTotal;
	}

	public void setBuyedTotal(Integer buyedTotal) {
		this.buyedTotal = buyedTotal;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getPrizeDesc() {
		return prizeDesc;
	}

	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}

	public int getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(int isVirtual) {
		this.isVirtual = isVirtual;
	}

	/**
	 * 产品剩余个数
	 */
	public Integer getRemaNum() {
		if (buyedTotal == null) {
			buyedTotal = 0;
		}
		this.remaNum = buyTotal - buyedTotal;
		return this.remaNum;
	}

}
