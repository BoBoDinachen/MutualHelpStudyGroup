package com.cat.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.SneakyThrows;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * date: 2021/12/21 17:31
 *
 * @author: 薛定谔的猫猫虫
 * @message: 处理数据自动填充的类
 */
@Component
public class MyMetaObjectHandler  implements MetaObjectHandler   {
    // 文件的动态路径
    @Value("${fileLocation}")
    private String fileLocation;


    /**
     * 插入时填充
     * @param metaObject
     */
    @SneakyThrows
    @Override
    public void insertFill(MetaObject metaObject) {
        Calendar calendar = Calendar.getInstance();
        // 获得 文件 的存储路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + fileLocation;
        // 汇报问题时间自动填充
        this.strictInsertFill(metaObject, "reportTime", String.class, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        // 加入小组时间自动填充
        this.strictInsertFill(metaObject, "joinTime", String.class, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        // 创建时间自动填充
        this.strictInsertFill(metaObject, "createTime", String.class, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        // 开始时间自动填充
        this.strictInsertFill(metaObject,"startTime",String.class,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        // 用户年龄填充
        this.strictInsertFill(metaObject,"age",Integer.class,18);
        // 用户头像路径填充
        this.strictInsertFill(metaObject,"portraitPath",String.class,realPath+"/avatar/default-avatar.png");
        // 小组头像路径填充
        this.strictInsertFill(metaObject,"teamHead",String.class,realPath+"/teamHead/default-team.png");
    }

    /**
     * 更新时填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
