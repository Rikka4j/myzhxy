package com.learn.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Student;

/**
 * <p>
 *
 * </p>
 *
 * @author wpc
 * @since 30
 */
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    IPage<Student> loginInfo(Page<Student> page,Student student);
}
