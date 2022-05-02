package com.cat.service.stickNotes;

import com.cat.model.StickyNotes;

import java.util.List;

/**
 * date: 2021/12/30 8:53
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface StickNotesService {
    List<StickyNotes> selectAllbyuid(String uid);
    boolean addsticky(StickyNotes stickyNotes);
    boolean deleteById(int stickerId);
    boolean updateBystId(String title,int stickerId);
}
