package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.*;
import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-11-16
 */
@Entity
@Table(name = "t_cx_customer")
public class CustomerEntity extends Stringeable {
    @Id
    @SequenceGenerator(name = "seq__cx_customer__customer_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq__cx_customer__customer_id")
    @Column(name = "customer_id", nullable = false)
    private long customerId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "id_card_number", nullable = false, unique = true)
    private String idCardNumber;

    public long getCustomerId() {
        return customerId;
    }

    public CustomerEntity setCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public CustomerEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public CustomerEntity setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }
}
