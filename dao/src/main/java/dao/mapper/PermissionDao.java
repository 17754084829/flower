package dao.mapper;

import dao.model.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface PermissionDao {
    @Select("select permission_list from permission where user_id=#{uid}")
    String getPermissionListByUserID(String uid) throws Exception;
    @Insert("insert into permission (id,permission_list,user_id) values (#{id},#{permission_list},#{user_id})")
    int addPermission(Permission permission);
}
