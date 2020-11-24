package com.cargo.menedata.controller;

import com.aliyun.oss.OSS;
import com.cargo.menedata.oss.OssProperties;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author : Lilei
 * @Description : 文件
 * @Date : 2019/4/21
 */
@Api(tags = "40-上传文件")
@RestController
@RequestMapping(value = "/api/base")
public class FastFileController {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private OssProperties ossProperties;

    @ApiOperation(value = "上传图片")
    @RequestMapping(path = "/file/fastUpload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseInfo uploadFileByDfs(@ApiParam("文件") @RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseUtil.success("上传文件不能为空！");
        }
        return ResponseUtil.success(uploadFile(file.getInputStream(), file.getOriginalFilename()));
    }


    /**
     * 上传文件
     *
     * @param inputStream 文件里
     * @param name        原始文件名
     * @return 访问地址
     */
    private String uploadFile(InputStream inputStream, String name) {
        String extension = FilenameUtils.getExtension(name);
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String key = format1.format(new Date()) + UUID.randomUUID().toString().replace("-", "") + "." + extension;
        final OSS oss = beanFactory.getBean(OSS.class);
        try {
            oss.putObject(ossProperties.getBucketName(), key, inputStream);
        }finally {
            oss.shutdown();
        }
        return ossProperties.getAccessDomainName() + key;
    }


}
