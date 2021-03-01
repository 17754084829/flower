package web.controller;

import common.util.DataVerify;
import common.util.ReturnMsg;
import common.util.URL;
import common.util.UUIDGenerator;
import dao.mapper.ContentDao;
import dao.model.*;
import model.PageHeleperInfo;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.*;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissonService permissonService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ContentListService contentListService;
    @Autowired
    private GoodsCarService goodsCarService;
    @RequestMapping("/index")
    public String getAdminIndexPage(){
        return "admin/admin_index.html";
    }
    @RequestMapping("/welecome.html")
    public String getAdminWelecomePage(){
        return "admin/welcome.html";
    }
    @RequestMapping("/alter_my_info.html")
    public String getMyInfoPage(){
        return "admin/alter_my_info.html";
    }
    @RequestMapping("/goods_infos.html")
    public String getGoodsInfoPage(){
        return "admin/goods_infos.html";
    }
    @RequestMapping("/getAdminInfo")
    @ResponseBody
    public Map<String,Object> getAdminInfo(HttpSession session)throws Exception{
        Map<String,Object> map=null;
        String uid=(String) session.getAttribute("uid");
        User user=userService.getUserInfoById(uid);
        map=new HashMap<>();
        map.put("user",user);
        map.put("image_url",user.getImage_url());
        map.put("menu_list",URL.dealMenu(roleService.getRoleEntryById(user.getRole())));
        map.put("code",200);
        return map;
    }
    @RequestMapping("/able_verify_code")
    @ResponseBody
    public Map<String,Object> start_able_verifyCode(HttpSession session) throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录状态异常,请重新登录");
        }
        String verify_code=userService.getVerify_codeById(uid);
        if(!DataVerify.stringIsNotEmpty(verify_code)||verify_code.length()!=32) {
            User user=userService.getUserInfoById(uid);
            verify_code=UUIDGenerator.getUUID();
            user.setVerify_code(verify_code);
            userService.updateUser(user);
        }
        User user=userService.getUserInfoById(uid);
        user.setAble_verify(1);
        userService.updateUser(user);
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("开启成功,请妥善保管");
        map.put("verify_code",verify_code);
        return map;
    }
    @RequestMapping("/shutdown_verify_code")
    @ResponseBody
    public Map<String,Object> shutdown_verify_code(HttpSession session)throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录状态异常,请重新登录");
        }
        User user=userService.getUserInfoById(uid);
        user.setAble_verify(0);
        userService.updateUser(user);
        return ReturnMsg.MapMsgSuccess("关闭成功");
    }
    @RequestMapping("/is_able_verify")
    @ResponseBody
    public Map<String,Object> is_able_verify(HttpSession session) throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录状态异常,请重新登录");
        }
        User user=userService.getUserInfoById(uid);
        int able=user.getAble_verify();
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("开启/关闭");
        map.put("able",able==1?true:false);
        map.put("verify_code",user.getVerify_code());
        return map;
    }
    @RequestMapping("/update_admin_info")
    @ResponseBody
    public Map<String,Object> updateUser(User user,HttpSession session) throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录状态异常,请重新登录");
        }
        User user1=userService.getUserInfoById(uid);
        if(DataVerify.stringIsNotEmpty(user.getUser_name()))
            user1.setUser_name(user.getUser_name());
        if(DataVerify.stringIsNotEmpty(user.getEmail()))
            user1.setEmail(user.getEmail());
        if(DataVerify.stringIsNotEmpty(user.getPassword()))
            user1.setPassword(user.getPassword());
        if(DataVerify.stringIsNotEmpty(user.getImage_url()))
            user1.setImage_url(user.getImage_url());
        userService.updateUser(user1);
        return ReturnMsg.MapMsgSuccess("信息修改成功");
    }

    @RequestMapping("/insertType")
    @ResponseBody
    public  Map<String,Object> insertType(String type)throws Exception{
        if(!DataVerify.stringIsNotEmpty(type))
            return ReturnMsg.MapMsgError("添加失败");
        List<String> list=goodsService.getTypes();
        for(String info:list){
            if(info.equals(type))
                return ReturnMsg.MapMsgError("类型已存在");
        }
            goodsService.insertType(type);
            return ReturnMsg.MapMsgSuccess("添加成功");
    }
    @RequestMapping("/addGoods")
    @ResponseBody
    public Map<String,Object> addGoods(Goods goods,String flag) throws Exception{
        if(!"alter".equals(flag))
            goods.setId(UUIDGenerator.getUUID());
        if(!DataVerify.ObjectisNotEmpty(goods)){
            return ReturnMsg.MapMsgError("属性值不能为空");
        }
        if("alter".equals(flag))
            goodsService.updateGoods(goods);
        else
            goodsService.addGoods(goods);
        return ReturnMsg.MapMsgSuccess("添加/更新成功");
    }
    @RequestMapping("/delGoods")
    @ResponseBody
    public Map<String,Object> delGoods(String id) throws Exception{
        if(!DataVerify.stringIsNotEmpty(id))
            return ReturnMsg.MapMsgError("参数错误");
        Goods goods=goodsService.getGoodsById(id);
        goods.setGoods_is_able(0);
        goodsService.updateGoods(goods);
        return ReturnMsg.MapMsgSuccess("删除成功");
    }

    @RequestMapping("/addContent")
    @ResponseBody
    public  Map<String,Object> addContent(HttpSession session,ContentList contentList) throws Exception{
        String uid=(String) session.getAttribute("uid");
        contentList.setIs_delete(0);
        if(DataVerify.stringIsNotEmpty(contentList.getId()))
            contentListService.UpdateIsReply(contentList.getId());
        contentList.setId(UUIDGenerator.getUUID());
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("请先登录,再进行评论");
        }
        contentList.setContent_subject_id(uid);
        if(!DataVerify.ObjectisNotEmpty(contentList))
            return ReturnMsg.MapMsgError("属性值错误");
        String nowDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        contentList.setAdd_time(nowDate);
        if(!DataVerify.stringIsNotEmpty(contentList.getContent_object_id())) {
            contentList.setGroup_id(UUIDGenerator.getUUID());
            contentList.setGroup_create_id(contentList.getContent_subject_id());
        }
        if(DataVerify.stringIsNotEmpty(contentList.getContent_object_id())&&uid.equals(contentList.getContent_object_id()))
            contentList.setIs_reply(1);
        else
            contentList.setIs_reply(0);
        contentListService.insertContent(contentList);
        return ReturnMsg.MapMsgSuccess("添加成功");
    }
    @RequestMapping("deleteContenr")
    @ResponseBody
    public Map<String,Object> deleteContent(ContentList contentList,HttpSession session){
        String uid=(String) session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("请先登录,再进行评论");
        }
        if(!DataVerify.stringIsNotEmpty(contentList.getGroup_id())||!DataVerify.stringIsNotEmpty(contentList.getGroup_create_id()))
            return ReturnMsg.MapMsgError("参数异常");
        if(uid.equals(contentList.getGroup_create_id())){
           contentListService.updateDelete(contentList.getGroup_id(),contentList.getGoods_id());
        }else{
            contentListService.updateDeleteContenrIngGroup(contentList.getId(),contentList.getGoods_id());
        }
        return ReturnMsg.MapMsgSuccess("删除成功");
    }
    @RequestMapping("/getNotReply")
    @ResponseBody
    public Map<String,Object> getNotReplyMsg(HttpSession session) throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("请先登录,再进行评论");
        }
        List<ContentList> lists=contentListService.getNotReply(uid);
        for(ContentList list:lists){
            Goods goods=goodsService.getGoodsById(list.getGoods_id());
            User user=userService.getUserInfoById(list.getContent_subject_id());
            list.setGoods_name(goods.getGoods_name());
            list.setGoods_image_url(goods.getGoods_image_url());
            list.setUser_name(user.getUser_name());
        }
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("操作成功");
        map.put("data",lists);
        return map;
    }


    @RequestMapping("/getNotReplyCount")
    @ResponseBody
    public Map<String,Object> getNotReplyCount(HttpSession session){
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录超时,请重新登录");
        }
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("获取成功");
        map.put("data",contentListService.getNotReplyCount(uid));
        return map;
    }
    @RequestMapping("/getNotReply.html")
    public  String  getNoReplyHtml(){
        return "/admin/NotReplyMsg.html";
    }
    @RequestMapping("/getGoods_car.html")
    public  String  getGoods_carHtml(){
        return "/admin/goods_car.html";
    }

    @RequestMapping("/addGoodsCar")
    @ResponseBody
    public Map<String,Object> addGoodsCar(String goods_id,int goods_num,HttpSession session)throws Exception{
        String uid=(String) session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录超时,请重新登录");
        }
        if(!DataVerify.stringIsNotEmpty(goods_id)){
            return ReturnMsg.MapMsgError("参数错误");
        }
        Goods_car car=new Goods_car();
        car.setId(UUIDGenerator.getUUID());
        Goods goods=goodsService.getGoodsById(goods_id);
        car.setGoods_desc(goods.getGoods_desc());
        car.setGoods_image_url(goods.getGoods_image_url());
        car.setGoods_num(goods_num);
        car.setGoods_price(goods.getGoods_price());
        car.setGoods_type(goods.getType());
        car.setUser_id(uid);
        car.setGoods_id(goods_id);
        goodsCarService.insertGoods(car);
        Map<String,Object> map= ReturnMsg.MapMsgSuccess("添加成功");
        map.put("id",car.getId());
        return map;
    }

    @RequestMapping("/addCarnum")
    @ResponseBody
    public Map<String,Object> addCarNum(String id,int goods_num){
        if(!DataVerify.stringIsNotEmpty(id)){
            return ReturnMsg.MapMsgError("参数错误");
        }
        goodsCarService.updateNum(id,goods_num);
        return ReturnMsg.MapMsgSuccess("添加成功");
    }
    @RequestMapping("/deletecar")
    @ResponseBody
    public Map<String,Object> deleteCar(String id){
        if(!DataVerify.stringIsNotEmpty(id)){
            return ReturnMsg.MapMsgError("参数错误");
        }
        String[] ids=id.split(";");
        if(ids.length>1){
            for(String i:ids){
                if(!DataVerify.stringIsNotEmpty(i))
                    continue;
                goodsCarService.deleteGoods(i);
            }
        }
        if(ids.length==1)
            goodsCarService.deleteGoods(id);
        return ReturnMsg.MapMsgSuccess("删除成功");
    }

    @RequestMapping("/getGoodsCar")
    @ResponseBody
    public Map<String,Object> getGoodsCar(HttpSession session){
        String uid=(String) session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录超时,请重新登录");
        }
        List<Goods_car> list=goodsCarService.getAllGoods(uid);
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("获取成功");
        map.put("data",list);
        return map;
    }

    @RequestMapping("/getGoodsCars.html")
    public String getGoodsCars(HttpSession session){
        String uid=(String) session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return "redirect:/login";
        }
        return "/admin/goods_car.html";
    }

    @RequestMapping("/initGoodsCar")
    @ResponseBody
    public Map<String,Object> initGoodsCar(String goods_id,HttpSession session){
        String uid=(String) session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("权限错误");
        }
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("success");
        map.put("data",goodsCarService.getGoodsCar(uid,goods_id));
        return map;
    }


    @RequestMapping("/person_infos.html")
    public String getPerson_infos(){
        return "/admin/person_infos.html";
    }



}

