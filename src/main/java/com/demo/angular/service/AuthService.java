package com.demo.angular.service;

import com.demo.angular.payload.request.ChangePasswordReq;

public interface AuthService {

    boolean validateCurrentPassword(ChangePasswordReq currentPassword);

    void changePassword(ChangePasswordReq currentPassword);
}
