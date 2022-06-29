package com.works.services;

import com.works.entities.Admin;
import com.works.repositories.AdminRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public ResponseEntity<Map<REnum, Object>> save(Admin admin){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        Admin ad = adminRepository.save(admin);
        hm.put(REnum.status, true);
        hm.put(REnum.result, admin);
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> list(){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, adminRepository.findAll());
        return new ResponseEntity<>(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum, Object>> delete(Long aid){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            adminRepository.deleteById(aid);
            hm.put(REnum.status, true);
            return new ResponseEntity<>(hm, HttpStatus.OK);
        }catch (Exception ex){
            hm.put(REnum.status, false);
            hm.put(REnum.message, ex.getMessage());
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<REnum, Object>> update(Admin admin){
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            Optional<Admin> oAdmin = adminRepository.findById(admin.getAid());
            if (oAdmin.isPresent()){
                adminRepository.saveAndFlush(admin);
                hm.put(REnum.status, true);
                hm.put(REnum.result, admin);
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
