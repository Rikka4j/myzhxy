package com.learn.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.service.AdminService;
import com.learn.myzhxy.util.MD5;
import com.learn.myzhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @ApiOperation("查询管理员信息(分页带条件)")
    @RequestMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的数据量") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字,用于模糊查询") String adminName){
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage = adminService.getAdminUsers(page, adminName);
        return Result.ok(iPage);
    }
    @ApiOperation("删除或批量删除管理员信息")
    @RequestMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("要删除的管理员的id的JSON数组") @RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("修改管理员信息")
    @RequestMapping("saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@ApiParam("需要修改的管理员") @RequestBody Admin admin){
        if (StringUtils.isNotBlank(admin.getId().toString())){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }
}
