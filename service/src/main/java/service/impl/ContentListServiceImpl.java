package service.impl;

import common.util.DataVerify;
import dao.mapper.ContentDao;
import dao.model.ContentList;
import model.PageHeleperInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ContentListService;

import java.util.ArrayList;
import java.util.List;
@Service
public class ContentListServiceImpl implements ContentListService {
    @Autowired
    private ContentDao dao;
    @Override
    public String getGroupId(String goods_id,int pageStart) {
        return dao.getGroupId(goods_id,pageStart);
    }

    @Override
    public PageHeleperInfo getContentListGroup(int pageStart,String goods_id) {
        String group_id=getGroupId(goods_id,pageStart);
        PageHeleperInfo<ContentList> pageHeleperInfo=new PageHeleperInfo();
        int pageSize=dao.getCount(goods_id);
        if(pageStart>=pageSize-1)
            pageHeleperInfo.setHasNext(false);
        else{
            pageHeleperInfo.setHasNext(true);
            pageHeleperInfo.setNextPage(pageStart+1);
        }
        if(pageStart==0)
            pageHeleperInfo.setHasPre(false);
        else {
            pageHeleperInfo.setHasPre(true);
            pageHeleperInfo.setPrePage(pageStart-1);
        }
        int size=dao.getGroupContentCount(group_id,goods_id);
        pageHeleperInfo.setData(dao.getContentList(group_id,goods_id,pageStart,size));
        return pageHeleperInfo;
    }

    @Override
    public int updateDelete(String group_id,String goods_id) {
        return dao.updateDelete(group_id,goods_id);
    }

    @Override
    public int insertContent(ContentList contentList) {
        return dao.insertContent(contentList);
    }

    @Override
    public int updateDeleteContenrIngGroup(String id,String goods_id) {
        return dao.updateDeleteContenrIngGroup(id,goods_id);
    }

    @Override
    public int UpdateIsReply(String id) {
        return dao.UpdateIsReply(id);
    }

    @Override
    public List<ContentList> getNotReply(String content_object_id) {
        return dao.getNotReply(content_object_id);
    }

    @Override
    public ContentList getContentById(String id) {
        return dao.getContentById(id);
    }

    @Override
    public int getNotReplyCount(String content_object_id) {
        return dao.getNotReplyCount(content_object_id);
    }
}
