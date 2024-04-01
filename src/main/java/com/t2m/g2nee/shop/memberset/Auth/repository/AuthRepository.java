package com.t2m.g2nee.shop.memberset.Auth.repository;

import com.t2m.g2nee.shop.memberset.Auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByAuthName(String authName);
}
