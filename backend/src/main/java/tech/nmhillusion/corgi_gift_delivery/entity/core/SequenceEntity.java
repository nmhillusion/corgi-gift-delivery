package tech.nmhillusion.corgi_gift_delivery.entity.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: minguy1
 * <p>
 * created date: 2024-12-28
 */
@Entity
@Table(name = "t_cx_sequence")
public class SequenceEntity extends Stringeable {
    @Id
    @Column(name = "seq_name")
    private String seqName;

    @Column(name = "seq_value")
    private long seqValue;

    public String getSeqName() {
        return seqName;
    }

    public SequenceEntity setSeqName(String seqName) {
        this.seqName = seqName;
        return this;
    }

    public long getSeqValue() {
        return seqValue;
    }

    public SequenceEntity setSeqValue(long seqValue) {
        this.seqValue = seqValue;
        return this;
    }
}
