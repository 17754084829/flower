package dao.model;

import common.annotion.NotEmpty;

public class Goods {
    @NotEmpty
    private String  id;
    @NotEmpty
    private String goods_name;
    @NotEmpty
    private String goods_image_url;
    @NotEmpty
    private Double goods_price;
    @NotEmpty
    private String goods_desc;
    @NotEmpty
    private int goods_is_able;
    @NotEmpty
    private String add_time;
    @NotEmpty
    private String drop_time;
    @NotEmpty
    private int number;
    @NotEmpty
    private String type;

    public Goods(String id, String goods_name, String goods_image_url, Double goods_price, String goods_desc, int goods_is_able, String add_time, String drop_time, int number, String type) {
        this.id = id;
        this.goods_name = goods_name;
        this.goods_image_url = goods_image_url;
        this.goods_price = goods_price;
        this.goods_desc = goods_desc;
        this.goods_is_able = goods_is_able;
        this.add_time = add_time;
        this.drop_time = drop_time;
        this.number = number;
        this.type = type;
    }

    public Goods() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public int getGoods_is_able() {
        return goods_is_able;
    }

    public void setGoods_is_able(int goods_is_able) {
        this.goods_is_able = goods_is_able;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDrop_time() {
        return drop_time;
    }

    public void setDrop_time(String drop_time) {
        this.drop_time = drop_time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
