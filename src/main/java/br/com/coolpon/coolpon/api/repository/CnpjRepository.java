package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.Cnpj;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Configuration
@FeignClient(value = "cnpj",url = "https://www.receitaws.com.br/v1/cnpj/")
public interface CnpjRepository {

    @GetMapping("/{cnpj}")
    Cnpj getCnpj(@PathVariable String cnpj);

}
