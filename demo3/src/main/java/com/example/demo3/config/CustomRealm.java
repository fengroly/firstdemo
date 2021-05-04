package com.example.demo3.config;

import com.example.demo3.model.ShiroUser;
import com.example.demo3.util.PasswordGenerateUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 冯广
 * @Date: 2021/5/1 17:10
 * @Description:
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    /**
     * 认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户输入的账号
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String username = (String) token.getPrincipal();
        //2.通过username从数据库中查找到user实体
        ShiroUser user = getUserByUserName(username);
        if (user == null) {
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, "123qwe", ByteSource.Util.bytes(user.getSalt()), getName());
        //4.用户账号状态验证等其他业务操作
        if (!user.getAvailable()) {
            throw new AuthenticationException("该账号已经被禁用");
        }
        //5.返回身份处理对象 db09dc223aff12777e500622d89eb0ac
        return simpleAuthenticationInfo;
    }

    @Override
    /**
     * 授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("调用了授权方法");
        //获取当前登录的用户
        ShiroUser user = (ShiroUser) principal.getPrimaryPrincipal();
        //通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加角色
        simpleAuthorizationInfo.addRole(user.getRole());
        //添加权限
        simpleAuthorizationInfo.addStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 模拟通过username从数据库中查找到user实体
     *
     * @param username
     * @return
     */
    private ShiroUser getUserByUserName(String username) {
        List<ShiroUser> users = getUsers();
        for (ShiroUser user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

//    /**
//     * 模拟数据库数据
//     * @return
//     */
//    private List<ShiroUser> getUsers(){
//        List<ShiroUser> users = new ArrayList<>();
//        users.add(new ShiroUser("张小黑的猫","123qwe",true));
//        users.add(new ShiroUser("张小黑的狗","123qwe",false));
//        return users;
//    }

    /**
     * 模拟数据库数据
     *
     * @return
     */
    private List<ShiroUser> getUsers() {
//        List<ShiroUser> users = new ArrayList<>(2);
//        List<String> cat = new ArrayList<>(2);
//        cat.add("sing");
//        cat.add("rap");
//        List<String> dog = new ArrayList<>(2);
//        dog.add("jump");
//        dog.add("basketball");
//        users.add(new ShiroUser("张小黑的猫","123qwe",true,"cat",cat));
//        users.add(new ShiroUser("张小黑的狗","123qwe",true,"dog",dog));
//        return users;
//        String originalPassword = "123qwe"; //原始密码
//        String hashAlgorithmName = "MD5"; //加密方式
//        int hashIterations = 2; //加密的次数
//
//        //盐
//        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
//
//        //加密
//        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, originalPassword, salt, hashIterations);
//        String encryptionPassword = simpleHash.toString();
//        /**
//         * 模拟创建用户1
//         */
        List<ShiroUser> users = new ArrayList<>(2);
        String username1 = "张小黑的猫";
        String salt1 = Long.toString(System.currentTimeMillis());
        String password1 = PasswordGenerateUtil.getPassword(username1, "123qwe", salt1, 2);
        List<String> cat = new ArrayList<>(2);
        cat.add("sing");
        cat.add("rap");
        users.add(new ShiroUser(username1, password1, salt1, true, "cat", cat));

        /***
         * 模拟创建用户2
         */
        String username2 = "张小黑的狗";
        String salt2 = Long.toString(System.currentTimeMillis());
        String password2 = PasswordGenerateUtil.getPassword(username2, "123qwe", salt2, 2);
        List<String> dog = new ArrayList<>(2);
        dog.add("jump");
        dog.add("basketball");
        users.add(new ShiroUser(username2, password2, salt2, true, "dog", dog));

        System.out.println(users);
        return users;
    }
}
