package zxh;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;

import java.util.Arrays;

/**
 * 角色demo
 */
public class RoleDemo  {

    public static void main(String[] args) {
        hasRoleTest();

        //checkRoleTest();
    }

    /**
     *   hasRole、hasRoles、hasAllRoles 方法使用
     */
    public static  void hasRoleTest(){
        String userName = "zxh";
        String password = "123456";
        //登录
        BaseLogin baseLogin = new BaseLogin();
        baseLogin.login("classpath:shiro-role.ini",userName,password);
        //获取subject
        Subject subject = baseLogin.getSubject();

        //hasRole
        System.out.println(userName+"是否拥有role1角色："+ subject.hasRole("role1"));

        //hasRoles
        boolean[] hasRoles = subject.hasRoles(Arrays.asList("role1","role3"));
        System.out.print(userName+"是否拥有role1、role3角色： ");
        for (int i=0;i<hasRoles.length;i++){
            System.out.print(hasRoles[i]+" ");
        }
        System.out.println();

        //hasAllRoles
        System.out.println(userName+"是否同时拥有role1、role2角色："+subject.hasAllRoles(Arrays.asList("role1","role2")));


    }

    /**
     * checkRole 方法使用，失败抛出异常
     */
    public static void checkRoleTest(){
        String userName = "olay";
        String password = "123456";
        //登录
        BaseLogin baseLogin = new BaseLogin();
        baseLogin.login("classpath:shiro-role.ini",userName,password);
        //获取subject
        Subject subject = baseLogin.getSubject();


        subject.checkRole("role1");


        subject.checkRoles("role1","role2");

        subject.checkRoles(Arrays.asList("role1","role2"));
    }
}
