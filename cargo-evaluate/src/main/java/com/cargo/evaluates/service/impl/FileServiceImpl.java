package com.cargo.evaluates.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.evaluates.entity.FileEntity;
import com.cargo.evaluates.mapper.FileMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图片存储表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {

}
