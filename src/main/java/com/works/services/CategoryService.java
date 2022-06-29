package com.works.services;

import com.works.entities.Basket;
import com.works.entities.Category;
import com.works.repositories.CategoryRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public ResponseEntity<Map<REnum, Object>> save(Category category){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Category c = categoryRepository.save(category);
        hm.put(REnum.status, true);
        hm.put(REnum.result, category);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> list(){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, categoryRepository.findAll());
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> delete(Long kid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            categoryRepository.deleteById(kid);
            hm.put(REnum.status, true);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }catch (Exception ex){
            hm.put(REnum.status, false);
            hm.put(REnum.message, ex.getMessage());
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Map<REnum, Object>> update(Category category) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(category.getKid());
            if (optionalCategory.isPresent()){
                categoryRepository.saveAndFlush(category);
                hm.put(REnum.status, true);
                hm.put(REnum.result, category);
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
