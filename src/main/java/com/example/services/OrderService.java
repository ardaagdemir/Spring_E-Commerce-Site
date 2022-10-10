package com.example.services;

import com.example.entities.Order;
import com.example.entities.Product;
import com.example.entities.joinTables.JoinOrderDetails;
import com.example.repositories.joinRepository.JoinOrderDetailsRepository;
import com.example.repositories.OrderRepository;
import com.example.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    final OrderRepository orderRepository;
    final JoinOrderDetailsRepository joinOrderListRepository;

    public ResponseEntity save(Order order){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try{
            hm.put(REnum.status, true);
            hm.put(REnum.result, orderRepository.save(order) );
        }catch (IllegalArgumentException e){
            hm.put(REnum.message, e);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity delete(String oid) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            int id = Integer.parseInt(oid);
            orderRepository.deleteById(id);
            hm.put(REnum.status, true);
            hm.put(REnum.message, "The order has been deleted.");
        } catch (Exception ex) {
            hm.put(REnum.message, oid);
            hm.put(REnum.status, false);
            hm.put(REnum.message, "Order not found.");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity update(Order order) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<JoinOrderDetails> optionalProduct = joinOrderListRepository.findById(order.getOid());
        if (optionalProduct.isPresent()) {
            orderRepository.saveAndFlush(order);
            hm.put(REnum.status, true);
            hm.put(REnum.result, order);
        } else {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "User name not found.");
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }
    public ResponseEntity getOrders() {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, joinOrderListRepository.getOrders());
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity getOrderById(int uid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, joinOrderListRepository.getOrderById(uid));
        return new ResponseEntity(hm, HttpStatus.OK);
    }

}
