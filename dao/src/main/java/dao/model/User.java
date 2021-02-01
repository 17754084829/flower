package dao.model;

import common.annotion.NotEmpty;

import java.io.Serializable;

public class User implements Serializable {
    private static final Long SID=2L;
    private String id;
    @NotEmpty
    private String user_name;
    @NotEmpty
    private String email;
    @NotEmpty
    private Integer is_able;
    private String role;
    @NotEmpty
    private String image_url;
    @NotEmpty
    private String password;
    private String verify_code;

    public User() {
    }

    public User(String id, String user_name, String email, Integer is_able, String role, String image_url, String password, String verify_code) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.is_able = is_able;
        this.role = role;
        this.image_url = image_url;
        this.password = password;
        this.verify_code = verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIs_able() {
        return is_able;
    }

    public void setIs_able(Integer is_able) {
        this.is_able = is_able;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
