package dao.mapper;

import dao.model.Goods;
import dao.model.Goods_car;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsCarDao {
    @Select("select * from goods_car where is_delete=0 and user_id=#{user_id}")
    List<Goods_car> getAllGoods(String user_id);
    @Update("update goods_car set goods_num=#{goods_num} where id=#{id}")
    int  updateNum(String id,int goods_num);
    @Update("update goods_car set is_delete=1 where id=#{id}")
    int deleteGoods(String id);
    @Insert("insert into goods_car values(#{id},#{goods_desc},#{goods_image_url},#{goods_type},#{goods_price},#{goods_num},0,#{user_id},#{goods_id})")
    int insertGoods(Goods_car car);
    @Select("select id from goods_car where goods_id=#{goods_id} and user_id=#{uid} and is_delete=0")
    String getCar(String goods_id,String uid);
    @Select("select * from goods_car where id=#{id} and is_delete=0")
    Goods_car getGoodcar(String id);
    @Select("select * from goods_car where id=#{id}")
    Goods_car getGoodcarContanier(String id);
    @Update("update goods_car set is_delete=1 where user_id=#{user_id} and goods_id=#{goods_id}")
    int clearGoodsCar(String user_id,String goods_id);
}
