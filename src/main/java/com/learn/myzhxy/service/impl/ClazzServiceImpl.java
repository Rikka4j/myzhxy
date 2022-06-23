package com.learn.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.myzhxy.mapper.ClazzMapper;
import com.learn.myzhxy.pojo.Clazz;
import com.learn.myzhxy.service.ClazzService;
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
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {


    @Override
    public IPage<Clazz> getClazz(Page<Clazz> page, Clazz clazz) {
        LambdaQueryWrapper<Clazz> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        System.out.println("clazz:_-"+clazz);
        lambdaQueryWrapper.eq(StringUtils.isNotBlank(clazz.getGradeName()),Clazz::getGradeName,clazz.getGradeName())
                .eq(StringUtils.isNotBlank(clazz.getName()),Clazz::getName,clazz.getName())
                .orderByDesc(Clazz::getId);
        Page<Clazz> clazzPage = baseMapper.selectPage(page, lambdaQueryWrapper);
        return clazzPage;
    }
}
