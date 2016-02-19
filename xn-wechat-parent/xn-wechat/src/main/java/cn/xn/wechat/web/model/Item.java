package cn.xn.wechat.web.model;

import cn.xn.wechat.web.intface.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class Item {
	    
	 @XStreamAlias("Title")
	 @XStreamCDATA 
	 private String Title;
	 @XStreamAlias("Description")  
	 @XStreamCDATA 
	 private String Description;
	 @XStreamAlias("PicUrl")  
	 @XStreamCDATA 
	 private String PicUrl;
	 @XStreamAlias("Url")  
	 @XStreamCDATA 
	 private String Url;
	 
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	@Override
	public String toString() {
		return "item [Title=" + Title + ", Description=" + Description
				+ ", PicUrl=" + PicUrl + ", Url=" + Url + "]";
	}
	 
	 
}
