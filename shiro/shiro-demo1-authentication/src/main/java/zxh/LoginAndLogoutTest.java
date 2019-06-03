package zxh;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * 模拟登录、登出
 */
public class LoginAndLogoutTest {
    public static void main(String[] args) {

        //loginAndLogout();
        //loginAndLogoutRealm();

        //loginAndLogoutMulitRealm();

        loginAndLogoutJdbcRealm();

    }

    /**
     * demo1：登录登出test
     */
    public static void loginAndLogout(){
        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory =  new IniSecurityManagerFactory("classpath:shiro.ini");

        //2、获取SecurityManager实例
        SecurityManager securityManager =  factory.getInstance();
        // securityManager并绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject
        Subject subject = SecurityUtils.getSubject();
        //创建用户名/密码身份验证token（即用户身份/凭证）
        UsernamePasswordToken token = new UsernamePasswordToken("zxh","123456");

        //记住我
        //token.setRememberMe(true);
        try{
            //4、登录
            subject.login(token);


        }catch (AuthenticationException e){
            //登录失败
        }

        //验证是否登录成功
        System.out.println("用户登录="+subject.isAuthenticated());

        //6、退出
        subject.logout();

        //验证是否登录成功
        System.out.println("用户登录="+subject.isAuthenticated());
    }


    /**
     * 使用Realm实现登录登出
     */
    public static void loginAndLogoutRealm(){
        String loginUser = "zxh";
        String loginPassword = "123456";

        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

        //2、获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //并且将securityManager并绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject
        Subject subject = SecurityUtils.getSubject();
        //创建用户名/密码身份验证token（即用户身份/凭证）
        UsernamePasswordToken token = new UsernamePasswordToken(loginUser,loginPassword);

        //记住我
        //token.setRememberMe(true);

        try{
            //4、登录
            subject.login(token);
        }catch (AuthenticationException e){//身份验证失败
            System.out.println("验证失败："+e.getMessage());
        }

        //测试登录成功
        System.out.println(loginUser+" 登录"+(subject.isAuthenticated()?"成功":"失败"));

        System.out.println("=============登出========");
        subject.logout();

        System.out.println(loginUser+" 登出"+(subject.isAuthenticated()?"成功":"失败"));
    }

    /**
     * 使用多Realm实现登录登出
     */
    public static void loginAndLogoutMulitRealm(){
        String loginUser = "root";
        String loginPassword = "123456";

        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-mulit-realm.ini");

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
            System.out.println("多realm-->"+"验证失败："+e.getMessage());
        }

        //测试登录成功
        System.out.println("多realm-->"+loginUser+" 登录"+(subject.isAuthenticated()?"成功":"失败"));

        System.out.println("=============登出========");
        subject.logout();

        System.out.println(loginUser+" 登出"+(subject.isAuthenticated()?"成功":"失败"));
    }

    /*
     * jdbc Reaml
     */
    public static void loginAndLogoutJdbcRealm(){
        String loginUser = "zxh";
        String loginPassword = "123456";

        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

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
            System.out.println("jdbc--->验证失败："+e.getMessage());
        }

        //测试登录成功
        System.out.println("jdbc--->"+loginUser+" 登录"+(subject.isAuthenticated()?"成功":"失败"));

        System.out.println("============= 登出========");
        subject.logout();

        System.out.println(loginUser+" 登出"+(subject.isAuthenticated()?"成功":"失败"));
    }
}
