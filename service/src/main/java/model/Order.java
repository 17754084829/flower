package model;

import dao.model.Goods_car;

import java.util.List;

public class Order {
    private List<List<Goods_car>> goods_cars;
    private String add_time;
    private String addr;
    private String all_count;
    private String id;

    public Order(List<List<Goods_car>> goods_cars, String add_time, String addr, String all_count,String id) {
        this.goods_cars = goods_cars;
        this.add_time = add_time;
        this.addr = addr;
        this.all_count = all_count;
        this.id=id;
    }
    public Order(){}

    public List<List<Goods_car>> getGoods_cars() {
        return goods_cars;
    }

    public void setGoods_cars(List<List<Goods_car>> goods_cars) {
        this.goods_cars = goods_cars;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAll_count() {
        return all_count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAll_count(String all_count) {
        this.all_count = all_count;
    }
}
