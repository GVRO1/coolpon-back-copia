package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.AccessToken;
import br.com.coolpon.coolpon.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

    Boolean existsByUser(User user);
}
