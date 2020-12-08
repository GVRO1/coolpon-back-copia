package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.repository.BusinessRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BusinessControllerTest {

    @Autowired
    private BusinessController businessController;

    @MockBean
    private BusinessRepository businessRepository;

    @Test
    void getAllBusinessComBusiness() {
        List<Business> businessList = Mockito.mock(ArrayList.class);
        Business business = Mockito.mock(Business.class);
        businessList.add(business);
        Business business2 = Mockito.mock(Business.class);
        businessList.add(business2);
        when(businessRepository.findAll()).thenReturn(businessList);

        ResponseEntity responseEntity = businessController.getAllBusiness();

        assertEquals(businessList,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void getAllBusinessSemBusiness() {
        List<Business> businessList = new ArrayList();
        when(businessRepository.findAll()).thenReturn(businessList);

        ResponseEntity responseEntity = businessController.getAllBusiness();

        assertNull(responseEntity.getBody());
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    void registerComEmail() {
        Business business = Mockito.mock(Business.class);
        business.setEmail("email");
        when(businessRepository.existsByEmail(business.getEmail())).thenReturn(true);

        ResponseEntity responseEntity = businessController.register(business);

        assertEquals(null,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void registerSemEmail() {
        Business business = Mockito.mock(Business.class);
        when(businessRepository.existsByEmail(business.getEmail())).thenReturn(false);

        ResponseEntity responseEntity = businessController.register(business);

        assertEquals(null,responseEntity.getBody());
        assertEquals(422,responseEntity.getStatusCodeValue());
    }

    @Test
    void getCnpjBusinessComBusiness() {
        Business business = Mockito.mock(Business.class);
        when(businessRepository.findByCnpj("cnpj")).thenReturn(business);

        ResponseEntity responseEntity = businessController.getCnpjBusiness("cnpj");

        assertEquals(business,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void getCnpjBusinessSemBusiness() {
        when(businessRepository.findByCnpj("cnpj")).thenReturn(null);

        ResponseEntity responseEntity = businessController.getCnpjBusiness("cnpj");

        assertEquals(null,responseEntity.getBody());
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    void getCpfBusinessComBusiness() {
        Business business = Mockito.mock(Business.class);
        when(businessRepository.findByCpf("cpf")).thenReturn(business);

        ResponseEntity responseEntity = businessController.getCpfBusiness("cpf");

        assertEquals(business,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void getCpfBusinessSemBusiness() {
        when(businessRepository.findByCpf("cpf")).thenReturn(null);

        ResponseEntity responseEntity = businessController.getCnpjBusiness("cpf");

        assertEquals(null,responseEntity.getBody());
        assertEquals(404,responseEntity.getStatusCodeValue());
    }

    @Test
    void loginComEmpresa() {
        Business business = Mockito.mock(Business.class);
        when(businessRepository.findByEmailAndPassword(business.getEmail(), business.getPassword())).thenReturn(business);

        ResponseEntity responseEntity = businessController.login(business);

        assertEquals(business,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void loginSemEmpresa() {
        Business business = Mockito.mock(Business.class);
        when(businessRepository.findByEmailAndPassword(business.getEmail(), business.getPassword())).thenReturn(null);


        ResponseEntity responseEntity = businessController.login(business);

        assertEquals(null,responseEntity.getBody());
        assertEquals(404,responseEntity.getStatusCodeValue());
    }



    @Test
    void putBusinessComEmpresaExistente() {
        when(businessRepository.existsById(1)).thenReturn(true);
        Business business = new Business();

        ResponseEntity responseEntity = businessController.putBusiness(1,business);

        assertEquals(1,business.getId());
        assertEquals(business,responseEntity.getBody());
        assertEquals(200,responseEntity.getStatusCodeValue());
    }

    @Test
    void putBusinessSemEmpresaExistente() {
        when(businessRepository.existsById(1)).thenReturn(false);
        Business business = Mockito.mock(Business.class);

        ResponseEntity responseEntity = businessController.putBusiness(1,business);

        assertEquals(null,responseEntity.getBody());
        assertEquals(204,responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteBusinessComEmpresa() {
        when(businessRepository.existsById(1)).thenReturn(true);

        ResponseEntity responseEntity = businessController.deleteBusiness(1);

        assertEquals(null,responseEntity.getBody());
        assertEquals(204,responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteBusinessSemEmpresa() {
        when(businessRepository.existsById(1)).thenReturn(false);

        ResponseEntity responseEntity = businessController.deleteBusiness(1);

        assertEquals(null,responseEntity.getBody());
        assertEquals(400,responseEntity.getStatusCodeValue());
    }
}