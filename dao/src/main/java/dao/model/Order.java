package dao.model;

public class Order {
    private String  id;
    private   String user_id;
    private String goods_car_id;
    private String add_time;
    private String all_count;
    private String addr_id;
    public  Order(){}
    public Order(String id, String user_id, String goods_car_id, String add_time, String all_count, String addr_id) {
        this.id = id;
        this.user_id = user_id;
        this.goods_car_id = goods_car_id;
        this.add_time = add_time;
        this.all_count = all_count;
        this.addr_id = addr_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_car_id() {
        return goods_car_id;
    }

    public void setGoods_car_id(String goods_car_id) {
        this.goods_car_id = goods_car_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAll_count() {
        return all_count;
    }

    public void setAll_count(String all_count) {
        this.all_count = all_count;
    }

    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
    }
}
