package com.ccit.entity;

import javax.persistence.*;

@Entity
@Table(name = "adn_customer_domain", schema = "matrix", catalog = "")
public class AdnCustomerDomainEntity {

    private long id;

    private Long customerId;

    private String secondLevelDomain;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "second_level_domain")
    public String getSecondLevelDomain() {
        return secondLevelDomain;
    }

    public void setSecondLevelDomain(String secondLevelDomain) {
        this.secondLevelDomain = secondLevelDomain;
    }

}
