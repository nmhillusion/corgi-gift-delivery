package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.Map;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-01-26
 */
public interface ShipperService {

    ShipperEntity save(ShipperEntity shipperEntity);

    void deleteById(int shipperId);

    ShipperEntity findById(int shipperId);

    Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize);

    @TransactionalService
    class Impl implements ShipperService {

        private final ShipperRepository repository;
        private final SequenceService sequenceService;

        public Impl(ShipperRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public ShipperEntity save(ShipperEntity shipperEntity) {
            if (IdConstant.MIN_ID > shipperEntity.getShipperId()) {
                shipperEntity.setShipperId(
                        (int) sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , ShipperEntity.ID.SHIPPER_ID.name()
                                )
                        )
                );
            }

            return repository.save(shipperEntity);
        }

        @Override
        public void deleteById(int shipperId) {
            repository.deleteById(shipperId);
        }

        @Override
        public ShipperEntity findById(int shipperId) {
            return repository.findById(shipperId).orElse(null);
        }

        @Override
        public Page<ShipperEntity> search(Map<String, ?> dto, int pageIndex, int pageSize) {
            final String name = StringUtil.trimWithNull(dto.get("name")).toLowerCase();
            return repository.search(
                    name,
                    PageRequest.of(pageIndex, pageSize)
            );
        }
    }
}
