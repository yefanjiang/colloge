package com.college.serviceedu.controller;

import com.college.commonutils.UnifiedResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author winte
 * @CrossOrigin 解决跨域问题
 */
@RestController
@RequestMapping("/serviceedu/user")
@CrossOrigin
public class EduLoginController {

    @PostMapping("login")
    public UnifiedResult login() {
        return UnifiedResult.ok().data("token", "admin");
    }

    @GetMapping("info")
    public UnifiedResult info() {
        return UnifiedResult.ok().data("roles", "[admin]")
                .data("name", "admin")
                .data("avater", "https://is3-ssl.mzstatic.com/image/thumb/Music/v4/99/21/82/9921826b-5d30-fc13-feda-01346a30eb30/075679956484.jpg/600x600bb-60.jpg");
    }
}
