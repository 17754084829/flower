package service.impl;

import dao.mapper.AddrDao;
import dao.mapper.GoodsCarDao;
import dao.mapper.OrderDao;
import dao.model.Goods_car;
import model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GoodsCarService;
import service.OrderService;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao dao;
    @Autowired
    private GoodsCarDao goodsCarDao;
    @Autowired
    private AddrDao addrDao;
    @Override
    public List<Order> getOrders(String uid) {
        List<String> ids=dao.getId(uid);
        List<Order> orderList=new ArrayList<>();
        for(String id:ids){
            Order order=new Order();
            List<List<Goods_car>> lists=new ArrayList<>();
            List<Goods_car> list=new ArrayList<>();
            List<dao.model.Order> orders= dao.getList(id);
            for(dao.model.Order order1:orders){
                list.add(goodsCarDao.getGoodcarContanier(order1.getGoods_car_id()));
                order.setAdd_time(order1.getAdd_time());
                order.setAddr(addrDao.getAddr(order1.getAddr_id()));
                order.setAll_count(order1.getAll_count());
                order.setId(order1.getId());
            }
            lists.add(list);
            order.setGoods_cars(lists);
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public int addOrder(dao.model.Order order) {
        return dao.addOrder(order);
    }
}
