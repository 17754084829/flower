package common.mail.observer;

import common.mail.msg.Msg;

public abstract class Observer{
    public String route="main";
    public String name="observer";

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void deal(Msg msg);
}
