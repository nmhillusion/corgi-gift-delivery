package tech.nmhillusion.slight_transportation.domains.commodity.commodityType;

import org.springframework.stereotype.Service;
import tech.nmhillusion.n2mix.util.StringUtil;
import tech.nmhillusion.n2mix.validator.StringValidator;
import tech.nmhillusion.slight_transportation.entity.business.CommodityTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-23
 */
public interface CommodityTypeService {
    List<CommodityTypeEntity> findAll();

    CommodityTypeEntity sync(Map<String, ?> dto);

    @Service
    public static class Impl implements CommodityTypeService {
        private final CommodityTypeRepository repository;

        public Impl(CommodityTypeRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<CommodityTypeEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public CommodityTypeEntity sync(Map<String, ?> dto) {
            final String currentTypeId = StringUtil.trimWithNull(dto.get("currentTypeId"));
            final String typeName = StringUtil.trimWithNull(dto.get("typeName"));

            final CommodityTypeEntity entity = new CommodityTypeEntity()
                    .setTypeName(typeName);

            if (!StringValidator.isBlank(currentTypeId)) {
                /// Mark: For update
                entity.setTypeId(Integer.parseInt(currentTypeId));
            }

            return repository.save(entity);
        }
    }
}
