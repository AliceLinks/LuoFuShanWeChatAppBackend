package com.example.luofushan.common.exception;

public class LuoFuShanException extends RuntimeException {

    public LuoFuShanException(String message) {
        super(message);
    }

    public static LuoFuShanException userAlreadyExists() {
        return new LuoFuShanException("用户名已经存在!");
    }

    public static LuoFuShanException resourceNotExists() {
        return new LuoFuShanException("景点/商家/住宿/餐饮不存在!");
    }

    public static LuoFuShanException locationNotExists() {
        return new LuoFuShanException("打卡点不存在!");
    }

    public static LuoFuShanException alreadyHit() {
        return new LuoFuShanException("用户已在该打卡点打过卡");
    }

    public static LuoFuShanException hasNoDistanceInfo() {
        return new LuoFuShanException("没有经纬度信息");
    }
}