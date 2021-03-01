package dao.mapper;

import dao.model.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.annotation.Resource;
@Mapper
public interface RoleDao {
    @Select(value = "select menu from role where id=#{id}")
    String getRoleEntryById(String id) throws Exception;
    @Insert(value = "insert into role (id,menu) values (#{id},#{menu})")
    int addRole(Role role) throws Exception;
    @Update(value = "update role set menu=#{menu} where id=#{id}")
    int update(Role role);
}
