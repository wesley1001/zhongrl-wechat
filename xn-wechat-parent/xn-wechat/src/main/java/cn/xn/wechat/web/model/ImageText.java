package cn.xn.wechat.web.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class ImageText extends MediaIdMessage{

	@XStreamImplicit
	private List<Item> itmes;

	public List<Item> getItmes() {
		return itmes;
	}

	public void setItmes(List<Item> itmes) {
		this.itmes = itmes;
	}

	@Override
	public String toString() {
		return "ImageText [itmes=" + itmes + "]";
	}

}
