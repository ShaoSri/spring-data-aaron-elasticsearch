package org.spring.springboot.repository;

import io.netty.util.concurrent.Future;
import org.spring.springboot.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import java.util.List;


public interface CityRepository extends ElasticsearchRepository<City, Long> {
    /**
     * AND 语句查询
     *
     * @param description
     * @param score
     * @return
     */
    List<City> findByDescriptionAndScore(String description, Integer score);

    /**
     * OR 语句查询
     *
     * @param description
     * @param score
     * @return
     */
    List<City> findByDescriptionOrScore(String description, Integer score);

    /**
     * 查询城市描述
     *
     * 等同于下面代码
     * @Query("{\"bool\" : {\"must\" : {\"term\" : {\"description\" : \"?0\"}}}}")
     * Page<City> findByDescription(String description, Pageable pageable);
     *
     * @param description
     * @param page
     * @return
     */
    Page<City> findByDescription(String description, Pageable page);

    /**
     * NOT 语句查询
     *
     * @param description
     * @param page
     * @return
     */
    Page<City> findByDescriptionNot(String description, Pageable page);

    /**
     * LIKE 语句查询
     *
     * @param description
     * @param page
     * @return
     */
    Page<City> findByDescriptionLike(String description, Pageable page);

    /**
     * QueryBuilder查询,通过方法名去解析
     * @Title:JfindMesById
     * @Description:Java8 QueryBuilder
     * @author  Aaron
     * @param name
     * @return list
     */
    List<City> findMesByName(String name);


    /**
     * 使用Query注解
     * @Title findByName
     * @Description:
     * @author: Aaron
     * @param name
     * @return list
     */
    @Query("select a from city a where a.id = ?0")
    List<City> findByNameIgnoreCase(String name);



    /**
     * 异步处理查询结果
     * @Title
     * @Description:
     * @author: Aaron
     * @param:
     * @return:
     */
    @Async
    List<Future<City>> findByName(String name);





}
