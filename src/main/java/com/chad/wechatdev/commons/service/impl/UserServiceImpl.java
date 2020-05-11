package com.chad.wechatdev.commons.service.impl;



import com.chad.wechatdev.commons.config.WechatMpProperties;
import com.chad.wechatdev.commons.service.UserService;
import com.chad.wechatdev.commons.utils.HttpUtils;
import com.chad.wechatdev.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);



    @Override
    public User saveWeChatUser(String code, String appId, String secret) {
        //1、通过openAppid和openAppsecret和微信返回的code，拼接URL
        String accessTokenUrl = String.format(WechatMpProperties.OPEN_ACCESS_TOKEN_URL, appId.trim(), secret.trim(), code);

        //2、通过URL再去回调微信接口 (使用了httpclient和gson工具）
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);

        logger.info("=====拿到授权accesstoken=====");
        logger.info(baseMap.toString());

        //3、回调成功后获取access_token和oppid
        if (baseMap == null || baseMap.isEmpty()||baseMap.get("access_token")==null){
            logger.error("=====获取access_token失败=====");
            return null;
        }
        String accessToken = (String) baseMap.get("access_token");
        String openId = (String) baseMap.get("openid");
        String refreshtoken = (String) baseMap.get("refresh_token");

        //校验token是否有效
        String iseffectiveUrl = String.format(WechatMpProperties.OPEN_ACCESS_TOKEN_ISEFFECTIVE_URL, accessToken.trim(), openId.trim());
        Map<String, Object> baseMap2 = HttpUtils.doGet(iseffectiveUrl);

        logger.info("=====验token是否有效=====");
        logger.info(baseMap2.toString());

        if (baseMap2 == null || baseMap2.isEmpty()){
            logger.error("=====验token是否有效失败=====");
            return null;
        }

        //不是有效 就刷新token
        System.out.println(baseMap2.get("errcode").toString());
        if (!baseMap2.get("errcode").toString().equals("0.0")) {
            String refreshtokenUrl = String.format(WechatMpProperties.OPEN_ACCESS_TOKEN_REFRESH_URL, appId.trim(), refreshtoken.trim());
            Map<String, Object> baseMap3 = HttpUtils.doGet(refreshtokenUrl);

            logger.info("=====刷新token=====");
            logger.info(baseMap3.toString());

            if (baseMap3 == null || baseMap3.isEmpty()){
                logger.error("=====刷新token失败=====");
                return null;
            }

            accessToken = (String) baseMap3.get("access_token");
            openId = (String) baseMap3.get("openid");
        }

        //4、access_token和openid拼接URL（openid是用户唯一标志）
        String userInfoUrl = String.format(WechatMpProperties.OPEN_USER_INFO_URL, accessToken, openId);

        //5、通过URL再去调微信接口获取用户基本信息
        Map<String, Object> baseUserMap = HttpUtils.doGet(userInfoUrl);

        logger.info("=====获取用户基本信息=====");
        logger.info(baseUserMap.toString());

        if (baseUserMap == null || baseUserMap.isEmpty()){
            logger.error("=====获取用户基本信息失败=====");
            return null;
        }

        //6、获取用户姓名、性别、城市、头像等基本信息
        String nickname = (String) baseUserMap.get("nickname");
        try {
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        String openid = (String) baseUserMap.get("openid");
        String headimgurl = (String) baseUserMap.get("headimgurl");

        logger.info(nickname+"===="+headimgurl);

        if(nickname != null){
            return new User(openid,nickname,headimgurl);
        }
        logger.error("=====默认失败=====");
        return null;
    }
}
