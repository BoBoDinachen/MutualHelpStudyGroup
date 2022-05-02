package com.cat.service.student;

import com.cat.model.Student;

/**
 * date: 2021/12/21 17:03
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public interface StudentService {
    // 登录
    String login(String email,String password);
    // 注册
    String register(String email,String password);
    // 获取学生信息
    String getStudentInfo(Integer uid);
    // 获取学生的头像
    String getAvatarPath(Integer uid);
    // 修改学生信息
    String updateStudentInfo(Student student);
    // 修改学生头像路径
    String updatePortraitPath(Integer studentId,String path);
    // 注销学生
    String logoutStudent(Integer uid,String password);
}
