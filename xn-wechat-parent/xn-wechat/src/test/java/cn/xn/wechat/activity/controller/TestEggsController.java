package cn.xn.wechat.activity.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestEggsController extends TestBase {
	/**
	 * 活动信息
	 */
	@Test
	public void testGetActivity() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/activity/eggs/getActivity");
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andDo(print());

	}
}
