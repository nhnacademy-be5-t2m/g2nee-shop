package com.t2m.g2nee.shop.orderset.packagetype.repository;

import com.t2m.g2nee.shop.orderset.packagetype.domain.PackageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 포장지 엔티티의 데이터베이스 테이블에 접근하는 메소드를 사용하기 위한 인터페이스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PackageRepository extends JpaRepository<PackageType, Long>, PackageRepositoryCustom {

    /**
     * 같은 이름을 가진 포장지가 있는지 확인합니다.
     *
     * @param name 포장지 이름
     * @return boolean
     */
    boolean existsByName(String name);

    /**
     * 특정 포장지 이름이면서 특정 포장지 id가 아닌 포장지가 존재하는지 확인합니다.
     * 다른 말로 포장지 id인 포장지를 제외하고 나머지 포장지 중 특정 이름을 가진 포장지가 있는지 확인합니다.
     * @param name 포장지 이름
     * @param packageId 패지키 id
     * @return 존재하면 ture, 없으면 false
     */
    boolean existsByNameAndPackageIdNot(String name, Long packageId);

    /**
     * 모든 포장지 정보를 페이징 처리하여 반환합니다.
     *
     * @param pageable 페이지 객체
     * @return Page<PackageType>
     */
    Page<PackageType> findAll(Pageable pageable);

    /**
     * 활성화된 모든 포장지 정보를 페이징 처리하여 반환합니다.
     *
     * @param pageable 페이지 객체
     * @return Page<PackageType>
     */
    Page<PackageType> findByIsActivatedTrue(Pageable pageable);
}
