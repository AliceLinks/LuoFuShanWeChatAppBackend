package com.example.luofushan.dto.resp;

import lombok.Data;

@Data
public class AdminMerchantListResp {
    private Long id;

    private String username;

    private String name;
    private String type;

    private Long resourceId;

    private Integer status;
}
