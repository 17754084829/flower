package common.util;

import java.util.ArrayList;
import java.util.List;

public class PageHelper{
    public static   List<Object> dealPage(List<Object> list, int pageStart, int pageSize){
        List<Object> list1=new ArrayList<>();
        if(list.size()-1<pageStart)
            return list1;
        if(list.size()>pageStart+pageSize){
            return list.subList(pageStart,pageStart+pageSize);
        }
        else
            return list.subList(pageStart,list.size());
    }
}
