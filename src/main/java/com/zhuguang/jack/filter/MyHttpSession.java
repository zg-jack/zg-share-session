package com.zhuguang.jack.filter;

import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.alibaba.fastjson.JSONObject;
import com.zhuguang.jack.redis.RedisApi;

public class MyHttpSession implements HttpSession {
    
    private ThreadLocal<String> local = new ThreadLocal<String>();
    
    HttpServletRequest req;
    
    HttpServletResponse resp;
    
    public MyHttpSession(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }
    
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setMaxInactiveInterval(int interval) {
        // TODO Auto-generated method stub
        
    }
    
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Object getAttribute(String name) {
        String sessionId = getSessionIdFromCookie();
        
        List<String> list = RedisApi.lrange(sessionId);
        if (list != null) {
            return getValueByKey(name, list);
        }
        return null;
    }
    
    private Object getValueByKey(String key, List<String> list) {
        for (String each : list) {
            JSONObject jo = JSONObject.parseObject(each);
            if (jo.containsKey(key)) {
                return jo.get(key);
            }
        }
        return null;
    }
    
    public Object getValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public String[] getValueNames() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setAttribute(String name, Object value) {
        String sessionId = getSessionIdFromCookie();
        
        if (sessionId == null) {
            sessionId = local.get();
            if (sessionId == null) {
                sessionId = "dnjack&" + UUID.randomUUID();
                local.set(sessionId);
            }
        }
        JSONObject jo = new JSONObject();
        jo.put(name, value);
        RedisApi.lpush(sessionId, jo.toJSONString());
        writeCookie(sessionId);
    }
    
    private void writeCookie(String sessionId) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
    
    /** 
     * @Description TODO 
     * @param @return 参数 
     * @return String 返回类型  
     * @throws 
     */
    private String getSessionIdFromCookie() {
        Cookie[] cookies = req.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    public void putValue(String name, Object value) {
        // TODO Auto-generated method stub
        
    }
    
    public void removeAttribute(String name) {
        // TODO Auto-generated method stub
        
    }
    
    public void removeValue(String name) {
        // TODO Auto-generated method stub
        
    }
    
    public void invalidate() {
        // TODO Auto-generated method stub
        
    }
    
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
