package com.zl.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.UniqueView;
import com.zl.blog.mapper.UniqueViewMapper;
import com.zl.blog.pojo.dto.UniqueViewDTO;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.UniqueViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.zl.blog.common.RedisPrefixConst.UNIQUE_VISITOR;
import static com.zl.blog.common.RedisPrefixConst.VISITOR_AREA;
import static com.zl.blog.common.enums.ZoneEnum.SHANGHAI;

/**
 * 访问量服务实现
 *
 * @author 冷血毒舌
 * @description 针对表【tb_unique_view】的数据库操作Service实现
 * @createDate 2022-11-21 15:18:18
 */
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView>
        implements UniqueViewService {

    @Autowired
    private RedisService redisService;

    @Resource
    private UniqueViewMapper uniqueViewMapper;

    @Override
    public List<UniqueViewDTO> listUniqueView() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewMapper.listUniqueView(startTime, endTime);
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveUniqueView() {
        // 获取每天用户量
        Long count = redisService.sSize(UNIQUE_VISITOR);
        // 获取昨天日期插入数据
        uniqueViewMapper.insert(UniqueView.builder()
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .createTime(LocalDateTimeUtil.offset(LocalDateTime
                        .now(ZoneId.of(SHANGHAI.getZone())), -1, ChronoUnit.DAYS))
                .build());
    }

    @Scheduled(cron = "0 1 0 * * ?", zone = "Asia/Shanghai")
    public void clear() {
        // 清空redis访客记录
        redisService.del(UNIQUE_VISITOR);
        // 清空redis游客地区统计
        redisService.del(VISITOR_AREA);

    }
}




