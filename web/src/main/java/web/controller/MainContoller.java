package web.controller;

import common.mail.msg.Msg;
import common.model.Mail;
import common.util.JobHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class MainContoller {
    @RequestMapping("/login")
    public String getLoginHtml(){
        return "login/login.html";
    }
    @RequestMapping("/")
    public String getIndex(){
        return "index.html";
    }
    @RequestMapping("/sendMail")
    @ResponseBody
    public HashMap<String,Object> sendMail(){
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("code",200);
        hashMap.put("msg","验证邮件已发送");
        Mail mail=new Mail("1878618369@qq.com","验证邮箱","<p style='width:100%;text-align:center'>验证码为: 888-999</p>");
        JobHelper.sendMsg(new Msg("email",mail));
        return hashMap;
    }
}
