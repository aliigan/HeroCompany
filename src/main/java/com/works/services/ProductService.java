package com.works.services;

import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }


    public ResponseEntity<Map<REnum,Object>> save(Product product){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Product p = productRepository.save(product);
        hm.put(REnum.status,true);
        hm.put(REnum.result, product);
        return new  ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum ,Object>>  list(){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, productRepository.findAll());
        return new  ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum ,Object>> delete (Long  pid ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            productRepository.deleteById(pid);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<REnum ,Object>> update(Product product){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try{
            Optional<Product> oProduct = productRepository.findById(product.getPid());
            if(oProduct.isPresent()){
                productRepository.saveAndFlush(product);
                hm.put(REnum.status, true);
                hm.put(REnum.result, product);

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

