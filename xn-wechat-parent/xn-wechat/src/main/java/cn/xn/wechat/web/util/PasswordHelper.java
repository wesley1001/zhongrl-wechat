package cn.xn.wechat.web.util;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.xn.wechat.web.model.UserInfo;

@Service
public class PasswordHelper
{

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName}")
    private String algorithmName = "md5";
    @Value("2")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    /**
     * customer user
     * @param user
     */
    public void encryptCustomerPassword(UserInfo user) {
        if (StringUtils.isEmpty(user.getSalt()))
            user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getLoginPwd(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        user.setLoginPwd(newPassword);
    }


    public static void main(String[] args) {
        PasswordHelper ph = new PasswordHelper();
        //"6d7d97df748e7a8977a3265539553ce7"
        UserInfo user = new UserInfo();
        user.setUserLoginName("admin");
        user.setLoginPwd("admin123");
        user.setSalt("cc03e747a6afbbcbf8be7668acfebee5");
        ph.encryptCustomerPassword(user);
        System.out.println("password: "+ user.getLoginPwd());

    }

}
