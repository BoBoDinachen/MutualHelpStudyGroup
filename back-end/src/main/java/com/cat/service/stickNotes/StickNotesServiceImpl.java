package com.cat.service.stickNotes;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cat.mapper.StickNotesMapper;
import com.cat.model.StickyNotes;
import com.cat.model.Student;
import com.cat.utils.ResultUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * date: 2021/12/30 8:53
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class StickNotesServiceImpl implements StickNotesService {

    StickNotesMapper stickNotesMapper;
    @Autowired
    public void setStickNotesMapper(StickNotesMapper stickNotesMapper) {
        this.stickNotesMapper = stickNotesMapper;
    }

    @Override
    public List<StickyNotes> selectAllbyuid(String uid) {
        List<StickyNotes> stickyNotes = stickNotesMapper.selectAllbyuid(uid);
        return stickyNotes;
    }

    @Override
    public boolean addsticky(StickyNotes stickyNotes) {
        boolean addsticky = stickNotesMapper.addsticky(stickyNotes);
       return addsticky;
    }

    @Override
    public boolean deleteById(int stickerId) {
        boolean b = stickNotesMapper.deleteById(stickerId);
        return b;
    }

    @Override
    public boolean updateBystId(String title, int stickerId) {
        boolean b = stickNotesMapper.updateBystId(title, stickerId);
        System.out.println("11111111111");
        return b;
    }
}
