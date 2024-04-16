package com.demo.angular.config.service;

import com.demo.angular.model.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // todo: add checks if authentication is present and user is not null (i.e. signed in).

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        try{
            UserDetailsImp u =(UserDetailsImp) authentication.getPrincipal();
            return Optional.of(u.getUsername());
        }catch (Exception e){
            return Optional.of("");
        }
    }

}
