package service.impl;

import common.util.DataVerify;
import dao.mapper.GoodsDao;
import dao.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GoodsService;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public int updateGoods(Goods goods) throws Exception{
        return goodsDao.updateGoods(goods);
    }

    @Override
    public int addGoods(Goods goods) throws Exception{
        if(!DataVerify.ObjectisNotEmpty(goods))
            return -1;
        return goodsDao.addGoods(goods);
    }

    @Override
    public Goods getGoodsById(String id) throws Exception{
        return goodsDao.getGoodsById(id);
    }

    @Override
    public List<Goods> getAllGoods() throws Exception{
        return goodsDao.getAllGoods();
    }

    @Override
    public List<String> getTypes() throws Exception {
        return goodsDao.getTypes();
    }

    @Override
    public int insertType(String type) {
        return goodsDao.insertType(type);
    }

    @Override
    public List<Goods> searchmode(String goods_name) {
        return goodsDao.searchmode(goods_name);
    }

    @Override
    public List<Goods> getGoodsByType(String type, int pageStart) {
        return goodsDao.getGoodsByType(type,pageStart);
    }
}
