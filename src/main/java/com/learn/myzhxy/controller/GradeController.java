package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.myzhxy.pojo.Grade;
import com.learn.myzhxy.service.GradeService;
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
@Api(tags = "年级控制器")
@RequestMapping("/sms/gradeController")
@RestController
public class GradeController {
    @Autowired
    private GradeService gradeService;
    @ApiOperation("获取年级信息(分页带条件)")
    @RequestMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrade(@ApiParam("分页查询的页码数") @PathVariable Integer pageNo,
                           @ApiParam("分页查询每页的数据数") @PathVariable Integer pageSize,
                           @ApiParam("年级名字，用于搜索时的模糊查询") @RequestParam(required = false) String gradeName){
        Page<Grade> gradePage = new Page<>(pageNo,pageSize);
        IPage<Grade> gradeByOpr = gradeService.getGradeByOpr(gradePage, gradeName);
        return  Result.ok(gradeByOpr);
    }
    @ApiOperation("添加或修改年级信息，参数grade有id属性为修改，没有是添加")
    @RequestMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("年级信息的JSON对象")  @RequestBody Grade grade){
        boolean b = gradeService.saveOrUpdate(grade);
        if (b==false) return Result.fail();
        return Result.ok();
    }
    @ApiOperation("(批量)删除年级信息")
    @RequestMapping("deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的年级信息的id的JSON集合") @RequestBody List<Integer> ids){
        boolean b = gradeService.removeByIds(ids);
        if (b==false) return Result.fail();
        return Result.ok();
    }
    @ApiOperation("获取全部年级")
    @RequestMapping("/getGrades")
    public Result getGrades(){
        List<Grade> gradeList = gradeService.list();
        return Result.ok(gradeList);
    }
}
