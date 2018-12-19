package com.spring.elasticsearch.controller;

import com.spring.elasticsearch.domain.City;
import com.spring.elasticsearch.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("city")
public class CityController {

    @Autowired
    CityService cityService;

    @RequestMapping("/save")
    public String save() {
        City city = new City(System.currentTimeMillis(), "城市" + System.currentTimeMillis(), "这是一个测试城市");
        cityService.saveCity(city);
        return "success";
    }

    @RequestMapping("/searchCity")
    public List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        return cityService.searchCity(pageNumber, pageSize, searchContent);
    }
}
