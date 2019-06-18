package zxh;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.subject.Subject;


/**
 * Permission demo
 */
public class PermissionDemo {
    public static void main(String[] args) {
        //isPermissionTest();

        //checkPermissionTest();

       // testWildcardPermissionTest();


    }

    private static Subject login(){
        BaseLogin baseLogin = new BaseLogin();
        baseLogin.login("classpath:shiro-permission.ini","zxh","123456");
        return baseLogin.getSubject();
    }

    /**
     * isPermission方法
     */
    public static void isPermissionTest(){
        Subject subject =  login();
        //判断拥有某个权限
        System.out.println("是否拥有user:add权限："+ subject.isPermitted("user:add"));
        System.out.println("是否拥有user:delete权限："+ subject.isPermitted("user:delete"));
        //判断所有权限
        System.out.println("是否同时拥有user:add、user:edit权限："+ subject.isPermittedAll("user:add","user:update"));
    }

    /**
     * checkPermission：没有权限就抛异常（UnauthorizedException）
     */
    public static void checkPermissionTest(){
        Subject subject =  login();
        subject.checkPermission("user:add");
        subject.checkPermissions("user:add","user:update");
        //没有user:delete权限，抛出异常UnauthorizedException
        subject.checkPermission("user:delete");
    }

    /**
     * 通配符
     */
    public static void testWildcardPermissionTest(){
        Subject subject =  login();
        //两种方式等价
        subject.checkPermission("user:add");
        subject.checkPermission(new WildcardPermission("user:add"));
    }

    @RequiresRoles("user:add")
    public void add(){

    }








}
