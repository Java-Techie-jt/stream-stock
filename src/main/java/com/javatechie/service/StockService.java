package com.javatechie.service;

import com.javatechie.entity.Stock;
import com.javatechie.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class StockService {
    @Autowired
    private StockRepository stockRepository;



    public List<Stock> getAllStocks() {
        List<Stock> stockList = stockRepository.findAll();
        log.info("Retrieved {} stocks from the database.", stockList.size());
        return stockList;
    }

    public Flux<Stock> streamStocks(){
        return Flux.fromIterable(stockRepository.findAll())
                .delayElements(Duration.ofSeconds(1));
    }


}
