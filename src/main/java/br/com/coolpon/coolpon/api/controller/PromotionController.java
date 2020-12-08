package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.Promotion;
import br.com.coolpon.coolpon.api.model.Reward;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import br.com.coolpon.coolpon.api.repository.PromotionRepository;
import br.com.coolpon.coolpon.api.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    RewardRepository rewardRepository;

    @GetMapping
    public ResponseEntity getAllPromotions(){
        List<Promotion> promotionList = promotionRepository.findAll();

        if(!promotionList.isEmpty()) {
            return ResponseEntity.ok(promotionList);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getPromotionById(@PathVariable Integer id){
        if (promotionRepository.existsById(id)) {
            return ResponseEntity.ok(promotionRepository.findById(id).get());

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/business/{businessId}/reward/{rewardId}")
    public ResponseEntity createPromotion(@PathVariable Integer businessId,
                                          @PathVariable Integer rewardId,
                                          @RequestBody Promotion promotion) {
        try {
            Business business = businessRepository.findById(businessId).get();
            Reward reward = rewardRepository.findById(rewardId).get();

            if (business != null && reward != null) {
                promotion.setBusiness(business);
                promotion.setReward(reward);

                return ResponseEntity.ok(promotionRepository.save(promotion));

            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{businessId}")
    public ResponseEntity createPromotionReward(@PathVariable Integer businessId,
                                          @RequestBody Promotion promotion) {
        System.out.println(promotion);
        try {
            Business business = businessRepository.findById(businessId).get();

            if (business != null) {
                promotion.setBusiness(business);
                return ResponseEntity.ok(promotionRepository.save(promotion));

            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{promotionId}")
    public ResponseEntity editPromotionById(@PathVariable Integer promotionId,
                                            @RequestBody Promotion promotion) {

        if (promotionRepository.existsById(promotionId)) {
            Promotion promo = promotionRepository.findById(promotionId).get();

            promotion.setBusiness(promo.getBusiness());
            promotion.setReward(promo.getReward());
            promotion.setId(promotionId);

            return ResponseEntity.ok(promotionRepository.save(promotion));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity deletePromotionById(@PathVariable Integer promotionId){

        if(promotionRepository.existsById(promotionId)) {
            promotionRepository.deleteById(promotionId);

            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("business/{businessId}")
    public ResponseEntity getAllPromotionsByBusiness(@PathVariable Integer businessId) {

        if(businessRepository.existsById(businessId)){
            List<Promotion> promotionList = promotionRepository.findAllByBusinessId(businessId);

            if(!promotionList.isEmpty()) {
                return ResponseEntity.ok(promotionList);

            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
