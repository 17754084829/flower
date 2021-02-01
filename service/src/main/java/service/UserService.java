package service;

import dao.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser() throws Exception;
    String getRoleIdByUserId(String id) throws Exception;
    User getUserInfoById(String id) throws Exception;
    String getPasswordByEmail(String email) throws Exception;
    String getPassWordByUserName(String user_name) throws Exception;
    String  getUsername(String user_name) throws Exception;
    String  getEmail(String email) throws Exception;
    String getIdByUserNname(String user_name) throws Exception;
    String getIdByEmail(String email) throws Exception;
    int addUser(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int userForbiddenByID(String id) throws Exception;
}
