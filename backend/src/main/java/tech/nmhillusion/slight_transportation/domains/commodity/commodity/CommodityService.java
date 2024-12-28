package tech.nmhillusion.slight_transportation.domains.commodity.commodity;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.constant.IdConstant;
import tech.nmhillusion.slight_transportation.domains.sequence.SequenceService;
import tech.nmhillusion.slight_transportation.entity.business.CommodityEntity;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-07
 */
public interface CommodityService {

    List<CommodityEntity> findAll();

    CommodityEntity sync(CommodityEntity commodityEntity);

    @TransactionalService
    class Impl implements CommodityService {
        private final CommodityRepository repository;
        private final SequenceService sequenceService;

        public Impl(CommodityRepository repository, SequenceService sequenceService) {
            this.repository = repository;
            this.sequenceService = sequenceService;
        }

        @Override
        public List<CommodityEntity> findAll() {
            return repository.findAll();
        }

        @Override
        public CommodityEntity sync(CommodityEntity commodityEntity) {
            if (null == commodityEntity.getCreateTime()) {
                commodityEntity.setCreateTime(
                        ZonedDateTime.now()
                );
            }

            if (IdConstant.MIN_ID > commodityEntity.getComId()) {
                commodityEntity.setComId(
                        sequenceService.nextValue(
                                sequenceService.generateSeqNameForClass(
                                        getClass()
                                        , CommodityEntity.ID.COM_ID.name()
                                )
                        )
                );
            }

            return repository.save(commodityEntity);
        }
    }

}
