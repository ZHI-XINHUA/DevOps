package zxh.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
/**
 * Realm提供安全数据（如用户、角色、权限等），可以看作Datasource（安全数据源）
 */
public class MyRealm2 implements Realm {
    /**
     * 返回一个唯一的realm名称
     * @return
     */
    @Override
    public String getName() {
        return "MyRealm2";
    }

    /**
     * 判断此realm是否支持token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 根据token获取认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();//用户名
        String password =  new String((char[])token.getCredentials());//密码
        System.out.println(getName()+"验证的信息：userName="+userName+" password="+password);
        if(!"root".equals(userName)){
            throw  new UnknownAccountException();//用户不存在
        }

        if(!"123456".equals(password)){
            throw new IncorrectCredentialsException();//验证失败
        }

        return new SimpleAuthenticationInfo(userName,password,getName());
    }
}
