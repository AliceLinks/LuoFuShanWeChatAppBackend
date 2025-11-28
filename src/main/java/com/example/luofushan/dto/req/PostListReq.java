package com.example.luofushan.dto.req;

import io.netty.util.internal.StringUtil;
import lombok.Data;

@Data
public class PostListReq {
    private String fuzzy;       // 模糊搜索文本内容
    private Double latitude;    // 用户纬度
    private Double longitude;   // 用户经度
    private Integer page;   // 页码，默认 1
    private Integer size;  // 每页数量，默认 10
    private String sortBy; // 排序方式，默认 time

    public void initDefault() {
        if (page == null || page <= 0) page = 1;
        if (size == null || size <= 0) size = 10;
        if (StringUtil.isNullOrEmpty(sortBy)) sortBy = "time";
        if (StringUtil.isNullOrEmpty(fuzzy)) fuzzy = "";
    }
}