package zxh;

import org.apache.shiro.subject.Subject;

public class AuthorizerTest {
    public static void main(String[] args) {
        //testIsPermitted();

        testIsPermittedJDBC();
    }



    public static  void testIsPermitted() {
        BaseLogin baseLogin = new BaseLogin();
        baseLogin.login("classpath:shiro-authorizer.ini","zxh","123456");
        Subject subject =  baseLogin.getSubject();



        //判断拥有权限：user:create
        System.out.println(subject.isPermitted("user1:update"));
        System.out.println(subject.isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        System.out.println(subject.isPermitted("+user1+2"));//新增权限
        System.out.println(subject.isPermitted("+user1+8"));//查看权限
        System.out.println(subject.isPermitted("+user2+10"));//新增及查看

        System.out.println(subject.isPermitted("+user1+4"));//没有删除权限

        System.out.println(subject.isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
    }

    public static void testIsPermittedJDBC() {
        BaseLogin baseLogin = new BaseLogin();
        baseLogin.login("classpath:shiro-jdbc-authorizer.ini","zxh","123456");
        Subject subject =  baseLogin.getSubject();

        //判断拥有权限：user:create
        System.out.println(subject.isPermitted("user1:update"));
        System.out.println(subject.isPermitted("user2:update"));
        //通过二进制位的方式表示权限
        System.out.println(subject.isPermitted("+user1+2"));//新增权限
        System.out.println(subject.isPermitted("+user1+8"));//查看权限
        System.out.println(subject.isPermitted("+user2+10"));//新增及查看

        System.out.println(subject.isPermitted("+user1+4"));//没有删除权限

        System.out.println(subject.isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
    }


}
