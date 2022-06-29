package com.works.services;

import com.works.entities.Admin;
import com.works.entities.Basket;
import com.works.repositories.BasketRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BasketService {
    final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }


    public ResponseEntity<Map<REnum, Object>> save(Basket basket){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Basket b = basketRepository.save(basket);
        hm.put(REnum.status, true);
        hm.put(REnum.result, basket);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> list(){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, basketRepository.findAll());
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> delete(Long bid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            basketRepository.deleteById(bid);
            hm.put(REnum.status, true);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }catch (Exception ex){
            hm.put(REnum.status, false);
            hm.put(REnum.message, ex.getMessage());
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum, Object>> update(Basket basket) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            Optional<Basket> optionalBasket = basketRepository.findById(basket.getBid());
            if (optionalBasket.isPresent()){
                basketRepository.saveAndFlush(basket);
                hm.put(REnum.status, true);
                hm.put(REnum.result, basket);
                return new ResponseEntity(hm, HttpStatus.OK);
            }else{
                hm.put(REnum.status, false);
                return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            hm.put(REnum.status, false);
            hm.put(REnum.message, ex.getMessage());
        }
        return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
    }
}
