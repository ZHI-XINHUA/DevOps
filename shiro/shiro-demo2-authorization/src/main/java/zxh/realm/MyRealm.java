package zxh.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import zxh.permission.BitPermission;

/**
 * 自定义realm
 *
 * copy by 《跟我学Shiro》-张开涛
 */
public class MyRealm extends AuthorizingRealm {

    /**
     * 返回一个唯一的realm名称
     * @return
     */
    @Override
    public String getName() {
        return "MyRealm";
    }

    /**
     * 根据用户身份获取授权信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");
        authorizationInfo.addRole("role1");
        authorizationInfo.addObjectPermission(new BitPermission("+user1+10"));
        authorizationInfo.addObjectPermission(new WildcardPermission("user1:*"));
        authorizationInfo.addStringPermission("+user2+10");
        authorizationInfo.addStringPermission("user2:*");
        return authorizationInfo;
    }

    /**
     * 获取身份验证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();//用户名
        String password = new String((char[]) token.getCredentials());//密码

        if(!"olay".equals(username) || !"zxh".equals(username)){
            //不存在用户
            throw new UnknownAccountException();
        }

        if(!"123456".equals(password) ){
            //验证失败
            throw new IncorrectCredentialsException();
        }
        //验证成功
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
