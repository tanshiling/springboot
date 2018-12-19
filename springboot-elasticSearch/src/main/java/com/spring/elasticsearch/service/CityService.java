package com.spring.elasticsearch.service;

import com.spring.elasticsearch.domain.City;

import java.util.List;

public interface CityService {

    Long saveCity(City city);

    List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent);
}
