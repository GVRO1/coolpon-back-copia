package br.com.coolpon.coolpon.api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.coolpon.coolpon.api.model.*;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import br.com.coolpon.coolpon.api.repository.RewardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
public class RewardController {
    @Autowired
    RewardRepository rewardRepository;
    @Autowired
    BusinessRepository businessRepository;

    @GetMapping
    public ResponseEntity getAllRewards(){
        List<Reward> rewardList = rewardRepository.findAll();

        if(!rewardList.isEmpty()) {
            return ResponseEntity.ok(rewardList);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getRewardById(@PathVariable Integer id) {
        if (rewardRepository.existsById(id)) {
            return ResponseEntity.ok(rewardRepository.findById(id).get());

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity getAllPromotionsByBusiness(@PathVariable Integer businessId) {

        if(businessRepository.existsById(businessId)) {

            return ResponseEntity.ok(rewardRepository.findByBusinessId(businessId));

        } else {

            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/business/{businessId}/product")
    public ResponseEntity postNewRewardProduct(@PathVariable Integer businessId,
                                               @RequestBody RewardProduct product) {

        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);

            product.setBusiness(business);

            return ResponseEntity.ok(rewardRepository.save(product));

        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/business/{businessId}/discount")
    public ResponseEntity postNewRewardDiscount(@PathVariable Integer businessId,
                                                @RequestBody RewardDiscount discount) {
        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);

            discount.setBusiness(business);

            return ResponseEntity.ok(rewardRepository.save(discount));

        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/business/{businessId}/product/{id}")
    public ResponseEntity editRewardProduct(@PathVariable Integer businessId,
                                            @PathVariable Integer id,
                                            @RequestBody RewardProduct product) {
        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);
            product.setBusiness(business);

            if (rewardRepository.existsById(id)) {
                product.setId(id);

                return ResponseEntity.ok(rewardRepository.save(product));

            } else {
                rewardRepository.save(product);

                return ResponseEntity.status(204).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/business/{businessId}/discount/{id}")
    public ResponseEntity editRewardDiscount(@PathVariable Integer businessId,
                                                @PathVariable Integer id,
                                                @RequestBody RewardDiscount discount) {

        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);
            discount.setBusiness(business);

            if (rewardRepository.existsById(id)) {
                discount.setId(id);

                return ResponseEntity.ok(rewardRepository.save(discount));

            } else {
                rewardRepository.save(discount);

                return ResponseEntity.status(204).build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/business/{businessId}/reward/{id}")
    public ResponseEntity deleteRewardProduct(@PathVariable Integer businessId,
                                              @PathVariable Integer id) {

        if(businessRepository.existsById(businessId)) {
            if (rewardRepository.existsById(id)) {

                rewardRepository.deleteById(id);
                return ResponseEntity.noContent().build();

            } else {
                return ResponseEntity.notFound().build();
            }


        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}/product")
    public ResponseEntity getAllRewardsProductsByBusinessId(@PathVariable Integer businessId) {
        if(businessRepository.existsById(businessId)) {
            if(rewardRepository.existsByBusinessIdAndType(businessId, RewardType.PRODUCT.name())) {
                List<Reward> productList = rewardRepository
                                                .findAllByBusinessIdAndType(businessId,
                                                                            RewardType.PRODUCT.name());
                return ResponseEntity.ok(productList);

            } else{
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}/discount")
    public ResponseEntity getAllRewardsDiscountByBusinessId(@PathVariable Integer businessId) {
        if(businessRepository.existsById(businessId)) {
            if(rewardRepository.existsByBusinessIdAndType(businessId, RewardType.DISCOUNT.name())) {
                List<Reward> productList = rewardRepository
                        .findAllByBusinessIdAndType(businessId,
                                RewardType.DISCOUNT.name());
                return ResponseEntity.ok(productList);

            } else{
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
