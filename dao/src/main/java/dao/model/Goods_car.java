package dao.model;

public class Goods_car {
    private  String id;
    private String goods_desc;
    private String goods_image_url;
    private String goods_type;
    private double goods_price;
    private int goods_num;
    private int is_delete;
    private String user_id;
    private String goods_id;

    public Goods_car(String id, String goods_desc, String goods_image_url, String goods_type, double goods_price, int goods_num, int is_delete, String user_id,String goods_id) {
        this.id = id;
        this.goods_desc = goods_desc;
        this.goods_image_url = goods_image_url;
        this.goods_type = goods_type;
        this.goods_price = goods_price;
        this.goods_num = goods_num;
        this.is_delete = is_delete;
        this.user_id = user_id;
        this.goods_id=goods_id;
    }
    public  Goods_car(){};

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
