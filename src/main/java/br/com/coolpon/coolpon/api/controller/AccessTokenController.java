package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.AccessToken;
import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/access-token")
public class AccessTokenController {
    @Autowired
    private AccessTokenRepository accessTokenRepository;
    private RestTemplate restTemplate;

//    @GetMapping
//    private ResponseEntity getAllAccesstoken() {
//        List<AccessToken> accessTokenList = accessTokenRepository.findAll();
//
//        if (!accessTokenList.isEmpty()) {
//            return ResponseEntity.ok(accessTokenList);
//
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity createNewAccessToken(@RequestBody User user){
//
//        if(accessTokenRepository.existsByUser(user)){
//            System.out.printf("aaaa");
//            return null;
//
//        } else {
//            System.out.printf("bbbb");
//             return null;
//        }
//
//    }
}
