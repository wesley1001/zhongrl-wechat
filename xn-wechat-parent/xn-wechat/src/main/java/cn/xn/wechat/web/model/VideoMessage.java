package cn.xn.wechat.web.model;

import cn.xn.wechat.web.intface.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Video") 
public class VideoMessage extends MediaIdMessage{

	@XStreamAlias("Title")  
	@XStreamCDATA 
	private String Title;
	
	@XStreamAlias("Description")  
	@XStreamCDATA 
	private String Description;

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
	 
	 
}
