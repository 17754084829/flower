package web.controller;

import common.util.DataVerify;
import common.util.ReturnMsg;
import common.util.URL;
import common.util.UUIDGenerator;
import dao.model.Permission;
import dao.model.Role;
import dao.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.PermissonService;
import service.RoleService;
import service.UserService;

import javax.servlet.http.HttpSession;
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

    public List<User> getAllUser() {
        return null;
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
            user.setId(UUIDGenerator.getUUID());
            user.setRole(UUIDGenerator.getUUID());
            num=roleService.addRole(new Role(user.getRole(),"<li>修改密码</li>"));
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
        if(user_name.equals("邮箱"))
            username=userService.getEmail(user_name);
        if(user_name.equals("用户名"))
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
}
