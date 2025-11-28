package com.example.luofushan.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.luofushan.dao.entity.PostComment;
import com.example.luofushan.dto.resp.PostCommentListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostCommentMapper extends BaseMapper<PostComment> {
    List<PostCommentListResp> selectCommentList(@Param("postId") Long postId,
                                                @Param("offset") int offset,
                                                @Param("size") int size);

    int countByPostId(@Param("postId") Long postId);

}