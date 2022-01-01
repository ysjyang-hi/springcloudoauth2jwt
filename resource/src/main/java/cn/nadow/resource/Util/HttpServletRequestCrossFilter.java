package cn.nadow.resource.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class HttpServletRequestCrossFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       // ParameterRequestWrapper parameterRequestWrapper = new ParameterRequestWrapper(request);
        CustomHttpServletRequest customHttpServletRequest = new CustomHttpServletRequest(request);
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

        if(!access_token.equals("")){

            //从cookie中取出token，把它设置为请求参数；还有另一种方式，设置请求头Authorization = bearer+空格+token
          //  parameterRequestWrapper.addParameter("access_token",access_token);
            customHttpServletRequest.addHeader("Authorization","bearer "+access_token);

        }else{
            try {
                String requestURI = request.getRequestURI();
                String forwarURI = "/login";
                if(!requestURI.equals(forwarURI)){
                    request.getRequestDispatcher("/login").forward(request, response);
                }


            }catch (IOException e){}
        }
        //把自定义的request传入过滤器链，进入下一个过滤器
        //filterChain.doFilter(parameterRequestWrapper, response);
        filterChain.doFilter(customHttpServletRequest, response);
    }

}