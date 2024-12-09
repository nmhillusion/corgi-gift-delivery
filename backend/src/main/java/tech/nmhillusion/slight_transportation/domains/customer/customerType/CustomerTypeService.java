package tech.nmhillusion.slight_transportation.domains.customer.customerType;

import org.springframework.stereotype.Service;
import tech.nmhillusion.slight_transportation.entity.business.CustomerTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface CustomerTypeService {
    List<CustomerTypeEntity> findAll();

    CustomerTypeEntity findById(String customerTypeId);

    @Service
    class Impl implements CustomerTypeService {
        private final CustomerTypeRepository repository;

        public Impl(CustomerTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<CustomerTypeEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public CustomerTypeEntity findById(String customerTypeId) {
            return repository.findById(Integer.parseInt(customerTypeId)).orElse(null);
        }
    }
}
