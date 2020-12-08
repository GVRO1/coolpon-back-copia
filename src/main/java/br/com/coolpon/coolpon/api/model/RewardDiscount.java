package br.com.coolpon.coolpon.api.model;

import br.com.coolpon.coolpon.api.model.Reward;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DISCOUNT")
public class RewardDiscount extends Reward {
    private Double discount;


    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "RewardDiscont{" +
                "discount=" + discount +
                "} " + super.toString();
    }
}
