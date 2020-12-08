package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Cnpj;
import br.com.coolpon.coolpon.api.repository.CnpjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cnpj")
public class CnpjController {

    @Autowired
    CnpjRepository cnpjRepository;


    @GetMapping("/{cnpj}")
    public ResponseEntity getCnpj(@PathVariable String cnpj){
        Cnpj cnpjObj = cnpjRepository.getCnpj(cnpj);
        if (cnpjObj.getStatus().equals("OK")){
        return ResponseEntity.ok(cnpjObj);
        }else{
            return ResponseEntity.badRequest().body(cnpjObj.getStatus() + cnpjObj.getError());
        }
    }
}
