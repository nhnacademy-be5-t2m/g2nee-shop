package com.t2m.g2nee.shop.memberset.auth.repository;

import com.t2m.g2nee.shop.memberset.auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByAuthName(Auth.AuthName authName);
}
