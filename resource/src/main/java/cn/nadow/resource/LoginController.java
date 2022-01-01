package cn.nadow.resource;

import cn.nadow.resource.Util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public ModelAndView login(){
        // 获得request对象,response对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
      String name=  request.getParameter("username");
        String pwd=  request.getParameter("password");
        //  String str = "" + name + " : " + pwd;
int a=0;
//http://localhost:9999/oauth/token?client_secret=abcxyz&grant_type=password&username=qq&password=a&client_id=client_1
if(name!=null&&pwd!=null&&!name.equals("")&&!pwd.equals("")){
    String str=null;

    String url="http://localhost:9999/oauth/token";
    str=String.format("client_secret=abcxyz&grant_type=password&username=%s&password=%s&client_id=client_1", name,pwd);
    String s= HttpRequest.sendGet(url, str);
    JSONObject jsonObject=JSONObject.parseObject(s);
    int exp=jsonObject.getInteger("expires_in");
    String access_token=jsonObject.getString("access_token");
    Cookie c1 =new Cookie("access_token",access_token);

//设置Cookie最大生存时间,以秒为单位,负数的话为浏览器进程,关闭浏览器Cookie消失
    c1.setMaxAge(exp);//设置cookie过期时间为1个小时
//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
    c1.setPath("/");
// 将Cookie添加到Response中,使之生效
    response.addCookie(c1);
}

//                Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
//        Iterator<Map.Entry<String, Object>> it = entries.iterator();
//        String param="";
//
//        while (it.hasNext()) {
//            Map.Entry<String, Object> next = it.next();
//            String key = next.getKey();
//            Object value = next.getValue();
//            param += key + "=" + value+" ";
//            Cookie c =new Cookie(key,value.toString());
//            c.setPath("/");
//            response.addCookie(c);
//        }
//        String access_token=jsonObject.getString("access_token");
//        Cookie c =new Cookie("access_token",access_token);
      //  response.addCookie(c);

//        String testapi="http://localhost:9998/api/test";
//        //--------------------
//        String result = "";
//        BufferedReader in = null;
//        try {
//
//            URL realUrl = new URL(testapi);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("Authorization",
//                    "Bearer "+access_token);
//              // 建立实际的连接
//            connection.connect();
//            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        //-----------------
//        try {
//            response.getWriter().write("aaaa");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(result);
//        try {
//            response.sendRedirect("/hello.html");
//        }catch (IOException e){
//            System.out.println(e.toString());
//        }
        StringBuffer rurl = request.getRequestURL();
       // String tempContextUrl = rurl.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getSession().getServletContext().getContextPath()).append("/").toString();
        String tempContextUrl = rurl.delete(rurl.length() - request.getRequestURI().length(), rurl.length()).append("/").toString();
        tempContextUrl=tempContextUrl+"api/test";
//        response.sendRedirect("/LoginError.html");
     //   return "登录成功";
        ModelAndView mv=new ModelAndView();
        int logined=1;
        String msg="登陆成功";
        mv.addObject("logined",logined);

        mv.setViewName("login.html");
        return  mv;
    }
    @RequestMapping("/exit")
    public ModelAndView logout(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        ModelAndView mv=new ModelAndView();
        int logined=0;
        Cookie c1 =new Cookie("access_token",null);
        c1.setMaxAge(0);
        response.addCookie(c1);
        String msg="退出成功";
        mv.addObject("logined",logined);

        mv.setViewName("index.html");
        return  mv;
    }
    @RequestMapping("/test")
    public ModelAndView test(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        Cookie[] cookies=request.getCookies();
        String token="";
        for (Cookie c:cookies){
            if(c.getName().equals("access_token")){
                token=c.getValue();
                break;
            }
        }
        boolean bVerify=true;
        if(!token.equals("")){
            String sign_key = "test123";
            MacSigner macSigner=  new MacSigner(sign_key);

            Jwt jwt=JwtHelper.decode(token);
            String s=jwt.getClaims();

            try {
                jwt.verifySignature(macSigner);
            }catch (InvalidSignatureException e){
                bVerify=false;
            }
        }else {
            try {
                response.sendRedirect("/");
            }catch (IOException e){}
        }
        ModelAndView mv=new ModelAndView();
        int logined=1;
        String msg="登陆成功";
        mv.addObject("logined",logined);

        mv.setViewName("login.html");
        return  mv;
    }
    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView();
        int logined=1;
        String msg="登陆成功";
        mv.addObject("logined",logined);

        mv.setViewName("index.html");
        return  mv;
    }
}
