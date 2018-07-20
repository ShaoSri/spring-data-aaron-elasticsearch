package org.spring.springboot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.domain.City;
import org.spring.springboot.repository.CityRepository;
import org.spring.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import io.netty.util.concurrent.Future;


@Service
public class CityESServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityESServiceImpl.class);

    // 分页参数 -> TODO 代码可迁移到具体项目的公共 common 模块
    private static final Integer pageNumber = 0;
    private static final Integer pageSize = 10;
    Pageable pageable = new PageRequest(pageNumber, pageSize);

    // ES 操作类
    @Autowired
    CityRepository cityRepository;

    @Override
    public Long saveCity(City city) {
       // City cityResult = cityRepository.save(city);
        return null;//cityResult.getId();
    }

    @Override
    public List<City> findByDescriptionAndScore(String description, Integer score) {
        return cityRepository.findByDescriptionAndScore(description, score);
    }

    @Override
    public List<City> findByDescriptionOrScore(String description, Integer score) {
        return cityRepository.findByDescriptionOrScore(description, score);
    }

    @Override
    public List<City> findByDescription(String description) {
        return cityRepository.findByDescription(description, pageable).getContent();
    }

    @Override
    public List<City> findByDescriptionNot(String description) {
        return cityRepository.findByDescriptionNot(description, pageable).getContent();
    }

    @Override
    public List<City> findByDescriptionLike(String description) {
        return cityRepository.findByDescriptionLike(description, pageable).getContent();
    }



    /**
     * QueryBuilder
     * @Title JfindMesById
     * @Description: QueryBuilder
     * @author: Aaron
     * @param name
     * @return list
     */
    @Override
    public List<City> findMesByName(String name) {
        return cityRepository.findMesByName(name);
    }


    /**
     * 使用Query注解
     * @Title queryMesByName
     * @Description:
     * @author: Aaron
     * @param name
     * @return list
     */
    @Override
    public List<City> findByNameIgnoreCase(String name) {
        return cityRepository.findByNameIgnoreCase(name);
    }


    /**
     * 异步处理查询结果
     * @Title
     * @Description
     * @author: Aaron
     * @param:
     * @return:
     */
    @Override
    public List<Future<City>> findByName(String name) {
        return cityRepository.findByName(name);
    }



}
