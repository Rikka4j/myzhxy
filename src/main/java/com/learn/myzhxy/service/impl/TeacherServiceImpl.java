package com.learn.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.myzhxy.mapper.TeacherMapper;
import com.learn.myzhxy.pojo.LoginForm;
import com.learn.myzhxy.pojo.Teacher;
import com.learn.myzhxy.service.TeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(loginForm.getUsername()),Teacher::getName,loginForm.getUsername())
                .eq(StringUtils.isNotBlank(loginForm.getPassword()),Teacher::getPassword, MD5.encrypt(loginForm.getPassword()));
        return getOne(lambdaQueryWrapper);
    }

    @Override
    public IPage<Teacher> getTeacherOrd(Page<Teacher> page, Teacher teacher) {
        LambdaQueryWrapper<Teacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(teacher.getClazzName()),Teacher::getClazzName,teacher.getClazzName())
                        .eq(StringUtils.isNotBlank(teacher.getName()),Teacher::getName,teacher.getName())
                        .orderByDesc(Teacher::getId);
        Page<Teacher> teacherPage = baseMapper.selectPage(page, lambdaQueryWrapper);

        return teacherPage;
    }
}
