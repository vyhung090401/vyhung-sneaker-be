package com.demo.angular.dto;

import com.demo.angular.model.Image;
import com.demo.angular.model.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO implements Serializable {
    public ImageDTO(Image image){
        this.link = image.getLink();
        this.type = image.getType();
        this.picByte = image.getPicByte();
    }
    Long id;
    String link;
    String type;
    byte[] picByte;
}
