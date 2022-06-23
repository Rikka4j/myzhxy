package com.learn.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.myzhxy.pojo.Grade;

/**
 * <p>
 *
 * </p>
 *
 * @author wpc
 * @since 30
 */
public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);
}
