package com.learn.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.myzhxy.pojo.Clazz;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wpc
 * @since 30
 */
@Service
public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazz(Page<Clazz> page,Clazz clazz);
}
