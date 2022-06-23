package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Student;
import com.learn.myzhxy.pojo.Teacher;
import com.learn.myzhxy.service.AdminService;
import com.learn.myzhxy.service.StudentService;
import com.learn.myzhxy.service.SysService;
import com.learn.myzhxy.service.TeacherService;
import com.learn.myzhxy.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private SysService sysService;

    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @RequestMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = CreateVerifiCodeImage.getVerifiCodeImage();
        String code = new String(CreateVerifiCodeImage.getVerifiCode());
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",code);
        ImageIO.write(image,"jpg",response.getOutputStream());
    }
    @RequestMapping("/login")
    public Result  loginUser(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("verifiCode");
        System.out.println("sessionCode---"+sessionCode);
        String verifiCode = loginForm.getVerifiCode();
        System.out.println("verifiCode---"+verifiCode);
        if (StringUtils.isBlank(sessionCode)){
            return Result.fail().message("验证码失效，请刷新后重新尝试。");
        }
        if (!sessionCode.equals(verifiCode)){
            return Result.fail().message("验证码输入错误。");
        }
        session.removeAttribute("verifiCode");
        return  sysService.userLogin(loginForm);
    }
    @RequestMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        if (JwtHelper.isExpiration(token)) return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        return Result.ok(sysService.tokenUserInfo(token));
    }
    @ApiOperation("文件上传统一入口")
    @RequestMapping("/headerImgUpload")
    public Result headerImgUpload( @ApiParam("上传的头像文件") @RequestPart("multipartFile") MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(index));

        // 保存文件 将文件发送到第三方/独立的图片服务器上，这里保存到本机
        String portraitPath = "C:/Users/Administrator/IdeaProjects/myzhxy/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath)); // 将图片传输到指定位置
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }
    @ApiOperation("修改用户密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("蕴含用户信息的token口令") @RequestHeader("token") String token,
            @ApiParam("原密码") @PathVariable("oldPwd") String olePwd,
            @ApiParam("新密码") @PathVariable("newPwd") String newPwd  ){
        // 判断token是否在有效期内
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration) return Result.fail().message("token失效，请重新登录");
        // 获取用户id和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        olePwd = MD5.encrypt(olePwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
                adminQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Admin admin = adminService.getOne(adminQueryWrapper);
                if(admin != null){
                    // 找到该用户，修改密码
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;

            case 2:
                QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
                studentQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Student student = studentService.getOne(studentQueryWrapper);
                if(student != null){
                    // 找到该用户，修改密码
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;

            case 3:
                QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
                teacherQueryWrapper.eq("id", userId.intValue())
                        .eq("password", olePwd);
                Teacher teacher = teacherService.getOne(teacherQueryWrapper);
                if(teacher != null){
                    // 找到该用户，修改密码
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    // 没有找到该用户，返回错误信息
                    return Result.fail().message("原密码错误");
                }
                break;
        }
        return  Result.ok();
    }
}
