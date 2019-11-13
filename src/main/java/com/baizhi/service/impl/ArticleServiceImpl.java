package com.baizhi.service.impl;

import com.baizhi.annotation.ClearRedis;
import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.repository.ArticleRepository;
import com.baizhi.service.ArticleService;
import org.apache.commons.collections4.IterableUtils;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        System.out.println("ssss");
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        Integer count = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", articles);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    @ClearRedis
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        //添加到全文检索
        articleRepository.save(article);
        int i = articleDao.insert(article);
        if (i == 0) {
            throw new RuntimeException("添加文章失败");
        }
    }

    @Override
    @ClearRedis
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if (i == 0) {
            throw new RuntimeException("修改文章失败");
        } else {
            //删除es的数据
            Article article1 = articleDao.selectByPrimaryKey(article.getId());
            articleRepository.save(article1);
        }
    }

    @Override
    @ClearRedis
    public void delete(String id) {
        int i = articleDao.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new RuntimeException("删除失败");
        } else {
            articleRepository.deleteById(id);
        }
    }

    @Override
    public List<Article> search(String content) {
        if ("".equals(content)) {//如果用户什么都没输入就默认查所有
            Iterable<Article> all = articleRepository.findAll();
            List<Article> articles = IterableUtils.toList(all);
            return articles;
        } else {
            //设置高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("*")//指明高亮的标签
                    .preTags("<code>")
                    .postTags("</code>")
                    .requireFieldMatch(false);//开启全字段高亮
            NativeSearchQuery build = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("author").field("brirf").field("content"))
                    .withSort(SortBuilders.scoreSort())//排序
                    .withHighlightBuilder(highlightBuilder)//高亮
                    .build();
            AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(build, Article.class, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                    //取到当前所有的击中                   SearchResponse  具体查到的响应
                    SearchHits searchHits = response.getHits();
                    //获取每一个击中的对象
                    SearchHit[] hits = searchHits.getHits();
                    List<Article> list = new ArrayList<>();
                    //遍历每一个击中 iter
                    for (SearchHit hit : hits) {
                        //获取具体的文章   原始数据
                        Map<String, Object> map = hit.getSourceAsMap();
                        Article article = new Article();
                        article.setId(map.get("id").toString());
                        article.setTitle(map.get("title").toString());
                        article.setAuthor(map.get("author").toString());
                        article.setBrief(map.get("brief").toString());
                        article.setContent(map.get("content").toString());
                        String date = map.get("createDate").toString();
                        article.setCreateDate(new Date(Long.valueOf(date)));
                        //处理高亮
                        Map<String, HighlightField> fieldMap = hit.getHighlightFields();
                        if (fieldMap.get("title") != null) {
                            article.setTitle(fieldMap.get("title").fragments()[0].toString());
                        }
                        if (fieldMap.get("author") != null) {
                            article.setAuthor(fieldMap.get("author").fragments()[0].toString());
                        }
                        if (fieldMap.get("brief") != null) {
                            article.setBrief(fieldMap.get("brief").fragments()[0].toString());
                        }
                        if (fieldMap.get("content") != null) {
                            article.setContent(fieldMap.get("content").fragments()[0].toString());
                        }
                        list.add(article);
                    }
                    return new AggregatedPageImpl<T>((List<T>) list);
                }

                @Override
                public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                    return null;
                }
            });
            return articles.getContent();
        }
    }
}
