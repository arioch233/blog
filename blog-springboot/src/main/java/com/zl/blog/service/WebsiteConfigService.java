package com.zl.blog.service;

import com.zl.blog.entity.WebsiteConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zl.blog.pojo.vo.WebsiteConfigVO;

/**
* @author 冷血毒舌
* @description 针对表【sys_website_config】的数据库操作Service
* @createDate 2022-11-16 13:58:37
*/
public interface WebsiteConfigService extends IService<WebsiteConfig> {

    WebsiteConfigVO getWebsiteConfig();
}
