package com.zl.blog.strategy.context;

import com.zl.blog.pojo.dto.ArticleSearchDTO;
import com.zl.blog.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.zl.blog.common.enums.SearchModeEnum.getStrategy;

/**
 * 搜索策略上下文
 *
 * @author 冷血毒舌
 * @create 2022/11/26 11:21
 */
@Service
public class SearchStrategyContext {

    /**
     * 上传模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;


    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>}
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(getStrategy(searchMode)).getArticles(keywords);
    }

}
