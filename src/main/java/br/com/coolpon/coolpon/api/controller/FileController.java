package br.com.coolpon.coolpon.api.controller;

import br.com.coolpon.coolpon.api.components.FilaObj;
import br.com.coolpon.coolpon.api.model.*;
import br.com.coolpon.coolpon.api.repository.HistoricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@RestController
@RequestMapping
public class FileController {
    public FilaObj<Historic> fileItem;

    @Autowired
    HistoricRepository historicRepository;

    public FilaObj<Historic> read(String arquivo) {

        FileReader arq = null;
        Scanner entrada = null;
        Integer contador = 0;
        List<String> linhas;

        try {
            arq = new FileReader(arquivo);
            entrada = new Scanner(arq);
            linhas = Files.readAllLines(new File(arquivo).toPath());
            fileItem = new FilaObj<>(linhas.size());
            String linha = "";
            String tipoRegistro = "";
            String dataReferencia = "";
            String dataHoraGerar = "";
            String versaoLayout = "";


            System.out.printf("\n" + tipoRegistro, dataReferencia, dataHoraGerar, versaoLayout);

            while ((linha = entrada.nextLine()) != null) {
                if (contador == 0) {

                } else if (linhas.size() - 1 == contador) {
                    break;
                }
                else{
                    Integer id = Integer.parseInt(linha.substring(2,4).trim());
                    String nome = linha.substring(4,24).trim();
                    String email = linha.substring(24,50).trim();
                    String cpfCnpj = linha.substring(50,68).trim();
                    String description = linha.substring(68,146).trim();
                    String type = linha.substring(146,155).trim();
                    String product = linha.substring(155,165).trim();
                    String clientName = linha.substring(165,185).trim();
                    String phone = linha.substring(185, 194).trim();
                    System.out.printf("Id: %d Nome: %s Email: %s Cnpj/Cpf:" +
                            " %s Descrição: %s Tipo: %s Produto: %s Nome: %s" +
                            " Phone %s\n",id,nome,email,cpfCnpj,description,type,product,clientName,phone);
                    //User
                    User user = new User();
                    user.setFullName(clientName);
                    user.setPhone(phone);

                    //Business
                    Business business = new Business();
                    business.setEmail(email);
                    business.setName(nome);
                   // business.setId(id);
                    if (cpfCnpj.length() < 12){
                        business.setCpf(cpfCnpj);
                    }else{
                        business.setCnpj(cpfCnpj);
                    }

                    //Reward
                    Reward reward;
                    if (type.equals("Desconto")){
                        reward = new RewardDiscount();
                        reward.setType(type);
                    }else{
                        reward = new RewardProduct();
                        reward.setType(type);
                    }

                    //Promotion
                    Promotion promotion = new Promotion();
                    promotion.setDescription(description);
                    promotion.setReward(reward);
                    promotion.setBusiness(business);

                    //PromotionHasUser
                    PromotionHasUser promotionHasUser = new PromotionHasUser();
                    promotionHasUser.setUser(user);
                    promotionHasUser.setPromotion(promotion);

                    //Historic
                    Historic historic = new Historic();
                    historic.setPurchaseDate(LocalDateTime.now());
                    historic.setPromotionHasUser(promotionHasUser);
                    historic.setAmountSpent(Double.parseDouble(product));

                    fileItem.insert(historic);

                }
                contador++;
            }
        } catch (FileNotFoundException erro) {
            System.err.println("Arquivo não encontrado");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchElementException erro) {
            System.err.println("Arquivo com problema.");
        } catch (IllegalStateException erro) {
            System.err.println("Erro na leitura do arquivo.");
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.err.println("Erro ao fechar arquivo.");
            }
        }
        return fileItem;
    }

    private FilaObj<Historic> receive = new FilaObj<>(100);
    @PostMapping("/import")
    public ResponseEntity importFile(@RequestParam("arquivo") MultipartFile arquivo) throws IOException{

        if (arquivo.isEmpty()){
            return ResponseEntity.badRequest().body("Arquivo não encontrado");
        }
        System.out.println("Recebendo um arquivo do tipo: " + arquivo.getContentType());

        byte[] conteudo = arquivo.getBytes();
        Path path = Paths.get(arquivo.getOriginalFilename());
        Files.write(path,conteudo);

        try {
            receive = read(arquivo.getOriginalFilename());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/add")
    public ResponseEntity addInfo() {
        System.out.println(receive);

        if (receive.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            while (!receive.isEmpty()) {
                historicRepository.save(receive.pool());
            }
            return ResponseEntity.created(null).build();
        }
    }

}
