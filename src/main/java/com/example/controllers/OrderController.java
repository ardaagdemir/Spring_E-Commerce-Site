package com.example.controllers;

import com.example.entities.Order;
import com.example.entities.Product;
import com.example.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    protected final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Order order){
        return orderService.save(order);
    }
    @GetMapping("/getOrders")
    public ResponseEntity getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/getOrderById/{uid}")
    public ResponseEntity getOrderById(@PathVariable int uid){
        return orderService.getOrderById(uid);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{oid}")
    public ResponseEntity delete(@PathVariable String oid){
        return orderService.delete(oid);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Order order){
        return orderService.update(order);
    }

}
