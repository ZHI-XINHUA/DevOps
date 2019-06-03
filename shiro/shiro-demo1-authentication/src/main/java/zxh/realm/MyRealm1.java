package zxh.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Realm提供安全数据（如用户、角色、权限等），可以看作Datasource（安全数据源）
 */
public class MyRealm1 implements Realm {
    /**
     * 返回一个唯一的realm名称
     * @return
     */
    @Override
    public String getName() {
        return "MyRealm1";
    }

    /**
     * 判断此realm是否支持token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken（用户名密码）类型的token
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
        //  String password = (String)token.getCredentials();//得到密码，这种获取方式失败，因为UsernamePasswordToken!getPassword() 返回的是char[]
        String password = new String((char[])token.getCredentials()); //得到密码
        System.out.println("验证的信息：userName="+userName+" password="+password);
        if(!"zxh".equals(userName)){//用户名不存在
            throw  new UnknownAccountException();//
        }
        if(!"123456".equals(password)){//密码不正确
            throw new IncorrectCredentialsException();//身份验证失败
        }
        //验证通过，返回一个AuthenticationInfo实例
        return new SimpleAuthenticationInfo(userName,password,getName());//参数：用户名，密码，当前realm名称
    }
}

