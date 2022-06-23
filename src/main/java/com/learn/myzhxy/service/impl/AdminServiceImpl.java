package com.learn.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.myzhxy.mapper.AdminMapper;
import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.service.AdminService;
import com.learn.myzhxy.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    public Admin login(LoginForm loginForm){
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(loginForm.getUsername()),Admin::getName,loginForm.getUsername())
                .eq(StringUtils.isNotBlank(loginForm.getPassword()),Admin::getPassword,MD5.encrypt(loginForm.getPassword()));
        Admin loginUser = getOne(lambdaQueryWrapper);
        return loginUser;
    }

    @Override
    public IPage getAdminUsers(Page<Admin> page, String adminName) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(adminName),Admin::getName,adminName)
                        .orderByDesc(Admin::getId);
        Page<Admin> adminPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return adminPage;
    }
}
