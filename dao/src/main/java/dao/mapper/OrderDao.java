package dao.mapper;

import dao.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDao {
    @Select("select * from order_list where id=#{id}")
    List<Order> getList(String user_id);
    @Select("select distinct id from order_list where user_id=#{uid}")
    List<String> getId(String uid);
    @Insert("insert into order_list values (#{id},#{goods_car_id},#{user_id},#{all_count},#{addr_id},#{add_time})")
    int addOrder(Order order);
}
