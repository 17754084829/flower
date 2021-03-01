package service;

import dao.model.ContentList;
import dao.model.Goods_car;
import model.PageHeleperInfo;

import java.util.List;

public interface ContentListService {
    String getGroupId(String goods_id,int pageStart);
    PageHeleperInfo getContentListGroup(int pageStart,String goods_id);
    int updateDelete(String group_id,String goods_id);
    int insertContent(ContentList contentList);
    int updateDeleteContenrIngGroup(String id,String goods_id);
    int UpdateIsReply(String id);
    List<ContentList> getNotReply(String content_object_id);
    ContentList getContentById(String id);
    int getNotReplyCount(String content_object_id);
}
