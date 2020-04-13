package com.course.cases;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Tzw_test {
    public static void main(String[] args) {
        String str= "[{\"id\":1,\"userName\":\"zhangsan\",\"password\":\"123456\",\"age\":22,\"sex\":0,\"permission\":0,\"isDelete\":0}]";
        List list= Arrays.asList(str);
        System.out.println(list);
        System.out.println(list.get(0));
        JSONArray jsonArray=new JSONArray(list);
        System.out.println(jsonArray);

        System.out.println(jsonArray.get(0));
        System.out.println(jsonArray.getString(0));


        JSONArray jsonArray1=new JSONArray(jsonArray.getString(0));
        JSONObject jsonObject= (JSONObject) jsonArray1.get(0);
        System.out.println(jsonArray1.get(0));
        System.out.println(jsonObject.get("password"));

    }
}
