package com.cat.controller;

import com.cat.model.StickyNotes;
import com.cat.model.Student;
import com.cat.service.stickNotes.StickNotesServiceImpl;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * date: 2021/12/30 8:50
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
@RequestMapping("/stick")
public class StickController {
    StickNotesServiceImpl stickNotesService;
    @Autowired
    public void setStickNotesService(StickNotesServiceImpl stickNotesService) {
        this.stickNotesService = stickNotesService;
    }
    @GetMapping("/list/{uid}")
    public String getList(@PathVariable String uid){
        List<StickyNotes> stickyNotes = stickNotesService.selectAllbyuid(uid);
        if (stickyNotes!=null){
            return ResultUtil.success("成功",stickyNotes);
        }
        else
        return ResultUtil.failed("查找失败");
    }

    @GetMapping("/delete/{stickId}")
    public String delete(@PathVariable int stickId){
        boolean b = stickNotesService.deleteById(stickId);
        if (b){
            return ResultUtil.success("删除成功",null);
        }
        else
        return ResultUtil.failed("删除失败");
    }

    @PostMapping("/create")
    public String create(@RequestBody StickyNotes stickyNotes){
        boolean addsticky = stickNotesService.addsticky(stickyNotes);
        if (addsticky){
            return ResultUtil.success("添加成功",null);
        }
        else
        return ResultUtil.failed("添加失败");
    }
    @PostMapping("/update")
    public String update(@RequestBody StickyNotes stickyNotes){
        String title = stickyNotes.getTitle();
        Integer stickId = stickyNotes.getId();
        boolean b = stickNotesService.updateBystId(title, stickId);
        if (b){
            return ResultUtil.success("修改成功",null);
        }
        else
            return ResultUtil.failed("修改失败");
    }


}
