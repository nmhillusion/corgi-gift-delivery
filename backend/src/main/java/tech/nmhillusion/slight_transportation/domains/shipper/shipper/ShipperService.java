package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-30
 */
public interface ShipperService {
    ShipperEntity findById(String id);

    ShipperEntity sync(ShipperEntity dto);

    void deleteById(String shipperId);

    Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    List<ShipperEntity> findAll();

    @TransactionalService
    class Impl implements ShipperService {
        private final ShipperRepository repository;

        public Impl(ShipperRepository repository) {
            this.repository = repository;
        }

        @Override
        public ShipperEntity findById(String id) {
            return repository.findById(id).orElse(null);
        }

        @Transactional
        @Override
        public ShipperEntity sync(ShipperEntity dto) {
            final ShipperEntity savedEntity = repository.save(dto);
            LogHelper.getLogger(this).info("savedEntity: {}", savedEntity);
            return savedEntity;
        }

        @Override
        public void deleteById(String shipperId) {
            repository.deleteById(shipperId);
        }

        @Override
        public Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            return repository.findBy(
                    StringUtil.trimWithNull(dto.get("shipperName")),
                    PageRequest.of(pageIndex, pageSize)
            );
        }

        @Override
        public List<ShipperEntity> findAll() {
            return repository.findAll();
        }
    }
}
