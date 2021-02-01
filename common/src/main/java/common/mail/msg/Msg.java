package common.mail.msg;

import common.util.DataVerify;

public class Msg {
    private String route="main";
    private Object msg=null;

    public Msg(String route, Object msg) {
        if(!DataVerify.stringIsNotEmpty(route)) {
            System.err.println("路由信息错误");
            return;
        }
        this.route = route;
        this.msg = msg;
    }

    public Msg() {
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        if(!DataVerify.stringIsNotEmpty(route)) {
            System.err.println("路由信息错误");
            return;
        }
        this.route = route;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
