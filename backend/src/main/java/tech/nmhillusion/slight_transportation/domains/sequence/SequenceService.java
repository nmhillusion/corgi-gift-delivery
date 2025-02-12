package tech.nmhillusion.slight_transportation.domains.sequence;

import tech.nmhillusion.slight_transportation.annotation.TransactionalService;
import tech.nmhillusion.slight_transportation.entity.core.SequenceEntity;

import java.text.MessageFormat;

/**
 * created by: minguy1
 * <p>
 * created date: 2024-12-28
 */
public interface SequenceService {
    String generateSeqNameForClass(Class<?> classToGen, String idName);

    long currentValue(String seqName);

    long nextValue(String seqName);

    String nextValueInString(String seqName);

    void modifyCurrentValue(String seqName, long value);

    @TransactionalService
    class Impl implements SequenceService {
        private final SequenceRepository repository;

        public Impl(SequenceRepository repository) {
            this.repository = repository;
        }

        @Override
        public String generateSeqNameForClass(Class<?> classToGen, String idName) {
            return MessageFormat.format(
                    "seq__{0}__{1}"
                    , classToGen.getName()
                    , idName
            );
        }

        @Override
        public long currentValue(String seqName) {
            return repository.findById(seqName)
                    .orElseThrow()
                    .getSeqValue();
        }

        @Override
        public long nextValue(String seqName) {
            final SequenceEntity mSequence = repository.findById(seqName)
                    .orElse(new SequenceEntity()
                            .setSeqName(seqName)
                            .setSeqValue(0)
                    );

            final long currentValue = mSequence.getSeqValue();
            final long nextValue = currentValue + 1;

            modifyCurrentValue(seqName, nextValue);

            return nextValue;
        }

        @Override
        public String nextValueInString(String seqName) {
            return String.valueOf(nextValue(seqName));
        }

        @Override
        public void modifyCurrentValue(String seqName, long value) {
            final SequenceEntity mSequence = repository.findById(seqName)
                    .orElse(new SequenceEntity()
                            .setSeqName(seqName)
                    );

            mSequence.setSeqValue(value);

            repository.saveAndFlush(mSequence);
        }
    }
}
