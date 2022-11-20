package com.zl.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.entity.WebsiteConfig;
import com.zl.blog.pojo.vo.AboutMeVO;
import com.zl.blog.pojo.vo.WebsiteConfigVO;

/**
 * 网站配置信息服务
 *
 * @author 冷血毒舌
 * @description 针对表【sys_website_config】的数据库操作Service
 * @createDate 2022-11-16 13:58:37
 */
public interface WebsiteConfigService extends IService<WebsiteConfig> {

    WebsiteConfigVO getWebsiteConfig();

    void saveOrUpdateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    String getAbout();

    void saveOrUpdateAbout(AboutMeVO aboutMeVO);
}
