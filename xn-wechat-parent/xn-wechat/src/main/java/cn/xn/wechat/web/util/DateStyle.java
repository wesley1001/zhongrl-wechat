package cn.xn.wechat.web.util;

public enum DateStyle {

	YYYY_MM("yyyy-MM", false),  
    YYYY_MM_DD("yyyy-MM-dd", false),  
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),  
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),  
      
    YYYY_MM_EN("yyyy/MM", false),  
    YYYY_MM_DD_EN("yyyy/MM/dd", false),  
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),  
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),  
      
    YYYY_MM_CN("yyyy年MM月", false),  
    YYYY_MM_DD_CN("yyyy年MM月dd日", false), 
    YYYY_MM_DD_CN_HH_MM("yyyy年MM月dd日 HH:mm", false), 
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),  
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false), 
    
    
    MM_DD_HH_SS("MM月dd日 HH:mm", true),
      
    HH_MM("HH:mm", true),  
    HH_MM_SS("HH:mm:ss", true),  
      
    MM_DD("MM-dd", true),  
    MM_DD_HH_MM("MM-dd HH:mm", true),  
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),  
      
    MM_DD_EN("MM/dd", true),  
    MM_DD_HH_MM_EN("MM/dd HH:mm", true),  
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),  
      
    MM_DD_CN("MM月dd日", true),  
    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),  
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true),
    YY_MM_DD_HH_MM_SS_("YY年MM月 -dd HH.mm.ss.S",true),
    
    YYYYMMDDHHMMSS("yyyyMMddHHmmss",true),
    
    YYYYMMDD("yyyyMMdd",true);
      
    private String value;  
      
    private boolean isShowOnly;  
      
    DateStyle(String value, boolean isShowOnly) {  
        this.value = value;  
        this.isShowOnly = isShowOnly;  
    }  
      
    public String getValue() {  
        return value;  
    }  
      
    public boolean isShowOnly() {  
        return isShowOnly;  
    }  
}
