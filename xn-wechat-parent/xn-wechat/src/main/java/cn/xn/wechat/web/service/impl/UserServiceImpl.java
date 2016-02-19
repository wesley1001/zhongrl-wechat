package cn.xn.wechat.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.xn.freamwork.exception.ServiceRtException;
import cn.xn.freamwork.model.QueryPage;
import cn.xn.freamwork.support.utils.GenerateNumberUtils;
import cn.xn.freamwork.util.MD5util;
import cn.xn.wechat.web.mapper.UserInfoMapper;
import cn.xn.wechat.web.model.UserInfo;
import cn.xn.wechat.web.model.UserInfoCriteria;
import cn.xn.wechat.web.service.IUserService;
import cn.xn.wechat.web.util.PasswordHelper;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("userService")
public class UserServiceImpl implements IUserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private PasswordHelper passwordHelper;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 创建用户
     * @param user
     */
    public UserInfo createUser(UserInfo user) {
        //加密密码
        user.setUserId(GenerateNumberUtils.generateUID());
        user.setPaymentPwd(MD5util.md5(user.getLoginPwd()));
        passwordHelper.encryptCustomerPassword(user);

        int state = userInfoMapper.insertSelective(user);
        return state > 0 ? user:null;
    }

    @Override
    public int updateUserById(UserInfo user) {
        return userInfoMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateUserByUID(UserInfo user) {
        UserInfoCriteria doCriteria = new UserInfoCriteria();
        doCriteria.createCriteria().andUserIdEqualTo(user.getUserId());

        return userInfoMapper.updateByExampleSelective(user, doCriteria);
    }

    @Override
    public int updateUserByExample(UserInfo user, UserInfoCriteria example) {

        return userInfoMapper.updateByExampleSelective(user, example);
    }

    @Override
    public void deleteUser(Long userId) {
        userInfoMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 修改密码
     */
    public int changePassword(UserInfo user) {

        return userInfoMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserInfo findOneByUserId(String userId) {
        UserInfoCriteria doCriteria = new UserInfoCriteria();
        doCriteria.createCriteria().andUserIdEqualTo(userId);

        List<UserInfo> result = userInfoMapper.selectByExample(doCriteria);
        return (null != result && !result.isEmpty()) ? result.get(0):null;
    }


    @Override
    public List<UserInfo> findAll() throws ServiceRtException {

        return userInfoMapper.selectByExample(new UserInfoCriteria());
    }

    @Override
    public List<UserInfo> queryByExample(UserInfoCriteria example) {
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public QueryPage<UserInfo> queryByPage(QueryPage<UserInfo> queryModel, UserInfoCriteria example) throws ServiceRtException {

        try {
            queryModel.init(userInfoMapper.countByExample(example));

            if (queryModel.selectFlag()) {
                queryModel.setData(userInfoMapper.selectByExampleWithRowbounds(example, queryModel));
            }
        } catch (Exception e) {
            logger.error("======>>> serivce query user list err: "+ e);
        }

        return queryModel;
    }


    @Override
    public UserInfo findInfoAllByUser(String username) {
        try {
            List<UserInfo>  user = userInfoMapper.selectLoginByExample(username);
            return (null != user && !user.isEmpty()) ? user.get(0):null;
        } catch (Exception e) {
            logger.error("======>>> select user err: "+ e);
        }
        return null;
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public UserInfo findByUserName(String username) {
        if (StringUtils.isEmpty(username))
            return null;

        UserInfoCriteria doCriteria = new UserInfoCriteria();
        try {
            doCriteria.createCriteria().andUserLoginNameEqualTo(username.toLowerCase().trim());
            List<UserInfo> userInfos = userInfoMapper.selectByExample(doCriteria);
            return (userInfos != null && !userInfos.isEmpty()) ? userInfos.get(0):null;
        } catch (Exception e) {
            logger.error("======>>> query user err: "+ e);
        }

        return null;
    }

    @Override
    public boolean needUserInfo(String userName) {
        return (null != this.findByUserName(userName)) ? true:false;
    }

    @Override
    public boolean isExistsPhone(String phone) {
        List<UserInfo> result = this.findUserInfoByPhone(phone);
        return (null != result && result.size() > 0) ? true:false;
    }

    public List<UserInfo> findUserInfoByPhone(String phone) {
        UserInfoCriteria doCriteria = new UserInfoCriteria();
        doCriteria.createCriteria().andPhoneNumberEqualTo(phone);

        return userInfoMapper.selectByExample(doCriteria);
    }


    @Override
    public boolean isBindMail(String uid) {
        UserInfo userInfo = this.findOneByUserId(uid);
        return (null != userInfo ?
                (StringUtils.isEmpty(userInfo.getEmail()) ? true:false ):
                false);
    }

    @Override
    public boolean isExistsMail(String email) {
        UserInfoCriteria doCriteria = new UserInfoCriteria();
        doCriteria.createCriteria().andEmailEqualTo(email);
        List<UserInfo> result = userInfoMapper.selectByExample(doCriteria);

        return (null != result && !result.isEmpty()) ? true:false;
    }

}
