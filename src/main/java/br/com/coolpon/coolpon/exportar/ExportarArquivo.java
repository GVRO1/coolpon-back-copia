package br.com.coolpon.coolpon.exportar;

import br.com.coolpon.coolpon.api.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;

public class ExportarArquivo {
    public String executar(List<Historic> list, LocalDate localDate) {
        FileWriter arq = null;
        Formatter saida = null;
        String conteudo = "";
        boolean deuRuim = false;
        String nomeArquivo = "aluno.txt";

        try {
            arq = new FileWriter(nomeArquivo, true);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.err.println("Erro ao abrir arquivo");
            System.exit(1);
        }
        LocalDateTime now = LocalDateTime.now();
        String dataHeader = String.format("%d-%d-%d %d-%d-%d",now.getDayOfMonth(),now.getMonth().getValue(),now.getYear(),now.getHour(),now.getMinute(),now.getSecond());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        String data = String.format("%d%d", localDate.getYear(), localDate.getMonthValue());
        conteudo = String.format("%s%-8s%-6s%-19s%-2d%n", "00", "HISTÓRICO PROMOÇÃO", data, now.format(dateTimeFormatter), 1);
        saida.format("%s%-8s%-6s%-19s%-2d%n", "00", "HISTÓRICO PROMOÇÃO", data, now.format(dateTimeFormatter), 1);
        int registros = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                Historic historic = list.get(i);
                Business business = historic.getPromotionHasUser().getPromotion().getBusiness();
                Promotion promotion = historic.getPromotionHasUser().getPromotion();
                Reward reward = promotion.getReward();
                User user = historic.getPromotionHasUser().getUser();
                String documento = business.getCpf() == null ? business.getCnpj():business.getCpf();
                String tipoReward = reward instanceof RewardProduct? "Produto": "Desconto";
                String valorReward = reward instanceof RewardProduct? ((RewardProduct) reward).getCodProduct():String.valueOf(((RewardDiscount) reward).getDiscount());
                conteudo += String.format("%s%-2s%-20s%-30s%-14s%-25s%-9s%-10s%-20s%-11s%n", "02",business.getId(),business.getName(),business.getEmail(),documento,promotion.getDescription(),tipoReward,valorReward,user.getFullName(),user.getPhone());
                registros++;
            }
            conteudo += String.format("%s%d%n", "03", registros);
            saida.format("%s%d%n", "03", registros);

        } catch (FormatterClosedException erro) {
            System.err.println("Erro ao gravar no arquivo");
            deuRuim = true;
        } finally { // bloco finally é executado independente de dar erro ou não
            // usado para fechar os objetos saida e close.
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.err.println("Erro ao fechar arquivo.");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
        return conteudo;
    }
}
