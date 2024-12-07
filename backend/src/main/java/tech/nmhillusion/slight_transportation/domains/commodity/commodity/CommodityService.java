package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import org.springframework.stereotype.Service;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-07
 */
public interface CommodityService {

    List<CommodityEntity> findAll();

    CommodityEntity sync(CommodityEntity commodityEntity);

    @Service
    class Impl implements CommodityService {
        private final CommodityRepository repository;

        public Impl(CommodityRepository repository) {
            this.repository = repository;
        }

        @Override
        public List<CommodityEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public CommodityEntity sync(CommodityEntity commodityEntity) {
            return repository.save(commodityEntity);
        }
    }

}
