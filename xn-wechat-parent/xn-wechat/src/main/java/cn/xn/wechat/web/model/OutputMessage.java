package cn.xn.wechat.web.model;

import cn.xn.wechat.web.intface.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
 *  
 * @author morning 
 * @date 2015年2月16日 下午2:29:32 
 */  
@XStreamAlias("xml")  
public class OutputMessage {  
  
    @XStreamAlias("ToUserName")  
    @XStreamCDATA  
    private String ToUserName;  
  
    @XStreamAlias("FromUserName")  
    @XStreamCDATA  
    private String FromUserName;  
  
    @XStreamAlias("CreateTime")  
    private Long CreateTime;  
  
    @XStreamAlias("MsgType")  
    @XStreamCDATA  
    private String MsgType = "text";  
  
    private ImageMessage Image;  
    
    private VoiceMessage Voice;  
    
    private VideoMessage Video;
    
    private MusicMessage Music;
    
    @XStreamAlias("Articles")
    @XStreamCDATA  
    private ImageText Articles;
    
    @XStreamAlias("ArticleCount")
    private int ArticleCount;
    
    @XStreamAlias("KfAccount")
    private String KfAccount;
    
    public String getKfAccount() {
		return KfAccount;
	}

	public void setKfAccount(String kfAccount) {
		KfAccount = kfAccount;
	}

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public ImageText getArticles() {
		return Articles;
	}

	public void setArticles(ImageText articles) {
		Articles = articles;
	}

	public MusicMessage getMusic() {
		return Music;
	}

	public void setMusic(MusicMessage music) {
		Music = music;
	}

	public VideoMessage getVideo() {
		return Video;
	}

	public void setVideo(VideoMessage video) {
		Video = video;
	}

	public VoiceMessage getVoice() {
		return Voice;
	}

	public void setVoice(VoiceMessage voice) {
		Voice = voice;
	}

	public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public Long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(Long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
  
    public ImageMessage getImage() {  
        return Image;  
    }  
  
    public void setImage(ImageMessage image) {  
        Image = image;  
    }

	@Override
	public String toString() {
		return "OutputMessage [ToUserName=" + ToUserName + ", FromUserName="
				+ FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
				+ MsgType + ", Image=" + Image + ", Voice=" + Voice
				+ ", Video=" + Video + ", Music=" + Music + ", Articles="
				+ Articles + ", ArticleCount=" + ArticleCount + "]";
	}  
    
}