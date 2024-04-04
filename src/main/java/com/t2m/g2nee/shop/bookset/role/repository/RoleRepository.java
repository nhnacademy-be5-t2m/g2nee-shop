package com.t2m.g2nee.shop.bookset.role.repository;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);


}
