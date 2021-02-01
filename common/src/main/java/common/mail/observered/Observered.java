package common.mail.observered;

import common.mail.msg.Msg;
import common.mail.observer.Observer;
import common.util.DataVerify;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Observered {
    private  static HashMap<String, ArrayList<Observer>> observers=new HashMap<>();
    private static ExecutorService executorService =null;
    private static Stack<Msg> msgqueen=new Stack<>();
    static {
        executorService=new ThreadPoolExecutor(25, 25,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new DefaultThreadFactory("work-job--"));
        executorService.submit(()->{listenMsg();});
    }
    public static void sendMsg(Msg msg){
        msgqueen.push(msg);
    }
    public synchronized static void  addObserver(Observer observer) {
        String route = observer.getRoute();
        ArrayList<Observer> observerss=observers.get(route);
        if(observerss==null)
            observerss=new ArrayList<>();
        observerss.add(observer);
        observers.put(route, observerss);
    }
    public static void listenMsg(){
        while (true){
            if(msgqueen.isEmpty())
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            else{
                Msg msg=msgqueen.pop();
                String route=msg.getRoute();
                ArrayList<Observer> observerss=observers.get(route);
                for(Observer ob:observerss){
                    executorService.submit(()->{
                        ob.deal(msg);
                    });
                }
            }
        }
    }

}
