package org.spring.springboot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.spring.springboot.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.netty.util.concurrent.Future;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;



@RestController
public class CityRestController {
    private static final Logger logger = LoggerFactory.getLogger(CityRestController.class);

    @Autowired
    private CityService cityService;


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 插入 ES 新城市
     *
     * @param city
     * @return
     */
    @PostMapping(value = "/city")
    public Long createCity(@RequestBody City city) {
        logger.info("============"+cityService.saveCity(city));
        return cityService.saveCity(city);
    }

    /**
     * AND 语句查询
     *
     * @param description
     * @param score
     * @return
     */
    @GetMapping(value = "/find")
    public List<City> findByDescriptionAndScore(@RequestParam(value = "description") String description,
                                                @RequestParam(value = "score") Integer score) {
        logger.info("============"+cityService.findByDescriptionAndScore(description, score));
        return cityService.findByDescriptionAndScore(description, score);
    }

    /**
     * OR 语句查询
     *
     * @param description
     * @param score
     * @return
     */
    @RequestMapping(value = "/api/city/or/find", method = RequestMethod.GET)
    public List<City> findByDescriptionOrScore(@RequestParam(value = "description") String description,
                                               @RequestParam(value = "score") Integer score) {
        return cityService.findByDescriptionOrScore(description, score);
    }

    /**
     * 查询城市描述
     *
     * @param description
     * @return
     */
    @RequestMapping(value = "/api/city/description/find", method = RequestMethod.GET)
    public List<City> findByDescription(@RequestParam(value = "description") String description) {
        return cityService.findByDescription(description);
    }

    /**
     * NOT 语句查询
     *
     * @param description
     * @return
     */
    @RequestMapping(value = "/api/city/description/not/find", method = RequestMethod.GET)
    public List<City> findByDescriptionNot(@RequestParam(value = "description") String description) {
        return cityService.findByDescriptionNot(description);
    }

    /**
     * LIKE 语句查询
     *
     * @param description
     * @return
     */
    @RequestMapping(value = "/api/city/like/find", method = RequestMethod.GET)
    public List<City> findByDescriptionLike(@RequestParam(value = "description") String description) {
        return cityService.findByDescriptionLike(description);
    }

    /**
      * 询和sql语句查询
      * @Title JfindMesById
      * @Description: Java8 Stream查询和sql语句查询
      * @author: Aaron
      * @param:
      * @return:
      */
    @GetMapping(value = "/findMesByName")
    public List<City> findMesByName(@RequestParam(value = "name") String name) {
        logger.info("==================="+cityService.findMesByName(name));
        return cityService.findMesByName(name);
    }



    /**
      * 使用ElasticsearchTemplate进行单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
      * @Title:
      * @Description:
      * @author: Aaron
      * @param:
      * @return:
      */
    @GetMapping("/singleWord")
    public Object singleTitle(@RequestParam(value = "name") String name, @PageableDefault Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(name)).withPageable(pageable).build();
        logger.info("==================="+elasticsearchTemplate.queryForList(searchQuery, City.class));
        return elasticsearchTemplate.queryForList(searchQuery, City.class);
    }


    /**
     * 使用ElasticsearchTemplate进行单字符串模糊查询，单字段排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     * @Title:
     * @Description:
     * @author: Aaron
     * @param:
     * @return:
     */
    @GetMapping("/singleWord2")
    public Object singlePost(@RequestParam(value = "name") String name, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(name)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, City.class);
    }


    /**
      * 单字段对某字符串模糊查询
      * @Title:
      * @Description:
      * @author: Aaron
      * @param:
      * @return:
      */
    @GetMapping("/singleMatch")
    public Object singleMatch(@RequestParam(value = "name") String name, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("name", name)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, City.class);
    }


    /**
      * 多个字段匹配某字符串
      * @Title:
      * @Description: 
      * @author: Aaron
      * @param: 
      * @return: 
      */
    @GetMapping("/multiMatch")
    public Object singleUserId(@RequestParam(value = "name") String name, @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(name,"description","name")).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, City.class);
    }


    /**
     * 使用Query注解
     * @Title
     * @Description:
     * @author: Aaron
     * @param:
     * @return:
     */
    @GetMapping(value = "/findByNameIgnoreCase")
    public List<City> findByNameIgnoreCase(@RequestParam(value = "name") String name) {
        logger.info("==================="+cityService.findByNameIgnoreCase(name));
        return cityService.findByNameIgnoreCase(name);
    }


    /**
     * 异步处理查询结果
     * @Title
     * @Description:
     * @author: Aaron
     * @param:
     * @return:
     */
    @GetMapping(value = "/findByName")
    public Result<List<Future<City>>> findByName(@RequestParam(value = "name") String name) {
        logger.info("===================name1");
        logger.info("===================name2");
        logger.info("===================name3");

        //异常断言工具
        Assert.hasLength(name,"sss");
        return Result.success(cityService.findByName(name));
    }







    





}
