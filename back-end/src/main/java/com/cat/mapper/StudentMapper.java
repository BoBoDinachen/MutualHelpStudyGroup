package com.cat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cat.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * date: 2021/12/21 16:08
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {
}
