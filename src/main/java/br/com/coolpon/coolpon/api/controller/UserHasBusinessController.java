package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.dto.input.UserFilter;
import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.model.UserHasBusiness;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import br.com.coolpon.coolpon.api.repository.UserHasBusinessRepository;
import br.com.coolpon.coolpon.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-has-business")
public class UserHasBusinessController {
    @Autowired
    UserHasBusinessRepository userHasBusinessRepository;
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAllUserHasBusiness() {
        return ResponseEntity.ok(userHasBusinessRepository.findAll());
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity getAllUsersHasBusinessByBusiness(@PathVariable Integer businessId) {
        List<UserHasBusiness> userHasBusinessList;

        if(businessRepository.existsById(businessId)) {
            userHasBusinessList = userHasBusinessRepository.findAllByBusinessId(businessId);

            if(!userHasBusinessList.isEmpty()) {

                return ResponseEntity.ok(userHasBusinessList);

            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{usersId}")
    public ResponseEntity getAllUsersHasBusinessByUser(@PathVariable Integer usersId) {
        List<UserHasBusiness> userHasBusinessList;

        if(userRepository.existsById(usersId)) {
            userHasBusinessList = userHasBusinessRepository.findAllByUserId(usersId);

            if(!userHasBusinessList.isEmpty()) {

                return ResponseEntity.ok(userHasBusinessList);

            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/business/{businessId}/users/{usersId}")
    public ResponseEntity postUserHasBusiness(@PathVariable Integer businessId,
                                              @PathVariable Integer usersId,
                                              @RequestBody UserHasBusiness userHasBusiness) {
        Business business = new Business();
        User user = new User();

        if(businessRepository.existsById(businessId)
            && userRepository.existsById(usersId)) {

            if(!userHasBusinessRepository.existsByBusinessIdAndUserId(businessId, usersId)){
                business.setId(businessId);
                user.setId(usersId);
                userHasBusiness.setBusiness(business);
                userHasBusiness.setUser(user);

            } else {
                Integer score = userHasBusiness.getScore();
                userHasBusiness = userHasBusinessRepository.findByBusinessIdAndUserId(businessId, usersId);
                userHasBusiness.setScore(userHasBusiness.getScore() + score);

            }
            return ResponseEntity.ok(userHasBusinessRepository.save(userHasBusiness));

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/business/{businessId}/users/{usersId}")
    public ResponseEntity editUserHasBusiness(@PathVariable Integer businessId,
                                              @PathVariable Integer usersId,
                                              @RequestBody UserHasBusiness userHasBusiness) {
        Business business = new Business();
        User user = new User();

        if(businessRepository.existsById(businessId)
                && userRepository.existsById(usersId)) {

            if(userHasBusinessRepository.existsByBusinessIdAndUserId(businessId, usersId)){
                Integer score = userHasBusiness.getScore();
                userHasBusiness = userHasBusinessRepository.findByBusinessIdAndUserId(businessId, usersId);
                userHasBusiness.setScore(userHasBusiness.getScore() + score);

            } else {
                business.setId(businessId);
                user.setId(usersId);
                userHasBusiness.setBusiness(business);
                userHasBusiness.setUser(user);

            }
            return ResponseEntity.ok(userHasBusinessRepository.save(userHasBusiness));

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/business/{businessId}/users/{usersId}")
    public ResponseEntity deleteUserHasBusiness(@PathVariable Integer businessId,
                                              @PathVariable Integer usersId) {

        if(businessRepository.existsById(businessId)
                && userRepository.existsById(usersId)) {
            if(userHasBusinessRepository.existsByBusinessIdAndUserId(businessId, usersId)){


                UserHasBusiness userHasBusinesses = userHasBusinessRepository
                                                        .findByBusinessIdAndUserId(businessId, usersId);

                userHasBusinessRepository.delete(userHasBusinesses);

                return ResponseEntity.noContent().build();

            } else {

                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/business/{businessId}/user-info")
    public ResponseEntity getUserHasBusinessByBusinessIdAndUserInfo(@PathVariable Integer businessId,
                                                                    @RequestBody UserFilter userFilter){

        if(businessRepository.existsById(businessId)) {

            Business business = new Business();
            business.setId(businessId);

            User user = new User();
            user.setFullName(userFilter.getFullName());
            user.setPhone(userFilter.getPhone());
            user.setBirthDate(userFilter.getBirthDate());

            UserHasBusiness userHasBusiness = new UserHasBusiness();
            userHasBusiness.setBusiness(business);
            userHasBusiness.setUser(user);


            List<UserHasBusiness> userHasBusinessList = userHasBusinessRepository.findAll(Example.of(userHasBusiness));

            if(userHasBusinessList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(userHasBusinessList);
        }

        return ResponseEntity.badRequest().build();
    }
}
