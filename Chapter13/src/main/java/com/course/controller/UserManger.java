package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "/v1",description = "用户管理系统")
@RequestMapping("/v1")
public class UserManger {
    @Autowired
    private SqlSessionTemplate template;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    public Boolean login(HttpServletResponse response, @RequestBody User user){
        int i=template.selectOne("login",user);
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if(i==1){
            log.info("用户是"+user.getUserName());
            return true;
        }
        return false;

    }

    @ApiOperation(value = "添加用户成功",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public Boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean x=verifyCookies(request);
        int result=0;
        if(x!=null){
            result= template.insert("addUser",user);

        }
        if(result>0){
            log.info("添加用户数量是"+result);
            return true;
        }
        return false;
    }


    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户(列表)信息接口",httpMethod = "POST")
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x=verifyCookies(request);
        log.info("getUserInfo_cookie111111111111111111111:"+x);
//        List<User> users=template.selectList("getUserInfo",user);
//        log.info("getUserInfo获取到的用户数量是"+users.size());
        if(x){
            List<User> users=template.selectList("getUserInfo",user);
            log.info("getUserInfo获取到的用户数量是"+users.size());
            return users;
        }else {
            return null;

        }
//        return users;
    }

    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    public int updateUser(HttpServletRequest request,@RequestBody User user ){
        Boolean x=verifyCookies(request);
        int i=0;
        if(x){
            i=template.update("updateUserInfo",user);
        }
        log.info("更新数据的条目数是"+i);
        return i;

    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        if(Objects.isNull(cookies)){
            log.info("cookie为空");
            return false;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                log.info("cookies验证通过");
                return true;
            }

        }
        return false;
    }
}
