package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByPhone(String phone);

    Boolean existsByPhone(String phone);

    User findByCpf(String cpf);

    Boolean existsByCpf(String cpf);

    List<User> findByFullNameLike(String fullName);

    List<User> findByBirthDate(LocalDateTime birthDate);

}
