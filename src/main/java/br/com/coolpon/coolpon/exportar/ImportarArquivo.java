package br.com.coolpon.coolpon.exportar;

import br.com.coolpon.coolpon.api.model.Promotion;
import br.com.coolpon.coolpon.api.model.PromotionHasUser;
import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.model.RewardDiscount;
import br.com.coolpon.coolpon.api.model.RewardProduct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportarArquivo {
    public void executar(File file) throws IOException {
        List<PromotionHasUser> promotionHasUsers = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while (br.ready()) {
            String linha = br.readLine();
            if (linha.indexOf("00")==0){
                System.out.println(linha.substring(2,10));
                System.out.println(linha.substring(10,17));
                System.out.println(linha.substring(17,37));
                System.out.println(linha.substring(37,39));
            }

            if (linha.indexOf("02")==0) {
                PromotionHasUser promotionHasUser = new PromotionHasUser();
                User user = new User();
                Promotion promotion = new Promotion();
                user.setId(Integer.valueOf(linha.substring(2, 4)));
                user.setCpf(linha.substring(4, 19));
                user.setPhone(linha.substring(19, 34));
                user.setFullName(linha.substring(34, 74));
                user.setId(Integer.valueOf(linha.substring(74, 76)));
                promotion.setActive(Boolean.valueOf(linha.substring(76, 77)));
                promotion.setExpirationDate(LocalDateTime.of(Integer.parseInt(linha.substring(83, 87)), Integer.parseInt(linha.substring(80, 82)), Integer.parseInt(linha.substring(77, 79)), 0, 0));
                promotion.setStartDate(LocalDateTime.of(Integer.parseInt(linha.substring(92, 97)), Integer.parseInt(linha.substring(90, 92)), Integer.parseInt(linha.substring(87, 89)), 0, 0));
                String typeReward = linha.substring(92, 97);
                if (typeReward.trim().equals("Desconto")) {
                    RewardDiscount reward = new RewardDiscount();
                    promotion.setReward(reward);
                } else {
                    RewardProduct reward = new RewardProduct();
                    promotion.setReward(reward);
                }
                promotionHasUser.setUser(user);
                promotionHasUser.setPromotion(promotion);
                promotionHasUsers.add(promotionHasUser);
            }
        }
        System.out.println(promotionHasUsers);
        br.close();
        fr.close();
    }
}
