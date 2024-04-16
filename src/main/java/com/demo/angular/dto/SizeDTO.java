package com.demo.angular.dto;

import com.demo.angular.model.Size;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SizeDTO {
    public SizeDTO(Size size){
        this.id = size.getId();
        this.sizeNumber = size.getSizeNumber();
    }
    Long id;
    Integer sizeNumber;
}
