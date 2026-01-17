package com.example.luofushan.controller;

import com.example.luofushan.dto.Result;
import com.example.luofushan.dto.req.AdminPasswordUpdateReq;
import com.example.luofushan.dto.req.AdminSaveResourceReq;
import com.example.luofushan.dto.req.AdminUnlockReq;
import com.example.luofushan.dto.resp.AdminSaveResourceResp;
import com.example.luofushan.dto.resp.AdminUnlockResp;
import com.example.luofushan.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping("/unlock")
    public Result<AdminUnlockResp> unlock(@RequestBody AdminUnlockReq req) {
        return Result.buildSuccess(adminService.unlock(req));
    }

    @PostMapping("/password/update")
    public Result<Void> updatePassword(@RequestBody AdminPasswordUpdateReq req) {
        adminService.updatePassword(req);
        return Result.buildSuccess(null);
    }

    @PostMapping("/admin/resource/save")
    public Result<AdminSaveResourceResp> saveResourceReq(@RequestBody AdminSaveResourceReq req) {
        return Result.buildSuccess(adminService.saveResource(req));
    }
}
