package com.learn.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.myzhxy.mapper.StudentMapper;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Student;
import com.learn.myzhxy.service.StudentService;
import com.learn.myzhxy.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        LambdaQueryWrapper<Student>  lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(loginForm.getUsername()),Student::getName,loginForm.getUsername())
                .eq(StringUtils.isNotBlank(loginForm.getPassword()),Student::getPassword, MD5.encrypt(loginForm.getPassword()));
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public IPage<Student> loginInfo(Page<Student> page, Student student) {

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        System.out.println("clazzList+student:=="+student);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(student.getName()),Student::getName ,student.getName())
                .eq(StringUtils.isNotBlank(student.getClazzName()),Student::getClazzName,student.getClazzName())
                .orderByDesc(Student::getId);
        Page<Student> studentPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return studentPage;
    }

}
