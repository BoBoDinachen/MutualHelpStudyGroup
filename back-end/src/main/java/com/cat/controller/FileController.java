package com.cat.controller;

import ch.qos.logback.core.util.FileUtil;
import com.cat.service.group.GroupServiceImpl;
import com.cat.service.student.StudentServiceImpl;
import com.cat.utils.FileUtils;
import com.cat.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

/**
 * date: 2021/12/31 11:07
 * 关于处理文件上传和下载的类
 * @author: 薛定谔的猫猫虫
 * @message:
 */
@RestController
@CrossOrigin
public class FileController {
    StudentServiceImpl studentService;
    GroupServiceImpl groupService;
    @Autowired
    public void setStudentService(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }
    @Autowired
    public void setGroupService(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    // 文件的动态路径
    @Value("${fileLocation}")
    private String fileLocation;

    /**
     * 上传用户头像
     * @param file
     * @param uid
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/avatar")
    public String uploadStudentAvatar(@RequestPart("avatar") MultipartFile file, @RequestParam("uid") Integer uid) throws IOException {
        Calendar calendar = Calendar.getInstance();
        // 获得 classpath 的绝对路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + fileLocation+"/avatar";
        File newFile = new File(realPath);
        // 如果文件夹不存在、则新建
        if (!newFile.exists()) newFile.mkdirs();
        // 定义文件的存储路径 和 文件名字
        String fileName = uid+"-"+calendar.getTimeInMillis() +"@" + file.getOriginalFilename();
        String path = newFile.getPath()+"\\"+fileName; // 文件的绝对路径
        // 判断图片类型
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
            FileUtils.deleteFiles(realPath,String.valueOf(uid)); // 删除用户以前的头像
            file.transferTo(new File(newFile, fileName)); // 保存头像
            studentService.updatePortraitPath(Integer.valueOf(uid),path); // 将头像路径保存到数据库
            return ResultUtil.success("头像上传成功",path);
        }else {
            return ResultUtil.failed("图片只支持jgp/png格式的");
        }
    }

    /**
     * 获取用户图片
     * @param uid
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/avatar/{uid}")
    public void getStudentAvatar(@PathVariable("uid")Integer uid, HttpServletResponse response) throws IOException {
        String url = studentService.getAvatarPath(uid);
        if (url!=null){
            byte[] bytes = FileUtils.getFileBytes(url);
            // 设置返回类型
            response.setContentType("image/png");
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream stream = response.getOutputStream();
            stream.write(bytes);
        }else {
            response.getWriter().write(ResultUtil.failed("读取图片路径为空"));
        }
    }

    /**
     * 上传小组头像
     * @param file
     * @param groupId
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/teamHead")
    public String uploadGroupAvatar(@RequestPart("avatar") MultipartFile file, @RequestParam("groupId") Integer groupId) throws IOException {
        Calendar calendar = Calendar.getInstance();
        // 获得 classpath 的绝对路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + fileLocation+"/teamHead";
        File newFile = new File(realPath);
        // 如果文件夹不存在、则新建
        if (!newFile.exists()) newFile.mkdirs();
        // 定义文件的存储路径 和 文件名字
        String fileName = groupId+"-"+calendar.getTimeInMillis() +"@" + file.getOriginalFilename();
        String path = newFile.getPath()+"\\"+fileName; // 文件的绝对路径
        // 判断图片类型
        if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")){
            FileUtils.deleteFiles(realPath,String.valueOf(groupId)); // 删除小组以前的头像
            file.transferTo(new File(newFile, fileName)); // 保存头像
            groupService.changeTeamHeadPath(groupId,path);// 将头像路径保存到数据库
            return ResultUtil.success("上传头像成功",path);
        }else {
            return ResultUtil.failed("图片只支持jgp/png格式的");
        }
    }

    /**
     * 获取小组头像
     * @param groupId
     * @param response
     * @throws IOException
     */
    @GetMapping("/download/team/{groupId}")
    public void getGroupAvatar(@PathVariable("groupId")Integer groupId, HttpServletResponse response) throws IOException {
        String url = groupService.getGroupHeadPath(groupId);
        if (url!=null){
            byte[] bytes = FileUtils.getFileBytes(url);
            // 设置返回类型
            response.setContentType("image/jpeg");
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream stream = response.getOutputStream();
            stream.write(bytes);
        }else {
            response.getWriter().write(ResultUtil.failed("读取图片路径为空"));
        }
    }
}
