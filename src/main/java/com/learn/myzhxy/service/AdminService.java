package com.learn.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.myzhxy.pojo.Admin;
import com.learn.myzhxy.pojo.LoginForm;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */
public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    IPage getAdminUsers(Page<Admin> page,String admin);
}
