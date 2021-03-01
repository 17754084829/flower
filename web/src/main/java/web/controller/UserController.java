package web.controller;

import common.util.DataVerify;
import common.util.ReturnMsg;
import common.util.URL;
import common.util.UUIDGenerator;
import dao.model.Addr;
import dao.model.Permission;
import dao.model.Role;
import dao.model.User;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.*;
import sun.nio.cs.ext.MacArabic;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissonService permissonService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AddrService addrService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsCarService goodsCarService;
    @RequestMapping("/getAlluser")
    @ResponseBody
    public List<User> getAllUser()throws Exception {
        return userService.getAllUser();
    }

    @RequestMapping("/login")
    @ResponseBody
    public HashMap<String, Object> login(String userName, String password, HttpSession session) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();
        if (!DataVerify.stringIsNotEmpty(userName) && !DataVerify.stringIsNotEmpty(password)) {
            hashMap.put("code", -1);
            hashMap.put("msg", "账号或密码不能为空");
            return hashMap;
        }
        if (DataVerify.isEmail(userName)) {
            String pass = userService.getPasswordByEmail(userName);
            if (!DataVerify.stringIsNotEmpty(pass) || !pass.equals(password)) {
                hashMap.put("code", -1);
                hashMap.put("msg", "账号或密码错误");
                session.setAttribute("verify", URL.defaultAllowUrl);
            } else {

                hashMap.put("code", 200);
                hashMap.put("msg", "登录成功");
                String uid = userService.getIdByEmail(userName);
                String permission_list = permissonService.getPermissionListByUserID(uid);
                session.setAttribute("verify", permission_list);
                session.setAttribute("uid", uid);
            }
        } else {
            String pass = userService.getPassWordByUserName(userName);
            if (!DataVerify.stringIsNotEmpty(pass) || !pass.equals(password)) {
                hashMap.put("code", -1);
                hashMap.put("msg", "账号或密码错误");
                session.setAttribute("verify", URL.defaultAllowUrl);
            } else {
                hashMap.put("code", 200);
                hashMap.put("msg", "登录成功");
                String uid = userService.getIdByUserNname(userName);
                String permission_list = permissonService.getPermissionListByUserID(uid);
                session.setAttribute("verify", permission_list);
                session.setAttribute("uid", uid);
            }
        }
        return hashMap;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    @Transactional(rollbackFor = RuntimeException.class)
    public HashMap<String, Object> addUser(User user) throws Exception {
        HashMap<String, Object> hashMap = new HashMap();
        int num=-1;
        if(DataVerify.ObjectisNotEmpty(user)) {
            if(DataVerify.stringIsNotEmpty(userService.getUsername(user.getUser_name())))
                return ReturnMsg.MapMsgError("用户名重复");
            if(DataVerify.stringIsNotEmpty(userService.getEmail(user.getEmail())))
                return ReturnMsg.MapMsgError("邮箱号重复");
            user.setId(UUIDGenerator.getUUID());
            user.setRole(UUIDGenerator.getUUID());
            if(user.getIs_admin()==0)
                num=roleService.addRole(new Role(user.getRole(),URL.getDefaultMenuList_user));
            else
                num=roleService.addRole(new Role(user.getRole(),URL.defaultMenuList_admin));
            if(num>0){
                num = userService.addUser(user);
            }

        }
        if (num <= 0) {
            hashMap.put("code", -1);
            hashMap.put("msg", "添加失败");
        } else {
            hashMap.put("code", 200);
            hashMap.put("msg", "添加成功");
            permissonService.addPermission(new Permission(UUIDGenerator.getUUID(),URL.defaultAllowUrl+"/admin.*;",user.getId()));
        }
        return hashMap;
    }
    @RequestMapping("/verify_user_name")
    @ResponseBody
    public Map<String,Object> verify_Username(String user_name) throws Exception{
        Map<String,Object> map=new HashMap<>();
        if(!DataVerify.stringIsNotEmpty(user_name)){
            map.put("code",-1);
            map.put("msg","用户名不能为空");
            return map;
        }
        String username=null;
        String flag=user_name.contains("@")?"邮箱":"用户名";
        if(flag.equals("邮箱"))
            username=userService.getEmail(user_name);
        if(flag.equals("用户名"))
            username=userService.getUsername(user_name);
        if(DataVerify.stringIsNotEmpty(username)){
            map.put("code","-1");
            map.put("msg",flag+"重复");
        }else {
            map.put("code",200);
            map.put("msg",flag+"可用");
        }
        return map;
    }
    @RequestMapping("/alter_password")
    @ResponseBody
    public Map<String,Object> alterPassword(String user_name,String new_password) throws Exception{
        Map<String,Object> map=null;
        if(!DataVerify.stringIsNotEmpty(user_name)||!DataVerify.stringIsNotEmpty(new_password)){
            map= ReturnMsg.MapMsgError("账号或密码不能为空");
            return map;
        }
        String id=null;
        if(user_name.contains("@")){
            id=userService.getIdByEmail(user_name);
        }else{
            id=userService.getIdByUserNname(user_name);
        }
        if(!DataVerify.stringIsNotEmpty(id))
            map=ReturnMsg.MapMsgError("账号不存在");
        else{
            User user=userService.getUserInfoById(id);
            user.setPassword(new_password);
            userService.updateUser(user);
            map=ReturnMsg.MapMsgSuccess("修改密码成功");
        }
        return map;
    }
    @RequestMapping("/exit")
    public String exit_account(HttpSession session){
        session.removeAttribute("verify");
        return "redirect:/login";
    }

    @RequestMapping("/addAddr")
    @ResponseBody
    public Map<String,Object> addAddr(Addr addr,HttpSession session){
        String uid=(String) session.getAttribute("uid");
        addr.setId(UUIDGenerator.getUUID());
        addr.setUser_id(uid);
        addrService.addAddr(addr);
        return ReturnMsg.MapMsgSuccess("SUCCESS");
    }
    @RequestMapping("/getAddrs")
    @ResponseBody
    public Map<String,Object> getAddrs(HttpSession session){
        String uid=(String) session.getAttribute("uid");
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("SUCCESS");
        map.put("data",addrService.getAddrs(uid));
        return map;
    }

    @RequestMapping("/getOrderList")
    @ResponseBody
    public Map<String,Object> getOrderList(HttpSession session){
        String uid=(String) session.getAttribute("uid");
        List<Order> orderList=orderService.getOrders(uid);
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("success");
        map.put("data",orderList);
        return map;
    }


    @RequestMapping("/addOrder")
    @ResponseBody
    public Map<String,Object> addOrder(String ids,Double all_count,String addr_id,HttpSession session) throws Exception{
        String orderId = UUIDGenerator.getUUID();
        String uid=(String)session.getAttribute("uid");
        User user=userService.getUserInfoById(uid);
        if(Double.parseDouble(user.getAccount())<all_count)
            return ReturnMsg.MapMsgSuccess("您的账户余额不足,请联系管理人员充值金额");
        double now_count=Double.parseDouble(user.getAccount())-all_count;
        user.setAccount(Double.toString(now_count));
        userService.updateUser(user);
        for(String id:ids.split(";")){
            if(id.equals(""))
                continue;
            dao.model.Order order=new dao.model.Order();
            order.setId(orderId);
            order.setUser_id(uid);
            order.setGoods_car_id(id);
            order.setAdd_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            order.setAddr_id(addr_id);
            order.setAll_count(all_count.toString());
            orderService.addOrder(order);
            goodsCarService.deleteGoods(id);
        }
        return ReturnMsg.MapMsgSuccess("success");
    }

    @RequestMapping("/getOrderList.html")
    public String getOrderListHtml(){
        return "/admin/Order_list.html";
    }

    @RequestMapping("/getUserById")
    @ResponseBody
    public Map<String,Object> getUserById(String id) throws Exception{
        if(!DataVerify.stringIsNotEmpty(id))
            return ReturnMsg.MapMsgError("参数不能为空");
        Map<String,Object> map=ReturnMsg.MapMsgSuccess("获取成功");
        map.put("data",userService.getUserInfoById(id));
        return map;
    }



    @RequestMapping("/updatePerson")
    @ResponseBody
    public Map<String,Object> updateUser(User user,HttpSession session) throws Exception{
        String uid=(String)session.getAttribute("uid");
        if(!DataVerify.stringIsNotEmpty(uid)){
            return ReturnMsg.MapMsgError("登录状态异常,请重新登录");
        }
        if(uid.equals(user.getId()))
            session.removeAttribute("verify");
        User user1=userService.getUserInfoById(user.getId());
        user.setRole(user1.getRole());
        user.setAble_verify(0);
        userService.updateUser(user);
        return ReturnMsg.MapMsgSuccess("信息修改成功");
    }


    @RequestMapping("/delUser")
    @ResponseBody
    public Map<String,Object> delUser(String  id)throws Exception{
        if(!DataVerify.stringIsNotEmpty(id))
            return ReturnMsg.MapMsgError("参数错误");
        User user=userService.getUserInfoById(id);
        user.setIs_able(0);
        userService.updateUser(user);
        return ReturnMsg.MapMsgSuccess("删除成功");
    }

}
