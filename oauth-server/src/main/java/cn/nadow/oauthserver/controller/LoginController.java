package cn.nadow.oauthserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestParam("username") String name,@RequestParam("password") String pwd){
        String str = "" + name + " : " + pwd;
int a=0;
        return null;
    }

}
