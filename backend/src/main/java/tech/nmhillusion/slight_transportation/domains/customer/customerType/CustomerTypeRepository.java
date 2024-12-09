package tech.nmhillusion.slight_transportation.domains.customer.customerType;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.nmhillusion.slight_transportation.entity.business.CustomerTypeEntity;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */

public interface CustomerTypeRepository extends JpaRepository<CustomerTypeEntity, Integer> {
}
