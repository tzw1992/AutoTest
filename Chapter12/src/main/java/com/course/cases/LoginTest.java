package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import com.mongodb.util.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        System.out.println("111111111111111111111");
        TestConfig.getUserInforUrl= ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl=ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.addUserUrl=ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.loginUrl=ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl=ConfigFile.getUrl(InterfaceName.UPDATEINFO);

        TestConfig.defaultHttpClient=new DefaultHttpClient();
    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        LoginCase loginCase=session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求
        String result=getResult(loginCase);
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);

    }



    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        LoginCase loginCase=session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //发送请求
        String result=getResult(loginCase);
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.loginUrl);
        JSONObject parm=new JSONObject();
        parm.put("userName",loginCase.getUserName());
        parm.put("password",loginCase.getPassword());

        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(parm.toString(),"utf-8");
        post.setEntity(entity);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");

        TestConfig.store=TestConfig.defaultHttpClient.getCookieStore();
        List<Cookie> cookies = TestConfig.store.getCookies();
        for(Cookie cookie:cookies){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("cookie name="+name+";cookie value="+value);
        }

        return result;
    }

}
