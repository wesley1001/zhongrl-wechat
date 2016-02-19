package cn.xn.wechat.activity.mapper;

import java.util.Map;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.activity.model.UserQRcode;

public interface UserQRcodeMapper extends BaseMapper {

	/**
	 * 新增二维码
	 */
	public int addUserQRcode(UserQRcode userQRcode);

	/**
	 * 查询用户的二维码
	 */
	public UserQRcode getUserQRcodeByMap(Map<String,Object> paramMap);

}
