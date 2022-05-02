package com.cat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cat.model.Group;
import com.cat.model.StickyNotes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * date: 2021/12/30 8:51
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Repository
public interface StickNotesMapper extends BaseMapper<StickyNotes>  {
    List<StickyNotes> selectAllbyuid(String uid);
     boolean updateBystId(@Param("title") String title,@Param("stickerId") int stickerId);
     boolean deleteById(int stickerId);
     boolean addsticky(StickyNotes stickyNotes);

}
