package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business,Integer> {

    Business findByCnpj(String cnpj);

    Business findByCpf(String cpf);

    Business findByEmailAndPassword(String email, String password);

    Boolean existsByEmail(String email);
}

