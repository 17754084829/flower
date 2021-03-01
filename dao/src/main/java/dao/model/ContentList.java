package dao.model;

import common.annotion.NotEmpty;

public class ContentList {
    private String id;
    @NotEmpty
    private String content_subject_id;
    private String content_object_id;
    @NotEmpty
    private String content;
    private String add_time;
    private String group_id;
    private String group_create_id;
    private int is_delete;
    private String goods_id;
    private String image_url;
    private String user_name;
    private String object_name;
    private int is_reply;
    private String goods_image_url;
    private String goods_name;

    public ContentList(String id, String content_subject_id, String content_object_id, String content, String add_time, String group_id, String group_create_id, int is_delete,String goods_id,String image_url,String user_name,String object_name,int is_reply,String goods_name,String goods_image_url) {
        this.id = id;
        this.content_subject_id = content_subject_id;
        this.content_object_id = content_object_id;
        this.content = content;
        this.add_time = add_time;
        this.group_id = group_id;
        this.group_create_id = group_create_id;
        this.is_delete = is_delete;
        this.goods_id=goods_id;
        this.image_url=image_url;
        this.user_name=user_name;
        this.object_name=object_name;
        this.is_reply=is_reply;
        this.goods_name=goods_name;
        this.goods_image_url=goods_image_url;

    }
    public  ContentList(){}

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setIs_reply(int is_reply) {
        this.is_reply = is_reply;
    }

    public int getIs_reply() {
        return is_reply;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_subject_id() {
        return content_subject_id;
    }

    public void setContent_subject_id(String content_subject_id) {
        this.content_subject_id = content_subject_id;
    }

    public String getContent_object_id() {
        return content_object_id;
    }

    public void setContent_object_id(String content_object_id) {
        this.content_object_id = content_object_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_create_id() {
        return group_create_id;
    }

    public void setGroup_create_id(String group_create_id) {
        this.group_create_id = group_create_id;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public String getImage_url() {
        return image_url;
    }
}
