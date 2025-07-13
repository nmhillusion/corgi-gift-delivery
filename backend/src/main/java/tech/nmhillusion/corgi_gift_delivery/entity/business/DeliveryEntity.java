package tech.nmhillusion.corgi_gift_delivery.entity.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@Entity
@Table(name = "t_cx_delivery")
public class DeliveryEntity extends BaseBusinessEntity<Long> {
    @Id
    @Column(name = "delivery_id")
    private Long deliveryId;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "delivery_period_year")
    private Integer deliveryPeriodYear;

    @Column(name = "delivery_period_month")
    private Integer deliveryPeriodMonth;

    @Column(name = "territory")
    private String territory;

    @Column(name = "region")
    private String region;

    @Column(name = "organ_id")
    private String organId;

    @Column(name = "received_organ")
    private String receivedOrgan;

    @Column(name = "amd_name")
    private String amdName;

    @Column(name = "customer_level")
    private String customerLevel;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "id_card_number")
    private String idCardNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "gift_name")
    private String giftName;

    @Column(name = "note")
    private String note;

    @Column(name = "insert_date", insertable = false, updatable = false)
    private ZonedDateTime insertDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public DeliveryEntity setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
        return this;
    }

    public String getEventId() {
        return eventId;
    }

    public DeliveryEntity setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public Integer getDeliveryPeriodYear() {
        return deliveryPeriodYear;
    }

    public DeliveryEntity setDeliveryPeriodYear(Integer deliveryPeriodYear) {
        this.deliveryPeriodYear = deliveryPeriodYear;
        return this;
    }

    public Integer getDeliveryPeriodMonth() {
        return deliveryPeriodMonth;
    }

    public DeliveryEntity setDeliveryPeriodMonth(Integer deliveryPeriodMonth) {
        this.deliveryPeriodMonth = deliveryPeriodMonth;
        return this;
    }

    public String getTerritory() {
        return territory;
    }

    public DeliveryEntity setTerritory(String territory) {
        this.territory = territory;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public DeliveryEntity setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getOrganId() {
        return organId;
    }

    public DeliveryEntity setOrganId(String organId) {
        this.organId = organId;
        return this;
    }

    public String getReceivedOrgan() {
        return receivedOrgan;
    }

    public DeliveryEntity setReceivedOrgan(String receivedOrgan) {
        this.receivedOrgan = receivedOrgan;
        return this;
    }

    public String getAmdName() {
        return amdName;
    }

    public DeliveryEntity setAmdName(String amdName) {
        this.amdName = amdName;
        return this;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public DeliveryEntity setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public DeliveryEntity setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public DeliveryEntity setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public DeliveryEntity setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DeliveryEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getGiftName() {
        return giftName;
    }

    public DeliveryEntity setGiftName(String giftName) {
        this.giftName = giftName;
        return this;
    }

    public String getNote() {
        return note;
    }

    public DeliveryEntity setNote(String note) {
        this.note = note;
        return this;
    }

    public ZonedDateTime getInsertDate() {
        return insertDate;
    }

    public DeliveryEntity setInsertDate(ZonedDateTime insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public DeliveryEntity setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    @Override
    public Long getId() {
        return getDeliveryId();
    }

    @Override
    public BaseBusinessEntity<Long> setId(Long id) {
        return setDeliveryId(id);
    }
}
