package service;

import dao.model.Goods;

import java.util.List;

public interface GoodsService {
    int updateGoods(Goods goods) throws Exception;
    int addGoods(Goods goods) throws Exception;
    Goods getGoodsById(String id) throws  Exception;
    List<Goods> getAllGoods() throws Exception;
    List<String> getTypes() throws Exception;
    int insertType(String type);
    List<Goods> searchmode(String goods_name);
    List<Goods> getGoodsByType(String type,int pageStart);
}
