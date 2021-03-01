package dao.mapper;

import dao.model.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;
public interface GoodsDao {
    @Update("update goods set goods_name=#{goods_name},goods_price=#{goods_price},goods_image_url=#{goods_image_url},goods_desc=#{goods_desc},goods_is_able=#{goods_is_able},add_time=#{add_time},drop_time=#{drop_time},number=#{number},type=#{type} where id=#{id}")
    int updateGoods(Goods goods);
    @Insert("insert into goods (id,goods_name,goods_price,goods_image_url,goods_desc,goods_is_able,add_time,drop_time,number,type) values (#{id},#{goods_name},#{goods_price},#{goods_image_url},#{goods_desc},#{goods_is_able},#{add_time},#{drop_time},#{number},#{type})")
    int addGoods(Goods goods);
    @Select("select id,goods_name,goods_price,goods_image_url,goods_desc,goods_is_able,add_time,drop_time,number,type from goods where id=#{id} and goods_is_able=1")
    Goods getGoodsById(String id);
    @Select("select id,goods_name,goods_price,goods_image_url,goods_desc,goods_is_able,add_time,drop_time,number,type from goods where goods_is_able=1")
    List<Goods> getAllGoods();
    @Select("select types from goods_info")
    List<String> getTypes();
    @Insert("insert into goods_info values(#{type})")
    int insertType(String type);
    @Select("select * from goods where goods_is_able=1 and goods_name like concat('%',#{goods_name},'%')")
    List<Goods> searchmode(String goods_name);
    @Select("select * from goods where goods_is_able=1 and type=#{type} limit #{pageStart},4")
    List<Goods> getGoodsByType(String type,int pageStart);
}
