package org.spring.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */
@SpringBootApplication
@EnableElasticsearchRepositories
public class Application {

  public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
