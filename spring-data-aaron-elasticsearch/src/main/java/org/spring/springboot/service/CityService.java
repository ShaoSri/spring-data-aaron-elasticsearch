
package org.spring.springboot.service;

import org.spring.springboot.domain.City;

import java.util.List;

import io.netty.util.concurrent.Future;

/**
 * 城市 ES 业务接口类
 *
 */
public interface CityService {

    /**
     * 新增 ES 城市信息
     *
     * @param city
     * @return
     */
    Long saveCity(City city);

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
     * @param description
     * @return
     */
    List<City> findByDescription(String description);

    /**
     * NOT 语句查询
     *
     * @param description
     * @return
     */
    List<City> findByDescriptionNot(String description);

    /**
     * LIKE 语句查询
     *
     * @param description
     * @return
     */
    List<City> findByDescriptionLike(String description);


    /**
     * Java8 Stream查询和sql语句查询
     * @Title JfindMesById
     * @Description: QueryBuilder
     * @author: Aaron
     * @param name
     * @return:
     */
    List<City> findMesByName(String name);


    /**
     * 使用Query注解
     * @Title queryMesByName
     * @Description:
     * @author: Aaron
     * @param name
     * @return list
     */
    List<City> findByNameIgnoreCase(String name);

    /**
     * 异步处理查询结果
     * @Title
     * @Description
     * @author: Aaron
     * @param:
     * @return:
     */
    List<Future<City>> findByName(String name);



}