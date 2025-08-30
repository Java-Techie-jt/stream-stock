package com.javatechie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.entity.Stock;
import com.javatechie.service.StockService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/stream")
    public StreamingResponseBody streamStocks(HttpServletResponse response) {
        response.setContentType("text/event-stream");
        return outputStream -> {
            stockService.getAllStocks()
                    .forEach(stock -> {
                        try {
                            String json = new ObjectMapper()
                                    .writeValueAsString(stock) + "\n";
                            outputStream.write(json.getBytes());
                            outputStream.flush();

                        } catch (Exception ex) {
                            throw new RuntimeException(ex.getMessage());
                        }
                    });
        };
    }

    @GetMapping("/live")
    public StreamingResponseBody streamStockPrices(HttpServletResponse response) {
        response.setContentType("text/event-stream"); // so browser/clients treat it as a stream

        return (OutputStream outputStream) -> {
            try {
                for (int i = 0; i < 20; i++) { // simulate 20 updates
                    double price = 100 + new Random().nextDouble() * 10; // random stock price
                    String update = "Stock Price: " + price + "\n";

                    outputStream.write(update.getBytes());
                    outputStream.flush();

                    Thread.sleep(1000); // every 1 second new update
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }


}
