package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.myzhxy.pojo.Clazz;
import com.learn.myzhxy.pojo.Grade;
import com.learn.myzhxy.service.ClazzService;
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
@Api(tags = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    @ApiOperation("查询班级信息(分页带条件)")
    @RequestMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazz(@ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
                           @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
                           @ApiParam("分页查询的查询条件")   Clazz clazz){
        Page<Clazz> clazzIPage = new Page<>(pageNo,pageSize);
        IPage<Clazz> clazzInfo = clazzService.getClazz(clazzIPage, clazz);
        return Result.ok(clazzInfo);
    }
    @ApiOperation("添加或修改班级信息")
    @RequestMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("JOSN格式的班级信息") @RequestBody Clazz clazz){
        boolean b = clazzService.saveOrUpdate(clazz);
        if (b==false) return  Result.fail();
       return Result.ok();
    }
    @ApiOperation("删除或批量删除班级信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @ApiParam("要删除的多个班级的ID的JSON数组") @RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("获取全部年级")
    @RequestMapping("/getClazzs")
    public Result getGrades(){
        List<Clazz> clazzList = clazzService.list();
        return Result.ok(clazzList);
    }
}
