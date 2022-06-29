package com.works.services;

import com.works.entities.Orders;
import com.works.repositories.OrdersRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrdersService {
    final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {

        this.ordersRepository = ordersRepository;
    }


    public ResponseEntity<Map<REnum,Object>> save(Orders orders){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Orders or = ordersRepository.save(orders);
        hm.put(REnum.status,true);
        hm.put(REnum.result, orders);
        return new  ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum ,Object>>  list(){

        Map<REnum,Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, ordersRepository.findAll());

        return new  ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum ,Object>> delete(Long  oid ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            ordersRepository.deleteById(oid);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum ,Object>> Orders (Orders orders){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try{
            Optional<Orders> oOrders = ordersRepository.findById(orders.getOid());
            if(oOrders.isPresent()){
                ordersRepository.saveAndFlush(orders);
                hm.put(REnum.status, true);
                hm.put(REnum.result, orders);

                return new  ResponseEntity(hm, HttpStatus.OK);
            }else{
                hm.put(REnum.status, false);
                return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            hm.put(REnum.status, false);
            hm.put(REnum.message, e.getMessage());
        }
        return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
    }
}
