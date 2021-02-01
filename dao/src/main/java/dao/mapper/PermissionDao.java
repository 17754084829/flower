package dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface PermissionDao {
    @Select("select permission_list from permission where user_id=#{uid}")
    String getPermissionListByUserID(String uid) throws Exception;
}
