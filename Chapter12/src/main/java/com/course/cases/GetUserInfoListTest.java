package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetUserInfoListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase=session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //发送请求获取结果
        JSONArray resultjson=getJsonResult(getUserListCase);
        //验证结果
        List<User> userList=session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u:userList){
            System.out.println("获取的user:"+u.toString());
        }
        JSONArray userListArray=new JSONArray(userList);
        Assert.assertEquals(userListArray.length(),resultjson.length());
        for(int i=0;i<resultjson.length();i++){
            JSONObject expect= (JSONObject) resultjson.get(i);
            JSONObject actual= (JSONObject) userListArray.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        JSONObject parm=new JSONObject();
        parm.put("userName",getUserListCase.getUserName());
        parm.put("sex",getUserListCase.getSex());
        parm.put("age",getUserListCase.getAge());

        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(parm.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray=new JSONArray(result);
        return jsonArray;
    }
}
