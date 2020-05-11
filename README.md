# wechatdev
Springboot微信公众号开发
# 微信公众号开发 
## 公众号开发 集成SpringBoot 便捷开发(前后端)

![demo 示例图](https://raw.githubusercontent.com/visayang/wechatdev/master/img/%E5%BE%AE%E4%BF%A1%E6%8E%88%E6%9D%83.jpg)

### 涵盖：
1. 微信公众号环境搭建 校验 接受转发信息。 **具体请看 VerificationController 相关**
2. 微信公众号授权用户  [获取用户基本信息](https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe8c41dc9dfadc85&redirect_uri=Url.Encode(usercodeinterface)&response_type=code&scope=snsapi_userinfo&state=testtest&connect_redirect=1#wechat_redirect) **具体请看 OauthController 相关**
3. 获取用户信息时，根据官方文档，判断是否需要刷新token 作出相关处理。**具体请看 UserServiceImpl 类实现**
4. Springboot Controll 和 html 通信，基于 thymeleaf。**具体请看 index.html 相关**
5. Springboot 简单get 与post 实现，以及前端 ajax 调用。**具体请看 test.html 和 DefineDataController 相关**

#### tips:
* 因个人公众号不能获取用户信息，案例用的是测试账号，请在 application.yml 更换 wechat 相关配置
* 项目授权时候，需要拷贝授权文件到自己部署服务器上，一般 服务器地址/文件 可访问到即成功。可以在本地映射出去（花生壳免费），放于本地tomcat root 下。
* static新引入的资源文件，需要重启项目才能found;templates 里放html文件
* 日志会输出在同级目录 logFolder 中
##
### 环境搭建完，后面就是根据各自项目去开发后台和前台代码了
## 
### 本人非前端和后端工程师，也是开发任务中穿插的任务，所以写下demo 供非同行人员快速开发。
## 

## 学习交流
* EMAIL:980373417@qq.com
* QQ:980373417


