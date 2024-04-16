package com.demo.angular.payload.request;

import com.demo.angular.model.EStatusOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeStatusReq {
    EStatusOrder status;
}
