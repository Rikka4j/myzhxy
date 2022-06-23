package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.myzhxy.pojo.Teacher;
import com.learn.myzhxy.service.TeacherService;
import com.learn.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */
@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @ApiOperation("获取教师信息(分页带条件)")
    @RequestMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("教师名称，用于模糊查询") Teacher teacher){
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> iPage = teacherService.getTeacherOrd(page, teacher);
        return Result.ok(iPage);
    }
    @ApiOperation("添加或修改教师信息")
    @RequestMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher( @ApiParam("要保存或修改的JSON格式的教师信息") @RequestBody Teacher teacher){
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }
    @ApiOperation("删除或批量删除教师信息")
    @RequestMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("要删除的教师id的JSON数组") @RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
