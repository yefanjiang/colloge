package com.college.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.college.commonutils.UnifiedResult;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.vod.service.VodService;
import com.college.vod.utils.ConstantPropertiesUtil;
import com.college.vod.utils.InitObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author winte
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new MyException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new MyException(20001, "vod 服务上传失败");
        }
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        try {
            // 初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // videoList 转换为逗号隔开
            String str = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            // 传入id
            request.setVideoIds(str);
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");

        } catch (Exception e) {
            throw new MyException(20001, "视频删除失败");
        }
    }
}
