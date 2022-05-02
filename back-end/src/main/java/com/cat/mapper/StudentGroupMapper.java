package com.cat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cat.model.StudentGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * date: 2021/12/21 21:54
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Repository
public interface StudentGroupMapper extends BaseMapper<StudentGroup> {
    List<StudentGroup> getGroupMemberList(Integer groupId);
}
