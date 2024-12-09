package tech.nmhillusion.slight_transportation.domains.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.entity.business.CustomerEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface CustomerService {
    CustomerEntity sync(CustomerEntity customerEntity);

    CustomerEntity findById(String id);

    Page<CustomerEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    void deleteById(String customerId);

    class Impl implements CustomerService {

        private final CustomerRepository repository;

        public Impl(CustomerRepository repository) {
            this.repository = repository;
        }

        @Override
        public CustomerEntity sync(CustomerEntity customerEntity) {
            final CustomerEntity savedEntity = repository.save(customerEntity);
            LogHelper.getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public CustomerEntity findById(String id) {
            return repository.findById(Long.parseLong(id)).orElse(null);
        }

        @Override
        public Page<CustomerEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String name = StringUtil.trimWithNull(dto.get("name"));

            LogHelper.getLogger(this).info("name: {}", name);
            return repository.search(
                    StringUtil.trimWithNull(name),
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public void deleteById(String customerId) {
            repository.deleteById(Long.parseLong(customerId));
        }
    }
}
