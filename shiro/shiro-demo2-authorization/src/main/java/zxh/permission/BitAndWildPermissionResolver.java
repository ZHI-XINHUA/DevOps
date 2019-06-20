package zxh.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 解析权限字符
 *
 * PermissionResolver 用于解析权限字符到Permission实例
 * RolePermissionResolver 用于根据角色解析相应的权限集合
 *
 * copy by 《跟我学Shiro》-张开涛
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        if(permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
