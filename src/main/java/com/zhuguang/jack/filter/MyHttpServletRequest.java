package com.zhuguang.jack.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyHttpServletRequest extends HttpServletRequestWrapper {
    
    HttpServletRequest req;
    
    HttpServletResponse resp;
    
    public MyHttpServletRequest(HttpServletRequest request,
            HttpServletResponse response) {
        super(request);
        this.req = request;
        this.resp = response;
    }
    
    @Override
    public HttpSession getSession() {
        // TODO Auto-generated method stub
        return new MyHttpSession(req, resp);
    }
    
}
