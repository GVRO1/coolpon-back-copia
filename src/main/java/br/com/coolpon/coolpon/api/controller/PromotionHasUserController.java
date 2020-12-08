package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.dto.input.UserFilter;
import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.Promotion;
import br.com.coolpon.coolpon.api.model.PromotionHasUser;
import java.util.List;

import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import br.com.coolpon.coolpon.api.repository.PromotionHasUserRepository;
import br.com.coolpon.coolpon.api.repository.PromotionRepository;
import br.com.coolpon.coolpon.api.repository.UserRepository;
import br.com.coolpon.coolpon.message.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/promotion-has-users")
public class PromotionHasUserController {
    @Autowired
    private PromotionHasUserRepository promotionHasUserRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private SmsService smsService;


    @GetMapping
    public ResponseEntity getAllPromotionHasUser(){
        List<PromotionHasUser> promotionHasUserList = promotionHasUserRepository.findAll();

        if(!promotionHasUserList.isEmpty()){
            return ok(promotionHasUserList);

        } else {
            return notFound().build();
        }
    }

    @PostMapping("/promotion/{promotionId}/user/{userId}")
    public ResponseEntity createNewPromotionHasUser(@PathVariable Integer promotionId,
                                                    @PathVariable Integer userId){
        Promotion promotion;
        User user;

        if(promotionRepository.existsById(promotionId) && userRepository.existsById(userId)) {
            promotion = promotionRepository.findById(promotionId).get();
            user = userRepository.findById(userId).get();

            if (!promotionHasUserRepository.existsByPromotionIdAndUserId(promotionId, userId)) {
                PromotionHasUser promotionHasUser = new PromotionHasUser();
                promotionHasUser.setPromotion(promotion);
                promotionHasUser.setUser(user);
                promotionHasUser.setActive(true);

//                String message = String.format("Bom dia %s, você acabou de receber " +
//                        "uma nova promoção do estabelecimento: %s. \n" +
//                        "Descrição da promoção: %s",
//                        user.getFullName(),
//                        promotionHasUser.getPromotion().getBusiness().getName(),
//                        promotionHasUser.getPromotion().getDescription());
//
//                smsService.sendSms(user.getPhone(), message);

                return ok(promotionHasUserRepository.save(promotionHasUser));

            } else {
                return notFound().build();
            }
        } else {
            return badRequest().build();
        }
    }

    @DeleteMapping("/promotion/{promotionId}/user/{userId}")
    public ResponseEntity deletePromotionHasUserByPromotionAndUser(@PathVariable Integer promotionId,
                                                                   @PathVariable Integer userId) {

        if(promotionRepository.existsById(promotionId) && userRepository.existsById(userId)) {

            if (promotionHasUserRepository.existsByPromotionIdAndUserId(promotionId, userId)) {
                PromotionHasUser promotionHasUser = promotionHasUserRepository
                        .findByPromotionIdAndUserId(promotionId, userId);

                promotionHasUserRepository.delete(promotionHasUser);

                return noContent().build();

            } else {
                return notFound().build();
            }
        } else {
            return badRequest().build();
        }
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity getAllPromotionHasUserByBusiness(@PathVariable Integer businessId) {
        List<PromotionHasUser> promotionHasUsers = promotionHasUserRepository.findByPromotionBusinessId(businessId);

        if(!promotionHasUsers.isEmpty()) {

            return ok(promotionHasUsers);

        } else {
            return notFound().build();
        }
    }

    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity getAllPromotionHasUserByPromotionId(@PathVariable Integer promotionId) {

        if(promotionRepository.existsById(promotionId)) {

            List<PromotionHasUser> promotionHasUsersList = promotionHasUserRepository.findByPromotionId(promotionId);

            if(!promotionHasUsersList.isEmpty()) {

                return ok(promotionHasUsersList);

            } else {
                return notFound().build();
            }

        } else {
            return badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getAllPromotionHasUserByUserId(@PathVariable Integer userId) {

        if(userRepository.existsById(userId)) {

            List<PromotionHasUser> promotionHasUsersList = promotionHasUserRepository.findByUserId(userId);

            if(!promotionHasUsersList.isEmpty()) {

                return ok(promotionHasUsersList);

            } else {
                return notFound().build();
            }

        } else {
            return badRequest().build();
        }
    }

    @GetMapping("user/{userId}/active")
    public ResponseEntity getAllActivePromotionsByUserId(@PathVariable Integer userId) {

        if(userRepository.existsById(userId)) {
            List<PromotionHasUser> promotionHasUserList = promotionHasUserRepository
                    .findByUserIdAndActive(userId, true);

            if(promotionHasUserList.isEmpty()) {
                return notFound().build();
            }

            return ok(promotionHasUserList);
        }

        return badRequest().build();
    }

    @GetMapping("/business/{businessId}/user/{userId}/active")
    public ResponseEntity getAllActivePromotionsByUserIdAndBusinessId(@PathVariable Integer businessId,
                                                                      @PathVariable Integer userId) {

        if(userRepository.existsById(userId) && businessRepository.existsById(businessId)) {
            List<PromotionHasUser> promotionHasUserList = promotionHasUserRepository
                    .findByPromotionBusinessIdAndUserIdAndActive(businessId, userId, true);

            if(promotionHasUserList.isEmpty()) {
                return notFound().build();
            }

            return ok(promotionHasUserList);
        }

        return badRequest().build();
    }

    @PostMapping("/business/{businessId}/user/active-promotions")
    public ResponseEntity getUserWithHisPromotionsByUserInfoAndBusinessId(@PathVariable Integer businessId,
                                                                          @RequestBody UserFilter userFilter){

        if(businessRepository.existsById(businessId)) {
            Business business = new Business();
            business.setId(businessId);

            Promotion promotion = new Promotion();
            promotion.setBusiness(business);

            User user = new User();
            user.setFullName(userFilter.getFullName());
            user.setPhone(userFilter.getPhone());
            user.setBirthDate(user.getBirthDate());

            PromotionHasUser promotionHasUser = new PromotionHasUser();
            promotionHasUser.setPromotion(promotion);
            promotionHasUser.setUser(user);
            promotionHasUser.setActive(true);


            List<PromotionHasUser> promotionHasUserList = promotionHasUserRepository.findAll(Example.of(promotionHasUser));

            if(promotionHasUserList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(promotionHasUserList);
        }

        return ResponseEntity.badRequest().build();
    }
}
