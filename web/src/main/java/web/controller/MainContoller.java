package web.controller;

import common.mail.msg.Msg;
import common.model.Mail;
import common.util.*;
import dao.model.ContentList;
import dao.model.Goods;
import dao.model.User;
import io.netty.util.internal.StringUtil;
import model.PageHeleperInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.ContentListService;
import service.GoodsService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainContoller {
    @Autowired
    private GoodsService service;
    @Autowired
    private UserService userService;
    @Autowired
    private ContentListService contentListService;
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

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile file) throws Exception{
        return FileUtil.upload(file);
    }

    @RequestMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response,String filename) throws Exception{
        FileUtil.download(response,filename);
    }
    @RequestMapping("/get_goods_info")
    @ResponseBody
    public Map<String,Object> getGoodsInfo(){
        Map<String, Object> map=null;
        try {
            map= ReturnMsg.MapMsgSuccess("加载成功");
            map.put("goods", service.getAllGoods());
        }catch (Exception e){
            map=ReturnMsg.MapMsgError("加载失败");
            map.put("goods",new ArrayList<Goods>());
        }
        return map;
    }

    @RequestMapping("/getTypes")
    @ResponseBody
    public Map<String,Object> getTypes() throws Exception{
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("获取成功");
        map.put("type",service.getTypes());
        return map;
    }
    @RequestMapping("/getGoodsById")
    @ResponseBody
    public Map<String,Object> getGoodsById(String id) throws Exception{
        if(!DataVerify.stringIsNotEmpty(id))
            return ReturnMsg.MapMsgError("查询条件错误");
        Goods goods=service.getGoodsById(id);
        Map<String,Object> ret=ReturnMsg.MapMsgSuccess("获取成功");
        ret.put("goods",goods);
        return ret;
    }
    @RequestMapping("/getGoods_details")
    public String getGoodsDetails(Model model,String goods_id)throws Exception{
        if(!DataVerify.stringIsNotEmpty(goods_id))
            return "404.html";
        Goods goods=service.getGoodsById(goods_id);
        model.addAttribute("goods_id",goods_id);
        model.addAttribute("image_url",goods.getGoods_image_url());
        model.addAttribute("goods_desc",goods.getGoods_desc());
        model.addAttribute("goods_price",goods.getGoods_price());
        return "Goods_details.html";
    }


    @RequestMapping("/getContentList")
    @ResponseBody
    public PageHeleperInfo<ContentList> getContentList(int pageStart,String goods_id) throws Exception{
        if(pageStart<0||!DataVerify.stringIsNotEmpty(goods_id))
            return new PageHeleperInfo<>();
        PageHeleperInfo<ContentList> pageHeleperInfo=contentListService.getContentListGroup(pageStart,goods_id);
        for(ContentList list:pageHeleperInfo.getData()){
            User user= userService.getUserInfoById(list.getContent_subject_id());
            list.setImage_url(user.getImage_url());
            list.setUser_name(user.getUser_name());
            if(!DataVerify.stringIsNotEmpty(list.getContent_object_id())) {
                list.setObject_name(user.getUser_name());
                continue;
            }
            list.setObject_name(userService.getUserInfoById(list.getContent_object_id()).getUser_name());
        }
        return pageHeleperInfo;
    }
    @RequestMapping("/search_key")
    @ResponseBody
    public List<Goods> searchModeGoods(String keyWord) {
        if (!DataVerify.stringIsNotEmpty(keyWord))
            return new ArrayList<>();
        return service.searchmode(keyWord);
    }
    @RequestMapping("/getGoodsByType")
    @ResponseBody
    public List<Goods> getGoodsByType(String type,int pageStart){
        return service.getGoodsByType(type,pageStart*4);
    }

}
