package service;

import dao.model.Goods_car;

import java.util.List;

public interface GoodsCarService {
    List<Goods_car> getAllGoods(String user_id);
    int  updateNum(String id,int goods_num);
    int deleteGoods(String id);
    int insertGoods(Goods_car car);
    Goods_car getGoodsCar(String uid,String goods_id);
    int clearGoodsCar(String user_id,String goods_id);
}
