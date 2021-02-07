package web.controller;

import common.mail.msg.Msg;
import common.model.Mail;
import common.util.DataVerify;
import common.util.JobHelper;
import common.util.VerifyCode;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainContoller {
    @RequestMapping("/login")
    public String getLoginHtml() {
        return "login/login.html";
    }
    @RequestMapping("/")
    public String getIndex(){
        return "index.html";
    }
    @RequestMapping("/sendMail")
    @ResponseBody
    public HashMap<String,Object> sendMail(String email, HttpServletRequest request){
        HashMap<String,Object> hashMap=new HashMap<>();
        if(!DataVerify.stringIsNotEmpty(email)){
            hashMap.put("code",-1);
            hashMap.put("msg","电子邮箱地址不能为空");
            return hashMap;
        }
        String verifyCode=VerifyCode.GenerateCode();
        hashMap.put("code",200);
        hashMap.put("msg","验证邮件已发送");
        String content="<p style='width:100%;text-align:center'>该邮件为花艺馆账号验证码</p>";
        Mail mail=new Mail(email,"验证邮箱",content+"<p style='width:100%;text-align:center'>验证码为:"+verifyCode+"</p>");
        JobHelper.sendMsg(new Msg("email",mail));
        request.getSession().setAttribute("verifyCode",verifyCode);
        return hashMap;
    }
    @RequestMapping("/verifyCode")
    @ResponseBody
    public Map<String,Object> verifyCode(String code, HttpSession session){
        Map<String,Object> map=new HashMap<>();
        String verifyCode=(String) session.getAttribute("verifyCode");
        if(!DataVerify.stringIsNotEmpty(verifyCode)||!DataVerify.stringIsNotEmpty(code)){
            map.put("code",-1);
            map.put("msg","状态异常");
            return map;
        }
        if(verifyCode.equals(code)){
            map.put("code",200);
            map.put("msg","验证成功");
        }else{
            map.put("code",-1);
            map.put("msg","验证码错误或失效");
        }
        return map;
    }

}
