package com.example.luofushan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.luofushan.common.exception.LuoFuShanException;
import com.example.luofushan.dao.entity.AdminConfig;
import com.example.luofushan.dao.entity.AdminToken;
import com.example.luofushan.dao.entity.Resource;
import com.example.luofushan.dao.mapper.AdminConfigMapper;
import com.example.luofushan.dao.mapper.AdminTokenMapper;
import com.example.luofushan.dao.mapper.ResourceMapper;
import com.example.luofushan.dto.req.AdminPasswordUpdateReq;
import com.example.luofushan.dto.req.AdminSaveResourceReq;
import com.example.luofushan.dto.req.AdminUnlockReq;
import com.example.luofushan.dto.resp.AdminSaveResourceResp;
import com.example.luofushan.dto.resp.AdminUnlockResp;
import com.example.luofushan.service.AdminService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminConfigMapper adminConfigMapper;
    private final AdminTokenMapper adminTokenMapper;
    private final ResourceMapper resourceMapper;

    @Override
    public AdminUnlockResp unlock(AdminUnlockReq req) {
        if (!StringUtils.hasText(req.getPassword())) {
            throw new LuoFuShanException("密码不能为空");
        }

        AdminConfig config = adminConfigMapper.selectOne(
                new LambdaQueryWrapper<AdminConfig>()
                        .orderByDesc(AdminConfig::getId)
                        .last("limit 1")
        );
        if (config == null) {
            throw new LuoFuShanException("管理端尚未初始化");
        }

        String inputHash = DigestUtils.md5DigestAsHex(
                req.getPassword().getBytes(StandardCharsets.UTF_8)
        );
        if (!inputHash.equals(config.getUnlockPassword())) {
            throw new LuoFuShanException("解锁密码错误");
        }

        // 生成会话 token（有效期 2 小时）
        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusHours(2);

        AdminToken at = new AdminToken();
        at.setToken(token);
        at.setCreateTime(now);
        at.setExpireTime(expireTime);
        adminTokenMapper.insert(at);

        AdminUnlockResp resp = new AdminUnlockResp();
        resp.setToken(token);
        resp.setExpireTime(expireTime);
        return resp;
    }

    @Override
    public void updatePassword(AdminPasswordUpdateReq req) {
        if (!StringUtils.hasText(req.getOldPassword()) ||
                !StringUtils.hasText(req.getNewPassword())) {
            throw new LuoFuShanException("旧密码和新密码均不能为空");
        }
        if (req.getNewPassword().length() < 6) {
            throw new LuoFuShanException("新密码长度不能少于6位");
        }

        AdminConfig config = adminConfigMapper.selectOne(
                new LambdaQueryWrapper<AdminConfig>()
                        .orderByDesc(AdminConfig::getId)
                        .last("limit 1")
        );
        if (config == null) {
            throw new LuoFuShanException("管理端尚未初始化");
        }

        String oldHash = DigestUtils.md5DigestAsHex(
                req.getOldPassword().getBytes(StandardCharsets.UTF_8)
        );
        if (!oldHash.equals(config.getUnlockPassword())) {
            throw new LuoFuShanException("旧密码不正确");
        }

        String newHash = DigestUtils.md5DigestAsHex(
                req.getNewPassword().getBytes(StandardCharsets.UTF_8)
        );
        config.setUnlockPassword(newHash);
        adminConfigMapper.updateById(config);

        //修改密码后清空所有已有的管理端 token
        adminTokenMapper.delete(null);
    }

    @Override
    public AdminSaveResourceResp saveResource(AdminSaveResourceReq req) {
        List<String> types = List.of("景点", "住宿", "餐饮", "商家");
        if(StringUtil.isNullOrEmpty(req.getName()) || StringUtil.isNullOrEmpty(req.getType())) {
            throw LuoFuShanException.adminFail("名称或类型为空");
        }
        if(!types.contains(req.getType())) {
            throw LuoFuShanException.adminFail("资源类型不为：景点/住宿/餐饮/商家");
        }
        Resource resource;
        // 插入
        if(req.getId() == null) {
            resource = BeanUtil.toBean(req, Resource.class);
            resourceMapper.insert(resource);

        }
        // 更新
        else {
            LambdaQueryWrapper<Resource> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Resource::getDelflag, 0)
                    .eq(Resource::getId, req.getId());
            resource = resourceMapper.selectOne(wrapper);
            if(resource==null) throw LuoFuShanException.resourceNotExists();
            if(req.getLatitude()!=null) resource.setLatitude(req.getLatitude());
            if(req.getLongitude()!=null) resource.setLongitude(req.getLongitude());
            if(req.getContentJson()!=null) resource.setContentJson(req.getContentJson());
            if(req.getName()!=null) resource.setName(req.getName());
            if(req.getCoverImg()!=null) resource.setCoverImg(req.getCoverImg());
            if(req.getHotScore()!=null) resource.setHotScore(req.getHotScore());
            if(req.getType()!=null) resource.setType(req.getType());
            resourceMapper.updateById(resource);
        }
        return BeanUtil.toBean(resource, AdminSaveResourceResp.class);
    }

    @Override
    public String deleteResource(Long id) {
        Resource resource = resourceMapper.selectById(id);
        if(resource==null) {
            throw LuoFuShanException.resourceNotExists();
        }
        resource.setDelflag(1);
        resourceMapper.updateById(resource);
        return "删除成功";
    }
}
