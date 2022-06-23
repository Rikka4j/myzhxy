package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.myzhxy.pojo.Student;
import com.learn.myzhxy.service.StudentService;
import com.learn.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    StudentService studentService;
    @ApiOperation("查询学生信息(分页带条件)")
    @RequestMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentOrd(@ApiParam("分页查询的页码") @PathVariable Integer pageNo,
                                @ApiParam("分页查询每页的数据量") @PathVariable Integer pageSize,
                                @ApiParam("分页查询的条件") Student student){

        Page<Student> studentPage = new Page<>(pageNo,pageSize);
        IPage<Student> studentIPage = studentService.loginInfo(studentPage, student);
        return  Result.ok(studentIPage);
    }
    @ApiOperation("添加或修改学生信息")
    @RequestMapping("/addOrUpdateStudent")
    public Result saveOrUpdateGrade(@RequestBody Student student){
        boolean b = studentService.saveOrUpdate(student);
        if (b==false) return  Result.fail();
        return  Result.ok();
    }
    @DeleteMapping("/delStudentById")
    @RequestMapping("/delStudentById")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        boolean b = studentService.removeByIds(ids);
        if (b==false) return  Result.fail();
        return  Result.ok();
    }

}
