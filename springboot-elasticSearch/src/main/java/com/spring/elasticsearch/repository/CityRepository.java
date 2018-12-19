package com.spring.elasticsearch.repository;

import com.spring.elasticsearch.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface CityRepository extends ElasticsearchRepository<City, Long> {

}
