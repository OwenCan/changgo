package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: changgou
 * @description:
 * @author: Owen Zhao
 * @create: 2020-11-09 16:04
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /***
     * 访问/test/hello  跳转到demo1页面
     * @param model
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("hello", "hello welcome");
        return "demo1";
    }
}