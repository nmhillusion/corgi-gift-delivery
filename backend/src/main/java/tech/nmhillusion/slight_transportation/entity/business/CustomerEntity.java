package tech.nmhillusion.slight_transportation.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "id_card_number", nullable = false, unique = true)
    private String idCardNumber;

    public String getCustomerId() {
        return customerId;
    }

    public CustomerEntity setCustomerId(String customerId) {
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
