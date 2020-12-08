package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/businesses")
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;

    @GetMapping
    public ResponseEntity getAllBusiness() {
        List<Business> allBusiness = businessRepository.findAll();

        if (!allBusiness.isEmpty()) {
            return ResponseEntity.ok(allBusiness);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid Business business) {

        if(businessRepository.existsByEmail(business.getEmail())){

            return ResponseEntity.ok(businessRepository.save(business));

        } else {
            return ResponseEntity.unprocessableEntity().build();
        }

    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity getCnpjBusiness(@PathVariable String cnpj){
        Business business = businessRepository.findByCnpj(cnpj);
        if(business != null){
            return ResponseEntity.ok(business);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity getCpfBusiness(@PathVariable String cpf){
        Business business = businessRepository.findByCpf(cpf);
        if(business != null){
            return ResponseEntity.ok(business);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Business login) {
        Business business = businessRepository.findByEmailAndPassword(login.getEmail(), login.getPassword());

        if(business != null){
            return ResponseEntity.ok(business);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{businessId}")
    public ResponseEntity putBusiness(@PathVariable Integer businessId,
                                      @RequestBody Business business) {

        if(businessRepository.existsById(businessId)) {
            business.setId(businessId);
            businessRepository.save(business);

            return ResponseEntity.ok(business);

        } else {
            businessRepository.save(business);

            return ResponseEntity.status(204).build();
        }
    }

    @DeleteMapping("/{businessId}")
    public ResponseEntity deleteBusiness(@PathVariable Integer businessId) {

        if(businessRepository.existsById(businessId)) {
            businessRepository.deleteById(businessId);

            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
