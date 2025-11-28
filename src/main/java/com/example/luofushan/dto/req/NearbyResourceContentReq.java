package com.example.luofushan.dto.req;

import lombok.Data;

@Data
public class NearbyResourceContentReq {
    private Long id;         // 资源ID
    private Double latitude; // 用户纬度
    private Double longitude;// 用户经度
}