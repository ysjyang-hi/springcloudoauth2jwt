package cn.nadow.resource;

import cn.nadow.resource.Util.CustomHttpServletRequest;

import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//@Order(1)

//@WebFilter(urlPatterns = "/*", filterName = "reqResFilter")
public class AeqResFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("=============自定义过滤器============");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        Cookie[] cookies=request.getCookies();
        String access_token="";
        if(cookies!=null){
            for (Cookie c:cookies){
                if(c.getName().equals("access_token")){
                    access_token=c.getValue();
                    break;
                }
            }
        }
        CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
        if(!access_token.equals("")){

            //添加header
            customHttpServletRequest.addHeader("test", "Bearer "+access_token);


            System.out.println("请求连接中的 "+customHttpServletRequest.getHeader("Authorization"));

        }
//        else{
//            filterChain.doFilter(request, servletResponse);
//        }
        filterChain.doFilter(customHttpServletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}