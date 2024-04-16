package com.demo.angular.repository;

import com.demo.angular.model.Role;
import com.demo.angular.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
}
