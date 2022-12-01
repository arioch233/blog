package com.zl.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.common.PageResult;
import com.zl.blog.entity.Comment;
import com.zl.blog.entity.Tourist;
import com.zl.blog.mapper.ArticleMapper;
import com.zl.blog.mapper.CommentMapper;
import com.zl.blog.mapper.TouristMapper;
import com.zl.blog.mapper.UserInfoMapper;
import com.zl.blog.pojo.dto.*;
import com.zl.blog.pojo.vo.*;
import com.zl.blog.service.CommentService;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.TouristService;
import com.zl.blog.service.WebsiteConfigService;
import com.zl.blog.utils.BeanCopyUtils;
import com.zl.blog.utils.HTMLUtils;
import com.zl.blog.utils.PageUtils;
import com.zl.blog.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zl.blog.common.CommonConst.FALSE;
import static com.zl.blog.common.CommonConst.TRUE;
import static com.zl.blog.common.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.zl.blog.common.RedisPrefixConst.COMMENT_USER_LIKE;

/**
 * 评论服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_comment】的数据库操作Service实现
 * @createDate 2022-11-26 15:16:59
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {


    @Autowired
    private HttpServletRequest request;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Autowired
    private RedisService redisService;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private WebsiteConfigService websiteConfigService;
    @Resource
    private TouristMapper touristMapper;
    @Autowired
    private TouristService touristService;


    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {
        // 查询评论量
        Long commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .eq(Comment::getType, commentVO.getType())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论数据
        List<CommentDTO> commentDTOList = commentMapper.listComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, commentCount);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        // 转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = commentMapper.listRepliesByCommentId(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentId);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        Tourist tourist =touristMapper.selectOne(new LambdaQueryWrapper<Tourist>()
                .eq(Tourist::getTouristAuth, UserUtils.generateUserMd5(request)));
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtils.filter(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(tourist.getId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();
        commentMapper.insert(comment);
    }

    @Override
    public void saveCommentLike(Integer commentId) {
        // 判断是否点赞
        String md5 = UserUtils.generateUserMd5(request);
        String commentLikeKey = COMMENT_USER_LIKE + md5;
        if (redisService.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisService.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisService.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisService.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVO.getIdList().stream().map(item -> Comment.builder()
                        .id(item)
                        .isReview(reviewVO.getIsReview())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }

    @Override
    public PageResult<CommentBackDTO> listCommentBackDTO(ConditionVO condition) {
        // 统计后台评论量
        long count = commentMapper.countCommentDTO(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentMapper.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackDTOList, count);
    }


    @Override
    public TouristDTO getTouristInfo() {
        Tourist tourist = touristService.getOne(new LambdaQueryWrapper<Tourist>()
                .eq(Tourist::getTouristAuth, UserUtils.generateUserMd5(request)));
        return BeanCopyUtils.copyObject(tourist, TouristDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reportTourist(TouristVO touristVO) {
        String md5 = UserUtils.generateUserMd5(request);
        Tourist tourist =touristMapper.selectOne(new LambdaQueryWrapper<Tourist>()
                .eq(Tourist::getTouristAuth, md5));
        Integer touristId = null;
        if (Objects.nonNull(tourist)) {
            touristId = tourist.getId();
        }
        touristService.saveOrUpdate(Tourist.builder()
                .id(touristId)
                .touristAuth(md5)
                .touristEmail(touristVO.getTouristEmail())
                .touristNickname(touristVO.getTouristNickname())
                .touristAvatar(touristVO.getTouristAvatar())
                .build());
    }
}




