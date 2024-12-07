package tech.nmhillusion.slight_transportation.domains.shipper.shipper;

import org.springframework.stereotype.Service;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.slight_transportation.entity.business.ShipperEntity;

import java.util.Map;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-30
 */
public interface ShipperService {
    ShipperEntity findById(String id);

    ShipperEntity sync(Map<String, ?> dto);

    void deleteById(String shipperId);

    @Service
    class Impl implements ShipperService {
        private final ShipperRepository repository;

        public Impl(ShipperRepository repository) {
            this.repository = repository;
        }

        @Override
        public ShipperEntity findById(String id) {
            return repository.findById(id).orElse(null);
        }

        @Override
        public ShipperEntity sync(Map<String, ?> dto) {
            final String currentShipperId = StringUtil.trimWithNull(dto.get("currentShipperId"));
            final String shipperCode = StringUtil.trimWithNull(dto.get("shipperCode"));
            final String shipperName = StringUtil.trimWithNull(dto.get("shipperName"));
            final String shipperTypeId = StringUtil.trimWithNull(dto.get("shipperTypeId"));

            final ShipperEntity entity = new ShipperEntity()
                    .setShipperCode(shipperCode)
                    .setShipperName(shipperName)
                    .setShipperTypeId(shipperTypeId);

            if (!StringValidator.isBlank(currentShipperId)) {
                /// Mark: For update
                entity.setShipperId(currentShipperId);
            }

            return repository.save(entity);
        }

        @Override
        public void deleteById(String shipperId) {
            repository.deleteById(shipperId);
        }
    }
}
