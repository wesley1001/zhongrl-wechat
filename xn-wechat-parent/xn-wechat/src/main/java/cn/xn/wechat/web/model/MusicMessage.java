package cn.xn.wechat.web.model;

import cn.xn.wechat.web.intface.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Music") 
public class MusicMessage extends MediaIdMessage{

	@XStreamAlias("Title")  
	@XStreamCDATA 
	private String Title;
	@XStreamAlias("Description")  
	@XStreamCDATA 
	private String Description;
	@XStreamAlias("MusicUrl")  
	@XStreamCDATA 
	private String MusicUrl;
	
	private String HQMusicUrl;
	
	private String ThumbMediaId;
	
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
	public String getMusicUrl() {
		return MusicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	
	
	
}
