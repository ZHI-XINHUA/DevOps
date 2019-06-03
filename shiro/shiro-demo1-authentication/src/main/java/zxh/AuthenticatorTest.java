package zxh;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.security.Principal;
import java.util.List;

/**
 *验证测试
 */
public class AuthenticatorTest {
    public static void main(String[] args) {
        //testAllSuccessfulStrategyWithFail();

        //testAllSuccessfulStrategyWithSuccess();

        //testAtLeastOneSuccessfulStrategyWithSuccess();

        testAtFirstSuccessStrategyWithSuccess();
    }

    /**
     * 所有realms验证失败
     */
    public static void testAllSuccessfulStrategyWithFail() {
        String loginUser="zxh";
        String loginPassword="123456";
        login("classpath:shiro-authenticator-all-fail.ini",loginUser,loginPassword);
    }

    /**
     * 所有realms验证成功才返回身份集合
     */
    public static void testAllSuccessfulStrategyWithSuccess() {
        String loginUser="zxh";
        String loginPassword="123456";
        login("classpath:shiro-authenticator-all-success.ini",loginUser,loginPassword);
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();
        System.out.println(principalCollection.asList().size());
    }

    /**
     * 只要验证一个realm即可，返回第一个realm验证成功的验证信息
     */
    public static void testAtFirstSuccessStrategyWithSuccess() {
        String loginUser="zxh";
        String loginPassword="123456";
        login("classpath:shiro-authenticator-first-success.ini",loginUser,loginPassword);
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();

    }

    /**
     * 只要验证一个realm即可，返回所有realm验证成功的验证信息
     */
    public static void testAtLeastOneSuccessfulStrategyWithSuccess() {
        String loginUser="zxh";
        String loginPassword="123456";
        login("classpath:shiro-authenticator-atLeastOne-success.ini",loginUser,loginPassword);
        Subject subject = SecurityUtils.getSubject();

        //得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principalCollection = subject.getPrincipals();

    }



    private static void login(String source,String loginUser,String loginPassword){
        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(source);

        //2、获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //并且将securityManager并绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject
        Subject subject = SecurityUtils.getSubject();

        //创建用户名/密码身份验证token（即用户身份/凭证）
        UsernamePasswordToken token = new UsernamePasswordToken(loginUser,loginPassword);

        try{
            //4、登录
            subject.login(token);
        }catch (AuthenticationException e){//身份验证失败
            System.out.println(source+"--->验证失败："+e.getMessage());
        }

        //测试登录成功
        System.out.println(source+"--->"+loginUser+" 登录"+(subject.isAuthenticated()?"成功":"失败"));

      /*  System.out.println("============= 登出========");
        subject.logout();

        System.out.println(loginUser+" 登出"+(subject.isAuthenticated()?"成功":"失败"));*/
    }
}
