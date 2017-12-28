package com.zhuguang.jack.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @RequestMapping(value = "login")
    public String login(HttpServletRequest req) {
        HttpSession sessoin = req.getSession();
        sessoin.setAttribute("name", req.getParameter("name"));
        sessoin.setAttribute("password", req.getParameter("password"));
        return "登录成功！";
    }
    
    @RequestMapping(value = "queryUser")
    public String queryUser(HttpServletRequest req) {
        HttpSession sessoin = req.getSession();
        String name = (String)sessoin.getAttribute("name");
        String password = (String)sessoin.getAttribute("password");
        return name + ":" + password;
    }
}
