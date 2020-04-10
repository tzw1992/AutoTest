package com.course.controller;


import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "/",description = "这是我第一个版本的Mybatis的demo",hidden = true)
@RequestMapping("/v1")
public class Demo {
        //首先获取一个执行sql的对象
    @Autowired//启动起来后自动加载
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "获取到用户数",httpMethod = "GET")
    public int getUserList(){
        return template.selectOne("getUserCount");
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户数据",httpMethod = "POST")
    public int addUser(@RequestBody User user){
        return template.insert("addUser",user);

    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "更新用户数据",httpMethod = "POST")
    public int updateUser(@RequestBody User user){
        return template.update("updateUser",user);
    }

    @RequestMapping(value = "/deleUser",method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除用户",httpMethod = "GET")
    public int deleUser(@RequestParam int id){
        return template.delete("deleteUser",id);
    }
}
