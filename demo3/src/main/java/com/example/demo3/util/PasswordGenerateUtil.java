package com.example.demo3.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Auther: 冯广
 * @Date: 2021/5/2 00:09
 * @Description:
 */
public class PasswordGenerateUtil {
    public static String getPassword(String username, String password, String salt,int hashTimes){
        Md5Hash md5Hash = new Md5Hash(password,username+salt,hashTimes);
        return md5Hash.toString();
    }
}
