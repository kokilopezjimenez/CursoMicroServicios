package com.curso.java.bookservice.service;

import com.curso.java.bookservice.client.stockservice.Stock;
import com.curso.java.bookservice.client.stockservice.StockClient;
import com.curso.java.bookservice.client.stockservice.StockUpdate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockService {

    private final StockClient stockClient;

    private final Map<Long, Stock> cache;

    @Autowired
    public StockService(StockClient stockClient){
        this.stockClient = stockClient;
        this.cache = new HashMap<>();
    }



    public Stock find(Long stockId){
        System.out.println("llamando a FindStock(stockId:"+stockId+")");
        var stockResponse = stockClient.findStock(stockId);
        if(stockResponse.getStatusCode().equals(HttpStatus.OK)){
            var stock = stockResponse.getBody();
            cache.put(stock.getId(), stock);
            return stock;
        }
        return null;
    }

    public Stock save(Stock stock){
        var stockResponse = stockClient.save(stock);
        if(stockResponse.getStatusCode().equals(HttpStatus.CREATED)){
            return stockResponse.getBody();
        }
        return null;
    }

    public Stock update(StockUpdate stock, long stockId){
        var stockResponse = stockClient.update(stockId,stock);
        if(stockResponse.getStatusCode().equals(HttpStatus.OK)){
            return stockResponse.getBody();
        }
        return null;
    }



}