package com.chad.wechatdev.commons.service;


import com.chad.wechatdev.entity.User;

/**
 *用户业务接口类
 */
public interface UserService {


     User saveWeChatUser(String code, String appId, String secret);

}
