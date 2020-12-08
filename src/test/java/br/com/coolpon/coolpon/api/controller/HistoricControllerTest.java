package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Historic;
import br.com.coolpon.coolpon.api.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HistoricControllerTest {

    @Autowired
    HistoricController historicController;

    @MockBean
    HistoricRepository historicRepository;

    @MockBean
    PromotionRepository promotionRepository;

    @MockBean
    PromotionHasUserRepository promotionHasUserRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BusinessRepository businessRepository;

    @Test
    void getAllHistoricComHistorico() {
        List<Historic> historics = new ArrayList<>();
        historics.add(new Historic());
        when(historicRepository.findAll()).thenReturn(historics);
        ResponseEntity responseEntity = historicController.getAllHistoric();
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(historics,responseEntity.getBody());
    }

    @Test
    void getAllHistoricSemHistorico() {
        List<Historic> historics = new ArrayList<>();
        when(historicRepository.findAll()).thenReturn(historics);
        ResponseEntity responseEntity = historicController.getAllHistoric();
        assertEquals(404,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void postHistoricComPromotion() {
        when(promotionHasUserRepository.existsById(1)).thenReturn(true);
        when(promotionRepository.save(any())).thenReturn(true);
        Historic historic = new Historic();
        ResponseEntity responseEntity = historicController.postHistoric(1,historic);
        assertEquals(201,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void postHistoricSemPromotion() {
        when(promotionRepository.existsById(1)).thenReturn(false);
        Historic historic = new Historic();
        ResponseEntity responseEntity = historicController.postHistoric(1,historic);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void deleteHistoricComHistoric() {
        when(historicRepository.existsById(1)).thenReturn(true);
        Historic historic = new Historic();
        when(historicRepository.findById(1)).thenReturn(Optional.of(historic));
        ResponseEntity responseEntity = historicController.getHistoricById(1);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(historic,responseEntity.getBody());
    }

    @Test
    void deleteHistoricSemHistoric() {
        when(historicRepository.existsById(1)).thenReturn(false);
        Historic historic = new Historic();
        when(historicRepository.findById(1)).thenReturn(Optional.of(historic));
        ResponseEntity responseEntity = historicController.deleteHistoric(1);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void getHistoricByByUserComUsuarioEComHistorico() {
        when(userRepository.existsById(1)).thenReturn(true);

        List<Historic> historicList = new ArrayList<>();
        Historic historic = new Historic();
        historicList.add(historic);
        when(historicRepository.findAllByPromotionHasUserUserId(1)).thenReturn(historicList);

        ResponseEntity responseEntity = historicController.getHistoricalByUser(1);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(historicList,responseEntity.getBody());
    }

    @Test
    void getHistoricByByUserComUsuarioESemHistorico() {
        when(userRepository.existsById(1)).thenReturn(true);

        List<Historic> historicList = new ArrayList<>();
        when(historicRepository.findAllByPromotionHasUserUserId(1)).thenReturn(historicList);

        ResponseEntity responseEntity = historicController.getHistoricalByUser(1);
        assertEquals(404,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void getHistoricByByUserSemUsuarioESemHistorico() {
        when(historicRepository.existsById(1)).thenReturn(false);

        List<Historic> historicList = new ArrayList<>();
        when(historicRepository.findAllByPromotionHasUserUserId(1)).thenReturn(historicList);

        ResponseEntity responseEntity = historicController.getHistoricalByUser(1);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void getHistoricalByIdComHistorico() {
        when(historicRepository.existsById(1)).thenReturn(true);
        Historic historic = new Historic();
        when(historicRepository.findById(1)).thenReturn(Optional.of(historic));
        ResponseEntity responseEntity = historicController.getHistoricById(1);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(historic,responseEntity.getBody());
    }

    @Test
    void getHistoricalByIdSemHistorico() {
        when(historicRepository.existsById(1)).thenReturn(false);
        ResponseEntity responseEntity = historicController.getHistoricById(1);
        assertEquals(404,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void getHistoricByBusinessComEmpresaEComHistorico() {
        when(businessRepository.existsById(1)).thenReturn(true);
        List<Historic> historicList = new ArrayList<>();
        Historic historic = new Historic();
        historicList.add(historic);
        when(historicRepository.findByPromotionHasUserPromotionBusinessId(1)).thenReturn(historicList);

        ResponseEntity responseEntity = historicController.getHistoricByBusiness(1);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertEquals(historicList,responseEntity.getBody());
    }

    @Test
    void getHistoricByBusinessComEmpresaESemHistorico() {
        when(businessRepository.existsById(1)).thenReturn(true);
        List<Historic> historicList = new ArrayList<>();
        when(historicRepository.findByPromotionHasUserPromotionBusinessId(1)).thenReturn(historicList);

        ResponseEntity responseEntity = historicController.getHistoricByBusiness(1);
        assertEquals(404,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }

    @Test
    void getHistoricByBusinessSemHistorico() {
        when(businessRepository.existsById(1)).thenReturn(false);
        ResponseEntity responseEntity = historicController.getHistoricByBusiness(1);
        assertEquals(400,responseEntity.getStatusCodeValue());
        assertEquals(null,responseEntity.getBody());
    }
}