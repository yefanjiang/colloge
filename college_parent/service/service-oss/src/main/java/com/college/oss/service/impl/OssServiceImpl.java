package com.college.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.college.oss.service.OssService;
import com.college.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * @param file
     * @return url
     * 上传头像到OSS
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String url = null;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 改造文件名称，防止重名覆盖问题
            String uuid = UUID.randomUUID().toString();
            fileName = uuid + fileName;

            // 将文件按照日期进行分类
            String dateTime = new DateTime().toString("yyyy/MM/dd");
            fileName = dateTime + "/" + fileName;

            // 调用OSS方法实现上传
            // 第二个参数：上传到OSS文件路径和文件名称
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }
}
