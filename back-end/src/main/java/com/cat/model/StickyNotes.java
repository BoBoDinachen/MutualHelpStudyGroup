package com.cat.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


@Data
@TableName("sticky_notes")
public class StickyNotes {
    @TableId(value = "sticker_id",type = IdType.AUTO)
    private Integer id;

    @TableField("student_id")
    private Integer studentId;

    private String title;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private String createTime;

}
