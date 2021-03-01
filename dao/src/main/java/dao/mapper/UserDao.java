package dao.mapper;

import dao.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserDao {
    @Select(value = "select id,user_name,email,role,is_able,image_url,password,verify_code,able_verify,account,is_admin from user where is_able=1")
    List<User> getAllUser() throws Exception;
    @Select("select role from user where id=#{id} and is_able=1")
    String getRoleIdByUserId(String id) throws Exception;
    @Select("select id,user_name,email,role,is_able,image_url,password,verify_code,able_verify,account,is_admin from user where id=#{id} and is_able=1")
    User getUserInfoById(String id) throws Exception;
    @Select("select password from user where email=#{email} and is_able=1")
    String getPasswordByEmail(String email) throws Exception;
    @Select("select password from user where user_name=#{user_name} and is_able=1")
    String getPassWordByUserName(String user_name) throws Exception;
    @Select("select user_name from user where user_name=#{user_name} and is_able=1")
    String  getUsername(String user_name) throws Exception;
    @Select("select email from user where email=#{email} and is_able=1")
    String  getEmail(String email) throws Exception;
    @Select("select id from user where user_name=#{user_name} and is_able=1")
    String getIdByUserNname(String user_name) throws Exception;
    @Select("select id from user where email=#{email} and is_able=1")
    String getIdByEmail(String email) throws Exception;
    @Insert("insert into user (id,user_name,email,role,is_able,image_url,password,verify_code,able_verify,account,is_admin) values (#{id},#{user_name},#{email},#{role},#{is_able},#{image_url},#{password},#{verify_code},#{able_verify},#{account},#{is_admin})")
    int addUser(User user) throws Exception;
    @Update("update user set user_name=#{user_name},email=#{email},role=#{role},is_able=#{is_able},image_url=#{image_url},password=#{password},verify_code=#{verify_code},able_verify=#{able_verify},account=#{account},is_admin=#{is_admin} where id=#{id}")
    int updateUser(User user)throws Exception;
    @Update("update user set is_able=#{is_able} where id=#{id}")
    int userForbiddenByID(String id) throws Exception;
    @Select("select verify_code from user where id=#{id} and is_able=1")
    String getVerify_codeById(String id);
}
