package service.impl;

import common.util.DataVerify;
import dao.mapper.UserDao;
import dao.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> getAllUser() throws Exception{
        return userDao.getAllUser();
    }

    @Override
    public String getIdByUserNname(String user_name) throws Exception{
        return userDao.getIdByUserNname(user_name);
    }

    @Override
    public String getIdByEmail(String email)throws Exception {
        return userDao.getIdByEmail(email);
    }

    @Override
    public String getRoleIdByUserId(String id) throws Exception{
        if(!DataVerify.stringIsNotEmpty(id)) {
            throw new Exception("用户主键不能为空");
        }
        return userDao.getRoleIdByUserId(id);
    }

    @Override
    public User getUserInfoById(String id) throws Exception{
        if(!DataVerify.stringIsNotEmpty(id)) {
            throw new Exception("用户主键不能为空");
        }
        return userDao.getUserInfoById(id);
    }

    @Override
    public String getPasswordByEmail(String email) throws Exception {
        return userDao.getPasswordByEmail(email);
    }

    @Override
    public String getPassWordByUserName(String user_name) throws Exception{
        return userDao.getPassWordByUserName(user_name);
    }

    @Override
    public String getUsername(String user_name) throws Exception{
        return userDao.getUsername(user_name);
    }

    @Override
    public String getEmail(String email)throws Exception {
        return userDao.getEmail(email);
    }

    @Override
    public int addUser(User user) throws Exception {
        int num=-1;
        if(DataVerify.stringIsNotEmpty(userDao.getEmail(user.getEmail()))||DataVerify.stringIsNotEmpty(user.getUser_name()))
        {
            log.error("用户名或邮箱地址重复");
            return num;
        }
        return userDao.addUser(user);
    }

    @Override
    public int updateUser(User user) throws Exception {
        int num=-1;
        if(DataVerify.stringIsNotEmpty(userDao.getEmail(user.getEmail()))&&user.getEmail()!= userDao.getUserInfoById(user.getId()).getEmail())
            return num;
        return userDao.updateUser(user);
    }

    @Override
    public int userForbiddenByID(String id) throws Exception {
        return userDao.userForbiddenByID(id);
    }
}
