package dao.model;

import common.annotion.NotEmpty;

public class Permission {
    @NotEmpty
    private  String id;
    @NotEmpty
    private  String permission_list;
    @NotEmpty
    private String user_id;

    public Permission(String id, String permission_list, String user_id) {
        this.id = id;
        this.permission_list = permission_list;
        this.user_id = user_id;
    }

    public Permission() {
    }

    public String getUsername() {
        return id;
    }

    public void setUsername(String id) {
        this.id = id;
    }

    public String getPermission_list() {
        return permission_list;
    }

    public void setPermission_list(String permission_list) {
        this.permission_list = permission_list;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
