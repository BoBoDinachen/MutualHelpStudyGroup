package com.cat.service.student;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cat.mapper.StudentMapper;
import com.cat.model.Student;
import com.cat.utils.JWTUtils;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * date: 2021/12/21 16:39
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@Service
public class StudentServiceImpl implements StudentService{
    StudentMapper studentMapper;
    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    /**
     * 用户登录
     * @param email 邮箱
     * @param password 密码
     */
    @Override
    public String login(String email, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Student::getEmail,email);
        Student student = studentMapper.selectOne(queryWrapper);
        if (student == null){
            return ResultUtil.failed("用户不存在");
        }else {
            QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.lambda().eq(Student::getEmail,email);
            queryWrapper2.lambda().eq(Student::getPassword,password);
            Student s =  studentMapper.selectOne(queryWrapper2);
            if (s==null){
                return ResultUtil.failed("密码错误");
            }else{
                Map<String, String> map = new HashMap<>();//用来存放payload
                map.put("email",email);
                map.put("password",password);
                String token = JWTUtils.getToken(map);
                JSONObject data = new JSONObject();
                s.setPassword(null);
                s.setPortraitPath(null);
                data.put("user",s);
                data.put("token",token);
                return ResultUtil.success("验证成功",data);
            }
        }
    }

    /**
     * 用户注册
     * @param email
     * @param password
     */
    @Override
    public String register(String email, String password) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Student::getEmail,email);
        Student student = studentMapper.selectOne(queryWrapper);
        if (student == null){
             Student s = new Student();
             s.setEmail(email);
             s.setPassword(password);
             s.setUserName("可爱萌新");
             int flag = studentMapper.insert(s);
             return ResultUtil.success("注册成功",null);
        }else {
            return ResultUtil.failed("该邮箱已注册");
        }
    }

    /**
     * 获取学生信息
     * @param uid
     * @return
     */
    public String getStudentInfo(Integer uid){
        Student student = studentMapper.selectById(uid);
        if (student == null){
            return ResultUtil.failed("没有查找到该学生");
        }else {
//            student.setPassword(null);
            student.setPortraitPath(null);
            JSONObject data = new JSONObject();
            data.put("student",student);
            return ResultUtil.success("获取成功",data);
        }
    }

    /**
     * 获取头像地址
     * @param uid
     * @return
     */
    public String getAvatarPath(Integer uid){
        Student student = studentMapper.selectById(uid);
        return student.getPortraitPath();
    }
    /**
     * 修改学生信息
     * @param student
     * @return
     */
    public String updateStudentInfo(Student student){
        int i = studentMapper.updateById(student);
        if (i != 0){
            return ResultUtil.success("修改成功",null);
        }else {
            return ResultUtil.failed("修改失败");
        }
    }

    /**
     * 修改用户头像地址
     * @param studentId
     * @param path
     * @return
     */
    @Override
    public String updatePortraitPath(Integer studentId, String path) {
        Student student = new Student();
        student.setId(studentId);
        student.setPortraitPath(path);
        int i = studentMapper.updateById(student);
        if (i != 0){
            return ResultUtil.success("修改地址成功",null);
        }else {
            return ResultUtil.failed("修改地址失败");
        }
    }

    /**
     * 注销账户
     * @param uid
     * @param password
     * @return
     */
    public String logoutStudent(Integer uid,String password){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Student::getId,uid);
        queryWrapper.lambda().eq(Student::getPassword,password);
        int delete = studentMapper.delete(queryWrapper);
        if (delete != 0){
            return ResultUtil.success("注销账户成功",null);
        }else {
            return ResultUtil.failed("密码验证不正确");
        }
    }
}
