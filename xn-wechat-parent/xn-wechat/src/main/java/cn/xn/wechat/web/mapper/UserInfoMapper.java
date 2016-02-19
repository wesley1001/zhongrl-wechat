package cn.xn.wechat.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import cn.xn.freamwork.support.orm.mapper.BaseMapper;
import cn.xn.wechat.web.model.UserInfo;
import cn.xn.wechat.web.model.UserInfoCriteria;

public interface UserInfoMapper extends BaseMapper {
	
   public int countByExample(UserInfoCriteria example);

   public int deleteByExample(UserInfoCriteria example);

   public int deleteByPrimaryKey(Long id);

   public int insert(UserInfo record);

   public int insertSelective(UserInfo record);

   public List<UserInfo> selectByExampleWithRowbounds(UserInfoCriteria example, RowBounds rowBounds);

   public List<UserInfo> selectByExample(UserInfoCriteria example);

   public UserInfo selectByPrimaryKey(Long id);

   public int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoCriteria example);

   public int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoCriteria example);

   public int updateByPrimaryKeySelective(UserInfo record);

   public int updateByPrimaryKey(UserInfo record);

   public List<UserInfo> selectLoginByExample(String userName);
}