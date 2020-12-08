package br.com.coolpon.coolpon.api.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorColumn(name = "TYPE")
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RewardDiscount.class, name = "DISCOUNT"),
        @JsonSubTypes.Type(value = RewardProduct.class, name = "PRODUCT"),
})
public abstract class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Business business;
    @Column(insertable = false, updatable = false)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", business=" + business +
                ", type='" + type + '\'' +
                '}';
    }
}
