package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.PromotionHasUser;
import br.com.coolpon.coolpon.api.repository.PromotionHasUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DownloadControllerTest {

    @Autowired
    DownloadController downloadController;

    @MockBean
    PromotionHasUserRepository promotionHasUserRepository;

    @Test
    void getText() throws NoSuchMethodException {
        List<PromotionHasUser> businesses = Mockito.mock(ArrayList.class);
        Mockito.when(promotionHasUserRepository.findByPromotionBusinessId(1)).thenReturn(businesses);
        HttpHeaders headers =  new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=promotion_has_user.txt");
        headers.add("Accept-Language","pt-BR");

        Class controller = DownloadController.class;
        Annotation responseBody = controller.getDeclaredMethod("getText").getDeclaredAnnotation(ResponseBody.class);
        ResponseEntity responseEntity = downloadController.getText(1);
        assertEquals(200,responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getHeaders().containsKey("Content-Disposition"));
        assertTrue(responseEntity.getHeaders().containsKey("Accept-Language"));
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseBody);
    }

}