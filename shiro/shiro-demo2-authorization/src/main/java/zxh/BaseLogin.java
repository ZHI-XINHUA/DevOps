package zxh;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;


/**
 * 登录
 */
public class BaseLogin {

    /**
     * 登录
     * @param configFile 配置文件
     * @param userName 用户名
     * @param password 密码
     */
    public void login(String configFile,String userName,String password){
        //1、获取SecurityManager工厂，此处使用ini配置初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //2、获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //并且将securityManager并绑定给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到subject
        Subject subject = SecurityUtils.getSubject();

        //创建用户名/密码身份验证token（即用户身份/凭证）
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);

        try{
            //4、登录
            subject.login(token);
        }catch (AuthenticationException e){//身份验证失败
            System.out.println("验证失败："+e.getMessage());
        }
    }

    /**
     * 获取主体（用户）
     * @return
     */
    public Subject getSubject(){
        return SecurityUtils.getSubject();
    }


    public void tearDown(){
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }


}
