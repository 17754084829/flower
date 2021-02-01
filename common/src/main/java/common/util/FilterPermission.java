package common.util;

import java.util.ArrayList;
import java.util.HashSet;

public class FilterPermission {
    public static HashSet<String> getPermissionList(String permission){
        HashSet<String> hashSet=new HashSet<>();
        permission=permission.trim();
        String[] strings=permission.split(";");
        for(String str:strings){
            hashSet.add(str);
        }
        return hashSet;
    }
    public static boolean hasPermission(String path,HashSet<String> permissions){
        boolean hasPermission=false;
        if(!DataVerify.stringIsNotEmpty(path)){
            return hasPermission;
        }
        for(String permission:permissions){
            if(path.matches(permission)){
                hasPermission=true;
                break;
            }
        }
        return hasPermission;
    }
}
