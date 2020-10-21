package com.college.serviceedu.client;

import com.college.commonutils.UnifiedResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author winte
 * @FeignClient 用于指定从哪个服务中调用功能 ，名称与被调用的服务名保持一致。
 * @GetMapping 用于对被调用的微服务进行地址映射。
 * @PathVariable 一定要指定参数名称，否则出错
 * @Component 防止在其他位置注入CodClient时idea报错
 */

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    /**
     * 定义调用的方法路径，根据视频ID删除云视频
     * @param id @PathVariable中一定要指定名称，否则出错
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public UnifiedResult removeVideo(@PathVariable("id") String id);

    /**
     * 删除云端多个视频
     * @param videoIdList 多个ID的列表
     * @return
     */
    @DeleteMapping("/eduvod/video/removeVideoList")
    public UnifiedResult removeVideoList(@RequestParam("videoList") List<String> videoIdList);
}
