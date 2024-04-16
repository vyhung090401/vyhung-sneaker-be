package com.demo.angular.controller;

import com.demo.angular.dto.SizeDTO;
import com.demo.angular.model.Size;
import com.demo.angular.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class SizeController {

    @Autowired
    SizeRepository sizeRepository;

    @GetMapping("/size")
    public ResponseEntity<?> getListSize(){
        List<Size> sizes = sizeRepository.findAll();
        List<SizeDTO> sizeDTOS = new ArrayList<>();
        for (Size size : sizes){
            SizeDTO sizeDTO = new SizeDTO(size);
            sizeDTOS.add(sizeDTO);
        }
        return ResponseEntity.ok(sizeDTOS);
    }
}
