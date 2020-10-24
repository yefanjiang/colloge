package com.college.msm.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.college.commonutils.UnifiedResult;
import com.college.msm.service.MsmService;
import com.college.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public UnifiedResult code(@PathVariable String phone) {
        // 生成随机值，传递给阿里云进行发送
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return UnifiedResult.ok();
        }

        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(phone, "SMS_205135128", param);
        if(isSend) {
            // 设置有效值为5分钟，超过五分钟，redis就取不到
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return UnifiedResult.ok();
        } else {
            return UnifiedResult.error().message("发送短信失败");
        }
    }
}
