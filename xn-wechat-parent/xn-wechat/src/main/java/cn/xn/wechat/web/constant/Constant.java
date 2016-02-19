package cn.xn.wechat.web.constant;

public class Constant {

	public static final String ACTIVITY_TYPE = "GOODSOUND";//活动类型
	
	public static final String SING_NAME_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//TODO 开发环境wx40d5184e05d61899,生产环境wx7193cd3aad46ab2c
//	public static final String APP_ID = "wx40d5184e05d61899";//"wx7193cd3aad46ab2c";//"wx40d5184e05d61899";
	//TODO 开发环境1c47e8f6d1a63b6e0fdcfa985792d51c,生产环境355c8666b6fd043f110d3f9fa89c3c99
//	public static final String APP_SECRET = "1c47e8f6d1a63b6e0fdcfa985792d51c";//"355c8666b6fd043f110d3f9fa89c3c99";//"1c47e8f6d1a63b6e0fdcfa985792d51c";
	//TODO 开发环境QGZ,生产环境XNQGZ
//	public static final String WECHAT_NAME = "QGZ"; //XNQGZ //TODO
	
	public static final String CLIENT_CREDENTIAL = "client_credential"; // 微信获取token 
	
	public static final String WECHAT_TYPE = "jsapi"; // 微信的js签名
	
	public static final String JSAPI_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"; // 微信的js签名请求地址
	
	public static final String WX_OAUTH = "https://open.weixin.qq.com/connect/oauth2/authorize"; //授权地址
	
	public static String REDIRECT_URI = "111";//授权后回调的URL 
	
	public static String QR_CODE="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";//生成二维码的接口
	
	public static String ACTION_NAME = "QR_LIMIT_STR_SCENE";//永久二维码标识
	
	public static String TEMPORARY_ACTION_NAME = "QR_SCENE";//临时二维码标识
	
	public static String GET_TICKET_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
	
	public static final String TOKEN = "8ZN3zEee8DgxoMpp";//服务器配置的token 8ZN3zEee8DgxoMpp
	
	public static String WX_MENU ="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";//创建菜单
	
	public static String DELETE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";//删除菜单
	
	public static String  PERMANENT_MATERIAL= "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";//新增永久素材
	
	public static String USER_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";//网页授权
	
	public static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?";//刷新token
	
	public static String GET_USER_URL = "https://api.weixin.qq.com/sns/userinfo?"; //拉取用户信息
	
//	public static final String MESSAGE = "萌萌的罐主，欢迎关注小牛钱罐子官方微信。8月17-23日，【大福利】小牛钱罐子iwatch任性送！点击下方菜单【我要理财】即可快速注册/登陆o((>ω< ))o↓";
	//public static final String MESSAGE = "各位罐主，请注意！小牛钱罐子新的官方微信号：xn-qianguanzi，微信名称：小牛钱罐子；请在微信添加朋友中搜索“小牛钱罐子”添加关注新的官方微信。";
	
//	public static final String MESSAGE = "萌萌哒罐主，欢迎关注小牛钱罐子！小牛钱罐子微信端正式上线，点击下方菜单栏【N多理财】快速注册/登陆o((>ω< ))o↓↓↓【I爱福利】官方最新活动，福利好得不得了如有任何疑，欢迎勾搭【U有服务】/示爱。";
	
	public static String MATERIAL_URL  = "https://api.weixin.qq.com/cgi-bin/material/batchget_material";//获取素材的连接
	
	public static String SHORT_LINK = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=";
	//发送模板消息
	public static String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	//获取模板ID
	public static String GET_TEMPLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";

	public static String GET_WEIXIN_OPENID = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";
	
	public static String GET_WECHAT_USER = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN"; //根据OPENID 获取用户信息
	
	public static String WECHAT_USER_INTFACES = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";//拉取用户信息
	
	public static String PAYMENT_BID="WECHATPAYMENT";
	
	public static String PAYMENT_KEY="WECHATPAYMENTMESSAGE";
	
	public static int PAYMENT_EXPIRE = 60 * 22;
	
	public static String UPLOAD_IMAGE_URL= "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token={0}&type={1}";
	
}
