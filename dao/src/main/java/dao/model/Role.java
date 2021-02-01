package dao.model;

import java.io.Serializable;

public class Role implements Serializable {
    private static final Long SID=1l;
    private String id;
    private String menu;

    public void setId(String id) {
        this.id = id;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public Role(String id, String menu) {
        this.id = id;
        this.menu = menu;
    }

    public String getMenu() {
        return menu;
    }
    public  Role(){}

}
