package cn.xn.wechat.web.service;


import java.util.List;

import cn.xn.freamwork.exception.ServiceRtException;
import cn.xn.freamwork.model.QueryPage;
import cn.xn.wechat.web.model.UserInfo;
import cn.xn.wechat.web.model.UserInfoCriteria;

public interface IUserService {

    /**
     * 创建用户
     * @param user
     */
    UserInfo createUser(UserInfo user);

    /**
     * 根据ID修改用户信息
     * @param user
     * @return
     */
    int updateUserById(UserInfo user);

    /**
     * 根据UID修改用户信息
     * @param user
     * @return
     */
    int updateUserByUID(UserInfo user);

    /**
     * 根据条件修改用户信息
     *
     * @param user
     * @param example
     * @return
     */
    int updateUserByExample(UserInfo user, UserInfoCriteria example);

    /**
     * 根据ID删除用户
     * @param userId
     */
    void deleteUser(Long userId);

    /**
     * 修改密码
     */
    int changePassword(UserInfo user);


    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    UserInfo findOneByUserId(String userId);

    /**
     * 查询所有用户
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 根据条件查询用户
     * @param example
     * @return
     */
    List<UserInfo> queryByExample(UserInfoCriteria example);

    /**
     *
     * @param queryModel
     * @param example
     * @return
     * @throws cn.xn.freamwork.exception.ServiceRtException
     */
    QueryPage<UserInfo> queryByPage(QueryPage<UserInfo> queryModel, UserInfoCriteria example) throws ServiceRtException;

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    UserInfo findByUserName(String username);

    /**
     * 存在用户
     * @param userName
     * @return
     */
    boolean needUserInfo(String userName);

    /**
     * 检查手机是否被绑定
     * @param phone
     * @return
     */
    boolean isExistsPhone(String phone);

    /**
     * 是否可绑定邮箱
     * @param uid
     * @return
     */
    boolean isBindMail(String uid);

    /**
     * 邮箱是否被使用
     * @param email
     * @return
     */
    boolean isExistsMail(String email);

    /**
     * 查询用户所有信息(登录时用)
     *
     * @param username
     * @return
     */
    UserInfo findInfoAllByUser(String username);


}
