package cn.xn.wechat.customer.service;

/**
 *  客服工具类
 * @author rod zhong 
 *
 */
public class CustomerConstant {

	public static String CLIENTID = "wx7193cd3aad46ab2c";//"wx40d5184e05d61899";// 请替换为公众号对应的数据,AppID(应用ID)
	public static String CLIENTSECRET = "355c8666b6fd043f110d3f9fa89c3c99";//"1c47e8f6d1a63b6e0fdcfa985792d51c";// 请替换为公众号对应的数据
	public static String TOKEN = "8ZN3zEee8DgxoMpp";//"8ZN3zEee8DgxoMpp";// Token(令牌),在微信公众平台中设置
	public static String ENCODINGAESKEY = "h4QLqSTrA8cadSKx69bjUwEv0J8vdit6UrFWmIGBmFR";//"lPQxpivgF2JcQtDACCrJdfPaWiA1izBDgGrk3ii9sfM";// EncodingAESKey(消息加解密密钥),在微信公众平台中设置
	public static String OPENID = "gh_52fda9a443d6";//"gh_37405f259871";//微信公众号原始ID
	
	
	public static String ORGNAME = "xiaoniuqianguanzi";//在环信IM后台注册的org名称
	public static String APPNAME = "xiaoniuqianguanzi";//在环信IM后台注册的app名称
	public static String EASEMOB_CLIENTID = "YXA6_1bQYHF1EeWqYIfpXhNV2A";//环信IM后台对应的clientid
	public static String EASEMOB_CLIENTSECRET = "YXA63V8l60kD-wlycKeAp9S5uP9J1Yo";//环信IM后台对应的clientSecret
	
	public static String TOUSER = "xnqgz_wechat";//用于设置关联的用户
	public static String FROMUSER = "xnqgz-send-msg";//用于发送微信消息的指定用户，该用户【需要告知环信】做后台处理
	public static String DOMAIN = "a1.easemob.com";
	
	public static String SENDMESSAGEURL = "http://"+DOMAIN+"/"+ORGNAME+"/"+APPNAME+"/messages";//如果是vip用户，该url需要替换
	public static String CHATFILEURL = "http://"+DOMAIN+"/"+ORGNAME+"/"+APPNAME+"/chatfiles/";
	
	public static String HXSECRET = "123456";//用于验证环信回调消息的签名
	public static String SECRET = "654321";//用于验证环信回调消息的签名
	
    public static String DOWNLOADFILEPATH = "/data/apps/log/kefu/";//下载的临时文件存放的目录
    
}
