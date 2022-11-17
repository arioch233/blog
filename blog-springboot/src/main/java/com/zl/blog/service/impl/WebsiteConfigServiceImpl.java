package com.zl.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.blog.entity.WebsiteConfig;
import com.zl.blog.mapper.WebsiteConfigMapper;
import com.zl.blog.pojo.vo.WebsiteConfigVO;
import com.zl.blog.service.RedisService;
import com.zl.blog.service.WebsiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.zl.blog.common.CommonConst.DEFAULT_CONFIG_ID;
import static com.zl.blog.common.RedisPrefixConst.WEBSITE_CONFIG;
import static com.zl.blog.common.RedisPrefixConst.WEBSITE_NAME;

/**
 * @author 冷血毒舌
 * @description 针对表【sys_website_config】的数据库操作Service实现
 * @createDate 2022-11-16 13:58:37
 */
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig>
        implements WebsiteConfigService {

    @Autowired
    private RedisService redisService;

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 从缓存中加载
        Object websiteConfigInRedis = redisService.hGet(WEBSITE_NAME, WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfigInRedis)) {
            websiteConfigVO = JSON.parseObject(websiteConfigInRedis.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            // 重新设置缓存
            redisService.hSet(WEBSITE_NAME, WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }
}




