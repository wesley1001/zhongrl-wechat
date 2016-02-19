package cn.xn.wechat.activity.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestPrizeInfoController extends TestBase {
	@Test
	public void testFindPrizeInfoList() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/prize/findPrizeInfoList?userId=10458564c68c4d898c5c9dbf708921f8");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}
	
	@Test
	public void getPrizeInfoById() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/prize/getPrizeInfoById?userId=10458564c68c4d898c5c9dbf708921f8&prizeId=4ddd60cfc05a4eff8fedf1e8743aca1a");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}

	@Test
	public void queryInviteFriendRecord() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/user/queryInviteFriendRecord?userId=10458564c68c4d898c5c9dbf708921f8&pageSize=5&pageIndex=1");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}
	
	/**
	 * 名称排行榜
	 * @throws Exception
	 */
	@Test
	public void findInviteNumberRank() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/user/findInviteNumberRank?userId=10458564c68c4d898c5c9dbf708921f8");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}

	@Test
	public void getMaterialResp() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/user/getMaterialResp?userId=10458564c68c4d898c5c9dbf708921f8");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}

	@Test
	public void findChangePrizeList() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/user/findChangePrizeList?userId=10458564c68c4d898c5c9dbf708921f8");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}

	@Test
	public void myUserInfoHome() throws Exception {
		StringBuilder url = new StringBuilder(
				"/activity/user/myUserInfoHome?userId=625b2641191349a4bfa98920f09fd838");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);
	}

	@Test
	public void testPromptChangePrize() throws Exception {
		String userId = "8b248967af69406bb503a181e99e8540";
		String prizeId = "06d64ab9b3594f0e9af4549962e53302";
		String mobile = "15118811426";

		StringBuilder url = new StringBuilder(
				"/activity/prize/confirmChangePrize?prizeId=" + prizeId
						+ "&userId=" + userId + "&mobile=" + mobile+"&username=zhansan&address=北京市中区");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);

	}

	@Test
	public void getActivityRule() throws Exception {

		StringBuilder url = new StringBuilder("/activity/getActivityRule");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);

	}
	
	@Test
	public void getQrCodeUrlById() throws Exception {
		StringBuilder url = new StringBuilder("/activity/user/getQrCodeUrlById?userId=1345ad2e40f9427593ee4f45ee5989d4");
		ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get(
				url.toString()).accept(MediaType.APPLICATION_JSON));
		MvcResult result = actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String respBody = result.getResponse().getContentAsString();
		System.out.println(respBody);

	}
	
	
	
}
