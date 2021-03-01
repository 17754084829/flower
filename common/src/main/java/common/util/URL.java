package common.util;

import common.model.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class URL {
    public static String defaultAllowUrl="/;/login;/user/login;/index.*;/resource/.*;/css/.*;/js/.*;/image/.*;/user.*;/sendMail;/([a-z]|[0-9]|_|[A-Z])*;";
    public static String getDefaultMenuList_user="dt:<i class=\"Hui-iconfont\">&#xe616;</i>个人信息管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li><a data-href=\"/admin/alter_my_info.html\" data-title=\"个人信息修改\" href=\"javascript:void(0)\">个人信息修改</a></li></ul>%"+
            "dt:<i class=\"Hui-iconfont\">&#xe616;</i>我的消息<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li> <span id=\"msgID\" class=\"badge badge-danger radius\" style=\"float:left\">0</span> <a data-href=\"/admin/getNotReply.html\" data-title=\"我的消息\" href=\"javascript:void(0)\">我的消息</a></li><li><a data-href=\"/admin/getGoods_car.html\" data-title=\"我的购物车\" href=\"javascript:void(0)\">我的购物车</a></li> <li><a data-href=\"/user/getOrderList.html\" data-title=\"我的订单\" href=\"javascript:void(0)\">我的订单</a></li></ul>%";
    public static String defaultMenuList_admin="dt:<i class=\"Hui-iconfont\">&#xe616;</i>个人信息管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li><a data-href=\"/admin/alter_my_info.html\" data-title=\"个人信息修改\" href=\"javascript:void(0)\">个人信息修改</a></li></ul>%" +
            "dt:<i class=\"Hui-iconfont\">&#xe616;</i>商品管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li><a data-href=\"/admin/goods_infos.html\" data-title=\"商品管理\" href=\"javascript:void(0)\">商品管理</a></li></ul>%"+
            "dt:<i class=\"Hui-iconfont\">&#xe616;</i>人员管理<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li><a data-href=\"/admin/person_infos.html\" data-title=\"人员管理\" href=\"javascript:void(0)\">人员管理</a></li></ul>%"+
            "dt:<i class=\"Hui-iconfont\">&#xe616;</i>我的消息<i class=\"Hui-iconfont menu_dropdown-arrow\">&#xe6d5;</i>%dd:<ul><li> <span id=\"msgID\" class=\"badge badge-danger radius\" style=\"float:left\">0</span> <a data-href=\"/admin/getNotReply.html\" data-title=\"我的消息\" href=\"javascript:void(0)\">我的消息</a></li><li><a data-href=\"/admin/getGoods_car.html\" data-title=\"我的购物车\" href=\"javascript:void(0)\">我的购物车</a></li> <li><a data-href=\"/user/getOrderList.html\" data-title=\"我的订单\" href=\"javascript:void(0)\">我的订单</a></li></ul>%";
    public static Map<String, ArrayList<Menu>> dealMenu(String menu){
        Map<String,ArrayList<Menu>> map=new HashMap<>();
        ArrayList<Menu> menus_dt=new ArrayList<>();
        ArrayList<Menu> menus_dd=new ArrayList<>();
        String[] menu_list=menu.split("%");
        for(String index:menu_list){
            if(index.startsWith("dt:")){
                ArrayList<String> dtList=new ArrayList<>();
                dtList.add(index.split("dt:")[1]);
                menus_dt.add(new Menu(dtList, dtList.size()));
                map.put("dt",menus_dt);
            }
            if(index.startsWith("dd:")){
                ArrayList<String> ddList=new ArrayList<>();
                ddList.add(index.split("dd:")[1]);
                menus_dd.add(new Menu(ddList,ddList.size()));
                map.put("dd",menus_dd);
            }
        }
        return map;
    }
}
