package tech.nmhillusion.corgi_gift_delivery.service_impl.core;

import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.constant.IdConstant;
import tech.nmhillusion.corgi_gift_delivery.entity.core.SequenceEntity;
import tech.nmhillusion.corgi_gift_delivery.repository.core.SequenceRepository;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;

import java.util.NoSuchElementException;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
@TransactionalService
public class SequenceServiceImpl implements SequenceService {
    private final SequenceRepository sequenceRepository;

    public SequenceServiceImpl(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public <T> Long getCurrentValue(Class<T> clazz) {
        final String sequenceKey = clazz.getName();

        return sequenceRepository.findById(sequenceKey)
                .map(SequenceEntity::getSeqValue)
                .orElseThrow();
    }

    @Override
    public <T> Long getNextValue(Class<T> clazz) {
        final SequenceEntity sequenceEntity = new SequenceEntity()
                .setSeqName(clazz.getName());
        try {
            sequenceEntity
                    .setSeqValue(
                            getCurrentValue(clazz) + 1
                    );
        } catch (NoSuchElementException ex) {
            sequenceEntity
                    .setSeqValue(IdConstant.MIN_ID);

        }
        return sequenceRepository.save(sequenceEntity).getSeqValue();
    }
}
