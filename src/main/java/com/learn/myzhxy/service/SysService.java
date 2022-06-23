package com.learn.myzhxy.service;

import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Student;
import com.learn.myzhxy.pojo.Teacher;
import com.learn.myzhxy.util.JwtHelper;
import com.learn.myzhxy.util.Result;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.rmi.MarshalledObject;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/6/18
 */
@Service
public class SysService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    public Result userLogin(LoginForm loginForm)  {

        Result result=null;
        switch (loginForm.getUserType()){
            case 1:
                Admin admin = adminService.login(loginForm);
                result = this.loginJudge(Admin.class,admin,loginForm.getUserType());
                break;
            case 2:
                Student student = studentService.login(loginForm);
                result = this.loginJudge(Student.class,student,loginForm.getUserType());
                break;
            case 3:
                Teacher teacher = teacherService.login(loginForm);
                result = this.loginJudge(Teacher.class,teacher,loginForm.getUserType());
                break;
        }
        return result;
    }
    public Map<String, Object> tokenUserInfo(String token){
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getBaseMapper().selectById(userId);
                map.put("userType", userType);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getBaseMapper().selectById(userId);
                map.put("userType", userType);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getBaseMapper().selectById(userId);
                map.put("userType", userType);
                map.put("user", teacher);
                break;
        }
        return map;
    }



    public <T> Result loginJudge(Class<T> clazz, T cla, Integer type)  {
        if (cla==null) return Result.fail().message("账号或密码输入错误.");
        T u = clazz.cast(cla);
        String userId=null;
        for (Field field : u.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if(field.getName().equals("id")){
                    userId = field.get(u)==null?null:String.valueOf(field.get(u));
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-userId-:"+userId);
        Map<String, Object> map  = new LinkedHashMap<>();
        map.put("token", JwtHelper.createToken(Long.parseLong(userId.toString()),type));
        return Result.ok(map);
    }





}
