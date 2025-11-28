package com.example.luofushan.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.luofushan.dao.entity.UserPost;
import com.example.luofushan.dto.resp.PostListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPostMapper extends BaseMapper<UserPost> {

    /**
     * 查询动态列表（支持模糊搜索、按距离、时间、点赞数、评论数排序）
     *
     * @param fuzzy     模糊搜索内容
     * @param latitude  用户纬度（按距离排序必填）
     * @param longitude 用户经度（按距离排序必填）
     * @param offset    分页偏移量
     * @param size      每页数量
     * @param sortBy    排序方式：time/distance/like/comment
     * @return 动态列表
     */
    List<PostListResp> selectPosts(
            @Param("fuzzy") String fuzzy,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("sortBy") String sortBy
    );

    /**
     * 查询符合条件的总条数，用于分页
     *
     * @param fuzzy    模糊搜索内容
     * @return 总数
     */
    int countPosts(@Param("fuzzy") String fuzzy);
}