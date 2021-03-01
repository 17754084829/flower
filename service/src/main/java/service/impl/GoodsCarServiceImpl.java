package service.impl;

import dao.mapper.GoodsCarDao;
import dao.model.Goods_car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GoodsCarService;

import java.util.List;
@Service
public class GoodsCarServiceImpl implements GoodsCarService {
    @Autowired
    private GoodsCarDao dao;
    @Override
    public List<Goods_car> getAllGoods(String user_id) {
        return dao.getAllGoods(user_id);
    }

    @Override
    public int updateNum(String id, int goods_num) {
        return dao.updateNum(id,goods_num);
    }

    @Override
    public int deleteGoods(String id) {
        return dao.deleteGoods(id);
    }

    @Override
    public int insertGoods(Goods_car car) {
        String id=dao.getCar(car.getGoods_id(),car.getUser_id());
        if(id==null)
            return dao.insertGoods(car);
        return -1;
    }

    @Override
    public Goods_car getGoodsCar(String uid,String goods_id) {
        String ids=dao.getCar(goods_id,uid);
        if(ids==null)
            return new Goods_car();
        return dao.getGoodcar(ids);
    }

    @Override
    public int clearGoodsCar(String user_id, String goods_id) {
        return dao.clearGoodsCar(user_id,goods_id);
    }
}
