package com.t2m.g2nee.shop.bookset.role.repository;

import com.t2m.g2nee.shop.bookset.role.domain.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT r FROM Role r WHERE r.isActivated=true")
    Page<Role> findAllActivated(Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.isActivated=true ORDER BY r.roleName")
    List<Role> findAllActivated();


}
