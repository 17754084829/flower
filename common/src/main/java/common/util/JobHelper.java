package common.util;

import common.mail.msg.Msg;
import common.mail.observer.Observer;
import common.mail.observered.Observered;

public class JobHelper {
    public static void subscription(Observer observer){
        Observered.addObserver(observer);
    }
    public static void sendMsg(Msg msg){
        Observered.sendMsg(msg);
    }
}
