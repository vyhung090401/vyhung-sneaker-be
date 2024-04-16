package com.demo.angular.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectResponse implements Serializable {
  private String status;
  private String message;
  private Object data;
}
