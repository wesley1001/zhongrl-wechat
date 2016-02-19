package cn.xn.wechat.activity.controller;

import javax.annotation.Resource;

import org.junit.Test;

import cn.xn.wechat.web.util.WechatUtil;

public class WechatTest extends TestBase{

	@Resource
	private WechatUtil wechatUtil;
	
	
	@Test
	public void test(){
		System.out.println("============");
		System.out.println(wechatUtil.createTemporaryQrCode(123,12333));
		System.out.println("---------------------");
	}
}
