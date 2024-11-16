package tech.nmhillusion.slight_transportation.entity.business;

import tech.nmhillusion.n2mix.type.Stringeable;

/**
 * created by: chubb
 * <p>
 * created date: 2024-11-16
 */
public class CustomerEntity extends Stringeable {
    private String customerId;
    private String fullName;
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
