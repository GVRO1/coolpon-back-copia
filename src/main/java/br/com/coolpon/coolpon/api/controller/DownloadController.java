package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.components.FilaObj;
import br.com.coolpon.coolpon.api.model.*;
import br.com.coolpon.coolpon.api.repository.HistoricRepository;
import br.com.coolpon.coolpon.api.repository.PromotionHasUserRepository;
import br.com.coolpon.coolpon.exportar.ExportarArquivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.coolpon.coolpon.api.components.PilhaObj;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    HistoricRepository historicRepository;


    @GetMapping(value = "exportar/{businessId}", produces = {"application/text"})
    @ResponseBody
    public ResponseEntity getText(@PathVariable Integer businessId){
        HttpHeaders headers =  new HttpHeaders();
        headers.add("Content-Disposition","attachment; filename=promotion_has_user.txt");
        headers.add("Accept-Language","pt-BR");
        ExportarArquivo exportarArquivo = new ExportarArquivo();
        return new ResponseEntity(exportarArquivo.executar(historicRepository.findByPromotionHasUserPromotionBusinessId(businessId), LocalDate.now()),headers, HttpStatus.OK);
    }

    @GetMapping(value = "/historical/Recent/business/{businessId}", produces = {"application/text"})
    @ResponseBody
    public ResponseEntity getRecentHistoryByBusinessId(@PathVariable Integer businessId){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=historical.txt");

        List<Historic> historicList = historicRepository.findByPromotionHasUserPromotionBusinessId(businessId);

        PilhaObj<Historic> historicPilhaObj = new PilhaObj<>(historicList);

        Business business = new Business();
        Integer size;

        if(!historicList.isEmpty()) {

            //String nomeArq = "historical.txt";
            // String geral que irá gerar o documento
            String file = "";
            String headerBusiness = "";
            String bodyBusiness = "";
            String headerHistoric = "";
            String bodyHistoric = "";
            String trailer = "";
            Integer totalRegisters = 0;

            // Metodo para capturar as informações do Estabelecimento
            business = historicPilhaObj.peek().getPromotionHasUser()
                                            .getPromotion()
                                            .getBusiness();

            // Criação do header
            headerBusiness += String.format("%4s |", "ID");
            headerBusiness += String.format("%-20s |", "NOME");
            headerBusiness += String.format("%-30s |", "EMAIL");
            // Lógica para saber se o estabelecimento possui um CNPJ ou CPF
            if(!business.getCnpj().equals(null)) {
                headerBusiness += String.format("%-41s %n", "CNPJ");

            } else {
                headerBusiness += String.format("%-14s %n", "CPF");
            }


            // Criação do body
            bodyBusiness += String.format("%4d |", business.getId());
            bodyBusiness += String.format("%-20s |", business.getName());
            bodyBusiness += String.format("%-30s |", business.getEmail());
            // Lógica para saber se o estabelecimento possui um CNPJ ou CPF
            if(!business.getCnpj().equals(null)) {
                bodyBusiness += String.format("%-41s%n", business.getCnpj());

            } else {
                bodyBusiness += String.format("%-14s%n", business.getCpf());
            }

            file += headerBusiness;
            file += bodyBusiness;
            // for para inserir a linha de separação das informações
            for(int i = 0; i < 150; i++){
                file += "-";
            }
            file += "\n";

            // Criação do header de historico de vendas
            headerHistoric += String.format("%-80s |", "DESCRIPTION");
            headerHistoric += String.format("%-9s |", "TYPE");
            headerHistoric += String.format("%-20s |", "DISCOUNT/CODPRODUCT");
            headerHistoric += String.format("%-20s |", "CLIENT NAME");
            headerHistoric += String.format("%-11s%n", "PHONE");

            while (!historicPilhaObj.isEmpty()){
                // Pega todas as informações do historico e insere em uma variavel
                Historic historic = historicPilhaObj.pop();
                // Pega todas as informações do PromotionHasUser e insere em uma variavel
                PromotionHasUser promotionHasUser = historic.getPromotionHasUser();
                // Pega todas as informações do Promotion e insere em uma variavel
                Promotion promotion = promotionHasUser.getPromotion();
                // Pega todas as informações do User e insere em uma variavel
                User user = promotionHasUser.getUser();
                // Pega todas as informações do Reward e insere em uma variavel
                Reward reward = promotion.getReward();

                // Criação body de historic
                bodyHistoric += String.format("%-80s |", promotion.getDescription());
                bodyHistoric += String.format("%-9s |", reward.getType());
                // Logica para saber se o tipo de recompensa
                if (reward.getType().equals("DISCOUNT")) {
                    // Faz o casting para transformar reward em reward discount
                    RewardDiscount rewardDiscount = (RewardDiscount) reward;
                    bodyHistoric += String.format("%-20s |", rewardDiscount.getDiscount());

                } else {
                    // Faz o casting para transformar reward em reward product
                    RewardProduct rewardProduct = (RewardProduct) reward;
                    bodyHistoric += String.format("%-20s |", rewardProduct.getCodProduct());
                }
                bodyHistoric += String.format("%-20s |", user.getFullName());
                bodyHistoric += String.format("%-11s%n", user.getPhone());

                totalRegisters++;
            }

            file += bodyHistoric;

            // for para inserir a linha de separação das informações
            for(int i = 0; i < 150; i++){
                file += "-";
            }
            file += "\n";

            trailer += String.format("%16s: ", "QUANTIDADE TOTAL");
            trailer += String.format("%5d", totalRegisters);

            file += trailer;

            return new ResponseEntity(file, headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping(value = "/historical/Acient/business/{businessId}", produces = {"application/text"})
    @ResponseBody
    public ResponseEntity getAncientHistoryByBusinessId(@PathVariable Integer businessId){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=historical.txt");

        List<Historic> historicList = historicRepository.findByPromotionHasUserPromotionBusinessId(businessId);

        FilaObj<Historic> historicFilaObj = new FilaObj<Historic>(historicList);

        Business business = new Business();
        Integer size;

        if(!historicList.isEmpty()) {


            String file = "";
            String headerBusiness = "";
            String bodyBusiness = "";
            String headerHistoric = "";
            String bodyHistoric = "";
            String trailer = "";
            Integer totalRegisters = 0;


            business = historicFilaObj.peek().getPromotionHasUser()
                    .getPromotion()
                    .getBusiness();


            headerBusiness += String.format("%4s |", "ID");
            headerBusiness += String.format("%-20s |", "NOME");
            headerBusiness += String.format("%-30s |", "EMAIL");

            if(!business.getCnpj().equals(null)) {
                headerBusiness += String.format("%-41s %n", "CNPJ");

            } else {
                headerBusiness += String.format("%-14s %n", "CPF");
            }



            bodyBusiness += String.format("%4d |", business.getId());
            bodyBusiness += String.format("%-20s |", business.getName());
            bodyBusiness += String.format("%-30s |", business.getEmail());

            if(!business.getCnpj().equals(null)) {
                bodyBusiness += String.format("%-41s%n", business.getCnpj());

            } else {
                bodyBusiness += String.format("%-14s%n", business.getCpf());
            }

            file += headerBusiness;
            file += bodyBusiness;

            for(int i = 0; i < 150; i++){
                file += "-";
            }
            file += "\n";

            // Criação do header de historico de vendas
            headerHistoric += String.format("%-80s |", "DESCRIPTION");
            headerHistoric += String.format("%-9s |", "TYPE");
            headerHistoric += String.format("%-20s |", "DISCOUNT/CODPRODUCT");
            headerHistoric += String.format("%-20s |", "CLIENT NAME");
            headerHistoric += String.format("%-11s%n", "PHONE");

            while (!historicFilaObj.isEmpty()){
                // Pega todas as informações do historico e insere em uma variavel
                Historic historic = historicFilaObj.pool();
                // Pega todas as informações do PromotionHasUser e insere em uma variavel
                PromotionHasUser promotionHasUser = historic.getPromotionHasUser();
                // Pega todas as informações do Promotion e insere em uma variavel
                Promotion promotion = promotionHasUser.getPromotion();
                // Pega todas as informações do User e insere em uma variavel
                User user = promotionHasUser.getUser();
                // Pega todas as informações do Reward e insere em uma variavel
                Reward reward = promotion.getReward();

                // Criação body de historic
                bodyHistoric += String.format("%-80s |", promotion.getDescription());
                bodyHistoric += String.format("%-9s |", reward.getType());
                // Logica para saber se o tipo de recompensa
                if (reward.getType().equals("DISCOUNT")) {
                    // Faz o casting para transformar reward em reward discount
                    RewardDiscount rewardDiscount = (RewardDiscount) reward;
                    bodyHistoric += String.format("%-20s |", rewardDiscount.getDiscount());

                } else {
                    // Faz o casting para transformar reward em reward product
                    RewardProduct rewardProduct = (RewardProduct) reward;
                    bodyHistoric += String.format("%-20s |", rewardProduct.getCodProduct());
                }
                bodyHistoric += String.format("%-20s |", user.getFullName());
                bodyHistoric += String.format("%-11s%n", user.getPhone());

                totalRegisters++;
            }

            file += bodyHistoric;

            // for para inserir a linha de separação das informações
            for(int i = 0; i < 150; i++){
                file += "-";
            }
            file += "\n";

            trailer += String.format("%16s: ", "QUANTIDADE TOTAL");
            trailer += String.format("%5d", totalRegisters);

            file += trailer;

            return new ResponseEntity(file, headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();

    }

}
