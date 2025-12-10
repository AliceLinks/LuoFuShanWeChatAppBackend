package com.example.luofushan.controller;

import com.example.luofushan.dto.Result;
import com.example.luofushan.dto.req.UpdateUserProfileReq;
import com.example.luofushan.dto.resp.UserProfileResp;
import com.example.luofushan.service.UserCenterService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserCenterController {

    @Resource
    private UserCenterService userCenterService;

    /**
     * 1. 查询用户信息（个人中心首页）
     * GET /user/profile
     */
    @GetMapping("/profile")
    public Result<UserProfileResp> getProfile() {
        return Result.buildSuccess(userCenterService.getProfile());
    }

    /**
     * 2. 修改个人信息
     * POST /user/profile/update
     */
    @PostMapping("/profile/update")
    public Result<UserProfileResp> updateProfile(@RequestBody UpdateUserProfileReq req) {
        return Result.buildSuccess(userCenterService.updateProfile(req));
    }
}
