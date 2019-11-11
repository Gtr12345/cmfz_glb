package com.baizhi.realm;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.AdminRoleDao;
import com.baizhi.dao.RoleDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.AdminRole;
import com.baizhi.entity.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;
    @Autowired
    private RoleDao roleDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //去拿主身份
        String username = (String) principals.getPrimaryPrincipal();
        //查询admin对象
        Admin admin = adminDao.selectOne(new Admin(null, username, null, null, null, null));
        //根据admin的id获取关系表中的adminRole对象的集合
        List<AdminRole> select = adminRoleDao.select(new AdminRole(null, admin.getId(), null));
        ArrayList<String> list = new ArrayList<>();
        //遍历adminRole集合,取其中的adminRole对象里边的roleId属性
        for (AdminRole adminRole : select) {
            //根据roleId属性,获取role对象
            Role role = roleDao.selectOne(new Role(adminRole.getRoleId(), null));
            list.add(role.getName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(list);

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token1) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) token1;
        Admin admin = new Admin();
        admin.setUsername(token.getUsername());
        Admin loginAdmin = adminDao.selectOne(admin);
        System.out.println(loginAdmin);
        if (loginAdmin == null) {
            return null;
        } else {
            SimpleAccount account = new SimpleAccount(loginAdmin.getUsername(), loginAdmin.getPassword(), ByteSource.Util.bytes(loginAdmin.getSalt()), this.getName());
            return account;
        }
    }
}
