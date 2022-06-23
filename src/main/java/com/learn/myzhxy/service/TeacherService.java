package com.learn.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Teacher;

/**
 * <p>
 *
 * </p>
 *
 * @author wpc
 * @since 30
 */
public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    IPage<Teacher> getTeacherOrd(Page<Teacher> page,Teacher teacher);
}

