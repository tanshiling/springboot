package com.spring.elasticsearch.service.impl;

import com.spring.elasticsearch.domain.City;
import com.spring.elasticsearch.repository.CityRepository;
import com.spring.elasticsearch.service.CityService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

public class CityServiceImpl implements CityService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    CityRepository cityRepository;

    Integer PAGE_SIZE = 5;
    Integer DEFAULT_PAGE_NUMBER = 0;

    String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    Float MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    @Override
    public Long saveCity(City city) {
        City cityResult = cityRepository.save(city);
        return cityResult.getId();
    }

    @Override
    public List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        if (pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        SearchQuery searchQuery = getCitySearchQuery(pageNumber, pageSize, searchContent);
        Page<City> cityPage = cityRepository.search(searchQuery);
        return cityPage.getContent();
    }

    private SearchQuery getCitySearchQuery(Integer pageNumber, Integer pageSize, String searchContent) {

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = {
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchPhraseQuery("name", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(1000)),
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchPhraseQuery("description", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(500)),
        };
        FunctionScoreQueryBuilder functionScoreQueryBuilder =
                functionScoreQuery(filterFunctionBuilders)
                        .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                        .setMinScore(MIN_SCORE);

        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);

        return new NativeSearchQueryBuilder()
                .withPageable(pageRequest)
                .withQuery(functionScoreQueryBuilder)
                .build();
    }
}
