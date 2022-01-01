package cn.nadow.resource;


import cn.nadow.resource.Util.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@RestController
@RequestMapping("/demo")
public class DemoTestController {
    @RequestMapping("test")
    public String get() {
        String str=null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String url="http://localhost:9999/oauth/token";
        str=String.format("client_secret=abcxyz&grant_type=password&username=%s&password=%s&client_id=client_1", "qq","a");
        String s= HttpRequest.sendGet(url, str);
        JSONObject jsonObject=JSONObject.parseObject(s);
//        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
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
//        JSONArray jsonarray=new JSONArray();
//        jsonarray.add(jsonObject);
//        for(int i = 0; i < jsonarray.size(); i++) {
//            JSONObject obj = jsonarray.getJSONObject(i);
//
//            Integer del = (Integer) obj.get("del");
//            String bookId = (String) obj.get("id");
//        }


        String access_token=jsonObject.getString("access_token");

        try {


            DecodedJWT decode = JWT.decode(access_token);
        String header=    decode.getHeader();

        } catch ( SignatureVerificationException | JWTDecodeException e) {

        }

        Cookie c =new Cookie("access_token",access_token);
            c.setPath("/");
            response.addCookie(c);
//try{
//response.getWriter().write("");}
//catch (IOException e){}
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
//            // 建立实际的连接
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
//
//        System.out.println(result);

        return "demo ..." ;
    }
}
