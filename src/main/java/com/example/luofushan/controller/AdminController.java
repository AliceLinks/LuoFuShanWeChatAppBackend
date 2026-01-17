package com.example.luofushan.controller;

import com.example.luofushan.dto.Result;
import com.example.luofushan.dto.req.AdminPasswordUpdateReq;
import com.example.luofushan.dto.req.AdminSaveResourceReq;
import com.example.luofushan.dto.req.AdminUnlockReq;
import com.example.luofushan.dto.resp.AdminSaveResourceResp;
import com.example.luofushan.dto.resp.AdminUnlockResp;
import com.example.luofushan.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/resource/save")
    public Result<AdminSaveResourceResp> saveResourceReq(@RequestBody AdminSaveResourceReq req) {
        return Result.buildSuccess(adminService.saveResource(req));
    }

    @PostMapping("/resource/delete")
    public Result<String> deleteResource(@RequestParam Long id) {
        return Result.buildSuccess(adminService.deleteResource(id));
    }
}
