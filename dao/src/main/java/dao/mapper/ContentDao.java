package dao.mapper;

import dao.model.ContentList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ContentDao {
    @Select("select group_id from content_list where is_delete=0 and goods_id=#{goods_id} and content_subject_id=group_create_id order by add_time desc limit #{pageStart},1")
    String getGroupId(String goods_id,int pageStart);
    @Select("select * from content_list where group_id=#{group_id} and goods_id=#{goods_id} and is_delete=0 limit 0,#{pageSize}")
    List<ContentList> getContentList(String group_id,String goods_id,int pageStart,int pageSize);
    @Update("update content_list set is_delete=1 where group_id=#{group_id} and goods_id=#{goods_id}")
    int updateDelete(String group_id,String goods_id);
    @Insert("insert into content_list values(#{id},#{content_subject_id},#{content_object_id},#{content},#{add_time},#{group_id},#{group_create_id},0,#{goods_id},#{is_reply})")
    int insertContent(ContentList contentList);
    @Update("update content_list set is_delete=1 where id=#{id} and goods_id=#{goods_id}")
    int updateDeleteContenrIngGroup(String id,String goods_id);
    @Select("select count(*) from content_list where group_id=#{group_id} and goods_id=#{goods_id} and is_delete=0")
    int getGroupContentCount(String group_id,String goods_id);
    @Select("select count(*) from content_list where is_delete=0 and goods_id=#{goods_id} and content_subject_id=group_create_id")
    int getCount(String goods_id);
    @Select("select * from content_list where is_delete=0 and content_object_id=#{content_object_id} and is_reply=0")
    List<ContentList> getNotReply(String content_object_id);
    @Select("select count(*) from content_list where is_delete=0 and content_object_id=#{content_object_id} and is_reply=0")
    int getNotReplyCount(String content_object_id);
    @Update("update content_list set is_reply=1 where id=#{id}")
    int UpdateIsReply(String id);
    @Select("select * from content_list where is_delete=0 and id=#{id}")
    ContentList getContentById(String id);
}
