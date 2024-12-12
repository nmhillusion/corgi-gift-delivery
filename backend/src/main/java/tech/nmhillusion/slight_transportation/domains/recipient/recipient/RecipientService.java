package tech.nmhillusion.slight_transportation.domains.recipient.recipient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.entity.business.RecipientEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-09
 */
public interface RecipientService {
    RecipientEntity sync(RecipientEntity recipientEntity);

    RecipientEntity findById(String id);

    Page<RecipientEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    void deleteById(String customerId);

    @Service
    class Impl implements RecipientService {

        private final RecipientRepository repository;

        public Impl(RecipientRepository repository) {
            this.repository = repository;
        }

        @Override
        public RecipientEntity sync(RecipientEntity recipientEntity) {
            final RecipientEntity savedEntity = repository.save(recipientEntity);
            LogHelper.getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public RecipientEntity findById(String id) {
            return repository.findById(Long.parseLong(id)).orElse(null);
        }

        @Override
        public Page<RecipientEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
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
