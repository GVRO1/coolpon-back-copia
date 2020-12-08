package br.com.coolpon.coolpon.api.model;

import javax.persistence.*;
import br.com.coolpon.coolpon.api.model.Promotion;

@Entity
public class PromotionHasUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;

    @ManyToOne(cascade = CascadeType.ALL)
    private Promotion promotion;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}