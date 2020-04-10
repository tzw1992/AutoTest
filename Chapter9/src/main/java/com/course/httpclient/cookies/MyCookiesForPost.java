package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    //用来存储cookies信息的变量
    private CookieStore store;
    @BeforeTest
    public void beforeTest(){
        bundle= ResourceBundle.getBundle("application", Locale.CHINA);
        url=bundle.getString("test.url");
    }
    @Test
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件中拼接url
        String uri=bundle.getString("getCookies.uri");
        String testurl=this.url+uri;
        //测试逻辑代码
        System.out.println(testurl);
        HttpGet get=new HttpGet(testurl);
        DefaultHttpClient client=new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);


        //获取cookie信息
        this.store = client.getCookieStore();
        List<Cookie> cookies = this.store.getCookies();
        for(Cookie cookie:cookies){
            String name=cookie.getName();
            String value=cookie.getValue();
            System.out.println("cookie name="+name+";cookie value="+value);
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException {
        String uri=bundle.getString("test.post.with.cookies");
        String testurl=this.url+uri;
        //声明一个client对象，用来进行方法的测试
        DefaultHttpClient client=new DefaultHttpClient();
        //声明一个方法，这个方法就是Post方法
        HttpPost post=new HttpPost(testurl);
        //添加参数
        JSONObject parm=new JSONObject();
        parm.put("name","zhangsan");
        parm.put("age","20");
        System.out.println("parm:"+parm);
        System.out.println(parm.getClass().equals(JSONObject.class));
        //设置请求头信息，设置header
        post.setHeader("content-type","application/json");
        //将参数添加到方法中
        StringEntity entity=new StringEntity(parm.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //设置cookie信息
        client.setCookieStore(this.store);
        //执行Post方法
        HttpResponse response=client.execute(post);
        //获取响应结果
        result=EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //处理结果，判断结果是否预期一致
        //将返回的响应结果字符串转换成json对象
        JSONObject resultJson=new JSONObject(result);

        //获取到结果值
        String name = (String) resultJson.get("zhangsan");
        String status=(String)resultJson.get("status");
        //具体的判断返回的值
        Assert.assertEquals("success",name);
        Assert.assertEquals("1",status);
    }
}
