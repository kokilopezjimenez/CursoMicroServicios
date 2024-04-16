
package com.curso.java.stockservice.service;

import com.curso.java.stockservice.exceptions.NotFoundException;
import com.curso.java.stockservice.exceptions.ValidationException;
import com.curso.java.stockservice.model.Stock;
import com.curso.java.stockservice.model.StockUpdate;
import com.curso.java.stockservice.repository.StockRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    
    private final StockRepository stockRepository;
    
    @Autowired
    public StockService(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    
    public Stock find(long stockId){
        Optional<Stock> stockOpt = this.stockRepository.find(stockId);
        return stockOpt.orElseThrow(() -> {
            return new NotFoundException(String.format("Stock "+stockId+" not found"));
        });
    }
    
    public Stock save(Stock stock){
        return this.stockRepository.save(stock);
    }
    
    public Stock update(long stockId, StockUpdate stockUpdate){
        var stock = this.find(stockId);
        if(stock.getCurrentQuantity() + stockUpdate.getIncreaseQuantity() < 0){
            throw new ValidationException(String.format("Not enough quantity in stock "+stockId));
        } else {
            stock.setCurrentQuantity(stock.getCurrentQuantity()+stockUpdate.getIncreaseQuantity());
        }
        return this.stockRepository.save(stock);
    }
    
}
