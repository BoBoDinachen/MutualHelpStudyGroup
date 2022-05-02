package com.cat.controller;

import com.cat.model.Student;
import com.cat.service.student.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * date: 2021/12/29 10:54
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class StudentController {
    StudentServiceImpl studentService;
    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    /**
     * 登录
     * @param student
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody Student student){
        String email = student.getEmail();
        String password = student.getPassword();
        String res = studentService.login(email, password);
        return res;
    }

    /**
     * 注册
     * @param student
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody Student student){
        String email = student.getEmail();
        String password = student.getPassword();
        String res = studentService.register(email, password);
        return res;
    }

    /**
     * 获取学生信息
     * @param uid
     * @return
     */
    @GetMapping("/info/{uid}")
    public String getInfo(@PathVariable Integer uid){
        String info = studentService.getStudentInfo(uid);
        return info;
    }

    /**
     * 更新学生信息
     * @param student
     * @return
     */
    @PostMapping("/update")
    public String updateInfo(@RequestBody Student student){
        System.out.println(student);
        String s = studentService.updateStudentInfo(student);
        return s;
    }

    /**
     * 注销账户
     * @param student
     * @return
     */
    @PostMapping("logout")
    public String logout(@RequestBody Student student){
        String s = studentService.logoutStudent(student.getId(), student.getPassword());
        return s;
    }
}
