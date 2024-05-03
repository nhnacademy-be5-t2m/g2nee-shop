package com.t2m.g2nee.shop.memberset.address.repository;

import com.t2m.g2nee.shop.memberset.address.domain.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    int countAddressByMemberCustomerId(Long memberId);

    List<Address> getAllByMemberCustomerIdOrderByIsDefaultDesc(Long memberId);

    Address findAddressByMemberCustomerIdAndIsDefault(Long memberId, Boolean isDefault);
}
