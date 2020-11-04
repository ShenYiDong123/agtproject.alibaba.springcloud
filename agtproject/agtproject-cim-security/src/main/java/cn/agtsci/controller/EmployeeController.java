package cn.agtsci.controller;/**
 * Created by ShenYiDong on 2019/11/5
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Employee测试接口类
 *@author: shenyidong
 *@create: 2019-11-05
 */
@Api(value = "employee模块接口", description = "employee模块接口")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @ApiOperation("greeting接口")
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello,World!";
    }

    @ApiOperation("login接口")
    @GetMapping("/login")
    public String login() {

        return "login sucess";
    }
}
