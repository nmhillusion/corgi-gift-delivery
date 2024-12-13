package tech.nmhillusion.slight_transportation.domains.recipient.recipientType;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.RecipientTypeEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface RecipientTypeService {
    List<RecipientTypeEntity> findAll();

    RecipientTypeEntity findById(String customerTypeId);

    @TransactionalService
    class Impl implements RecipientTypeService {
        private final RecipientTypeRepository repository;

        public Impl(RecipientTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<RecipientTypeEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public RecipientTypeEntity findById(String customerTypeId) {
            return repository.findById(Integer.parseInt(customerTypeId)).orElse(null);
        }
    }
}
