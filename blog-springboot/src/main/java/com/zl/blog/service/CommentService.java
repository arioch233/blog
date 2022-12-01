package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.Comment;
import com.zl.blog.pojo.dto.CommentBackDTO;
import com.zl.blog.pojo.dto.CommentDTO;
import com.zl.blog.pojo.dto.ReplyDTO;
import com.zl.blog.pojo.dto.TouristDTO;
import com.zl.blog.pojo.vo.CommentVO;
import com.zl.blog.pojo.vo.ConditionVO;
import com.zl.blog.pojo.vo.ReviewVO;
import com.zl.blog.pojo.vo.TouristVO;

import java.util.List;

/**
 * 评论服务
 *
 * @author 冷血毒舌
 * @description 针对表【tb_comment】的数据库操作Service
 * @createDate 2022-11-26 15:17:00
 */
public interface CommentService extends IService<Comment> {
    /**
     * 查看评论
     *
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     */
    void updateCommentsReview(ReviewVO reviewVO);

    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition);

    /**
     * 获取游客信息
     *
     * @return
     */
    TouristDTO getTouristInfo();

    /**
     * 上传游客信息
     */
    void reportTourist(TouristVO touristVO);


}
