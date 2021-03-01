package dao.mapper;

import dao.model.Addr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddrDao {
    @Select("select * from addr where user_id=#{user_id}")
    List<Addr> getAddrs(String user_id);
    @Insert("insert into addr values(#{id},#{addr},#{user_id},0)")
    int addAddr(Addr addr);
    @Select("select addr from addr where id=#{id}")
    String  getAddr(String id);
}
