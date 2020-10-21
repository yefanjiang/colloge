package com.college.serviceedu.client;

import com.college.commonutils.UnifiedResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author winte
 * 出错才执行，配合熔断机制
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public UnifiedResult removeVideo(String id) {
        return UnifiedResult.error().message("time out");
    }

    @Override
    public UnifiedResult removeVideoList(List<String> videoIdList) {
        return UnifiedResult.error().message("time out");
    }
}
