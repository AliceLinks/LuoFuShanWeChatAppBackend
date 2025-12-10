package com.example.luofushan.service;

import com.example.luofushan.dto.req.UpdateUserProfileReq;
import com.example.luofushan.dto.resp.UserProfileResp;

public interface UserCenterService {

    /**
     * 1. 查询当前登录用户信息
     */
    UserProfileResp getProfile();

    /**
     * 2. 修改当前登录用户信息
     */
    UserProfileResp updateProfile(UpdateUserProfileReq req);
}
