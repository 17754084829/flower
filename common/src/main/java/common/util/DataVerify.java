package common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class DataVerify {
    public static boolean stringIsNotEmpty(String obj){
        if(obj==null)
            return false;
        if("".equals(obj))
            return false;
        if(obj.trim().equals(""))
            return false;
        return true;
    }
    public static boolean isEmail(String email){
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean ObjectisNotEmpty(Object obj) throws Exception{
        if(obj==null)
            return false;
        Class cls =obj.getClass();
        Field[] fields=cls.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            boolean flag=false;
            Annotation[] annotations=field.getAnnotations();
            for(Annotation annotation:annotations){
                if(annotation.annotationType().getName().contains("common.annotion.NotEmpty")){
                    flag=true;
                    break;
                }
            }
            if(flag==false)
                continue;
            String type=field.getType().toString();
            type=type.trim();
            String[] strings=type.split(" ");
            Object object=field.get(obj);
            if(strings.length==2){
                if("java.lang.String".equals(strings[1])){
                    if(!DataVerify.stringIsNotEmpty((String) object)){
                        log.error(field.getName()+" 值不能为空");
                        return false;
                    }
                }else {
                    if(object==null) {
                        log.error(field.getName()+" 值不能为空");
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
