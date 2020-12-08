package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.dto.input.HistoricDate;
import br.com.coolpon.coolpon.api.dto.input.UserFilter;
import br.com.coolpon.coolpon.api.model.*;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import br.com.coolpon.coolpon.api.repository.HistoricRepository;
import br.com.coolpon.coolpon.api.repository.PromotionHasUserRepository;
import br.com.coolpon.coolpon.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/historical")
public class HistoricController {

    @Autowired
    HistoricRepository historicRepository;
    @Autowired
    PromotionHasUserRepository promotionHasUserRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BusinessRepository businessRepository;

    @GetMapping
    public ResponseEntity getAllHistoric() {

        List<Historic> historicList = historicRepository.findAll();

        if(!historicList.isEmpty()) {
            return ok(historicList);

        } else {
            return notFound().build();
        }
    }

    @PostMapping("/promotion-has-user/{promotionHasUserId}")
    public ResponseEntity postHistoric(@PathVariable Integer promotionHasUserId,
                                       @RequestBody Historic historic) {
        PromotionHasUser promotionHasUser;

        if(promotionHasUserRepository.existsById(promotionHasUserId)) {
            promotionHasUser = promotionHasUserRepository.findById(promotionHasUserId).get();

            historic.setPromotionHasUser(promotionHasUser);
            historic.setPurchaseDate(LocalDateTime.now());

            promotionHasUser.setActive(false);
            promotionHasUserRepository.save(promotionHasUser);

            return ok(historicRepository.save(historic));

        } else {
            return badRequest().build();
        }
    }

    @DeleteMapping("/promotion-has-user/{promotionHasUserId}")
    public ResponseEntity deleteHistoric(@PathVariable Integer promotionHasUserId) {

        if(historicRepository.existsById(promotionHasUserId)) {
            historicRepository.deleteById(promotionHasUserId);

            return noContent().build();

        } else {
            return badRequest().build();
        }
    }

    @GetMapping("/{historicalId}")
    public ResponseEntity getHistoricById(@PathVariable Integer historicalId) {
        if (historicRepository.existsById(historicalId)) {
            return ok(historicRepository.findById(historicalId).get());

        } else {
            return notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getHistoricalByUser(@PathVariable Integer userId) {
        List<Historic> historicList;

        if(userRepository.existsById(userId)) {
            historicList = historicRepository.findAllByPromotionHasUserUserId(userId);

            if(!historicList.isEmpty()) {
                return ok(historicList);

            } else {
                return notFound().build();
            }

        } else {
            return badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity getHistoricByBusiness(@PathVariable Integer businessId) {
        List<Historic> historicList;

        if(businessRepository.existsById(businessId)) {
            historicList = historicRepository.findByPromotionHasUserPromotionBusinessId(businessId);

            if(!historicList.isEmpty()) {
                return ok(historicList);

            } else {
                return notFound().build();
            }

        } else {
            return badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}/user/{userId}")
    public ResponseEntity getHistoricByBusinessIdAndUserId(@PathVariable Integer businessId,
                                                           @PathVariable Integer userId) {
        List<Historic> historicList;
        if(businessRepository.existsById(businessId) && userRepository.existsById(userId)) {

            historicList = historicRepository
                    .findByPromotionHasUserPromotionBusinessIdAndPromotionHasUserUserId(businessId, userId);
            if(historicList.isEmpty()) {
                return notFound().build();
            }
            return ok(historicList);
        }

        return badRequest().build();
    }

    @PostMapping("/business/{businessId}/between-dates")
    public ResponseEntity getHistoricByBusinessIdAndBetweenDates(@PathVariable Integer businessId,
                                                                @RequestBody HistoricDate historicDate) {
        List<Historic> historicList;
        if(businessRepository.existsById(businessId)){
            historicList = historicRepository
                    .findByPromotionHasUserPromotionBusinessIdAndPurchaseDateIsBetween(
                            businessId,
                            historicDate.getInitialDate(),
                            historicDate.getFinishDate());

            if(historicList.isEmpty()) {
                return notFound().build();
            }

            return ok(historicList);
        }
        return badRequest().build();
    }

    @PostMapping("/business/{businessId}/user-info")
    public ResponseEntity getHistoricByBusinessIdAndUserInfo(@PathVariable Integer businessId,
                                                             @RequestBody UserFilter userFilter) {
        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);

            Promotion promotion = new Promotion();
            promotion.setBusiness(business);

            User user = new User();
            user.setFullName(userFilter.getFullName());
            user.setPhone(userFilter.getPhone());

            PromotionHasUser promotionHasUser = new PromotionHasUser();
            promotionHasUser.setPromotion(promotion);
            promotionHasUser.setUser(user);

            Historic historic = new Historic();
            historic.setPromotionHasUser(promotionHasUser);

            List<Historic> historicList = historicRepository.findAll(Example.of(historic));


            if(historicList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            if(userFilter.getLastVisit()) {
                return ResponseEntity.ok(historicList.get(historicList.size() - 1));
            }

            return ResponseEntity.ok(historicList);
        }

        return ResponseEntity.badRequest().build();
    }
}
