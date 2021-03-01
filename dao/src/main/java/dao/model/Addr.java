package dao.model;

public class Addr {
    private String id;
    private  String user_id;
    private String addr;
    private String is_default;

    public Addr(String id, String user_id, String addr, String is_default) {
        this.id = id;
        this.user_id = user_id;
        this.addr = addr;
        this.is_default = is_default;
    }
    public  Addr(){}

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
