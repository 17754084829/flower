package common.util;

import java.util.HashMap;

public class ReturnMsg {
    public static HashMap<String,Object> MapMsgSuccess(String msg){
        HashMap hashMap=new HashMap<String,Object>();
        hashMap.put("code",200);
        hashMap.put("msg",msg);
        return hashMap;
    }
    public static HashMap<String,Object> MapMsgError(String msg){
        HashMap hashMap=new HashMap<String,Object>();
        hashMap.put("code",-1);
        hashMap.put("msg",msg);
        return hashMap;
    }
}
