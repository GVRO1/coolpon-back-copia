package br.com.coolpon.coolpon.api.model;

import br.com.coolpon.coolpon.api.model.Reward;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRODUCT")
public class RewardProduct extends Reward {
    private String codProduct;

    public String getCodProduct() {
        return codProduct;
    }

    public void setCodProduct(String codProduct) {
        this.codProduct = codProduct;
    }

    @Override
    public String toString() {
        return "RewardProduct{" +
                "codProduct='" + codProduct +
                "} " + super.toString();
    }
}
