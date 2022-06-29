package com.works.services;

import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {
    final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }


    public ResponseEntity<Map<REnum, Object>> save(Customer customer) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Customer cu = customerRepository.save(customer);
        hm.put(REnum.status, true);
        hm.put(REnum.result, customer);
        return new ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> list() {

        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, true);
        hm.put(REnum.result, customerRepository.findAll());

        return new ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity<Map<REnum, Object>> delete(Long id) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            customerRepository.deleteById(id);
            hm.put(REnum.status, true);
            return new ResponseEntity(hm, HttpStatus.OK);
        } catch (Exception ex) {
            hm.put(REnum.status, false);
            hm.put(REnum.message, ex.getMessage());
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Map<REnum, Object>> update(Customer customer) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            Optional<Customer> oCustomer = customerRepository.findById(customer.getId());
            if (oCustomer.isPresent()) {
                customerRepository.saveAndFlush(customer);
                hm.put(REnum.status, true);
                hm.put(REnum.result, customer);

                return new ResponseEntity(hm, HttpStatus.OK);
            } else {
                hm.put(REnum.status, false);
                return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            hm.put(REnum.status, false);
            hm.put(REnum.message, e.getMessage());
        }
        return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
    }
}
