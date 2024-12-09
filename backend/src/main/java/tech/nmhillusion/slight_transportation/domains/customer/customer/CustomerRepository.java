package tech.nmhillusion.slight_transportation.domains.customer.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.nmhillusion.slight_transportation.entity.business.CustomerEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(" select s from CustomerEntity s where :name is null or s.fullName like %:name% ")
    Page<CustomerEntity> search(String name, PageRequest pageRequest);

}
