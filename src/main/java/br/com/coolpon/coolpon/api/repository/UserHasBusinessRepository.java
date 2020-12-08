package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.User;
import br.com.coolpon.coolpon.api.model.UserHasBusiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHasBusinessRepository extends JpaRepository<UserHasBusiness, Integer> {

    List<UserHasBusiness> findAllByBusinessId(Integer businessId);

    List<UserHasBusiness> findAllByUserId(Integer userId);

    Boolean existsByBusinessIdAndUserId(Integer businessId, Integer userId);

    UserHasBusiness findByBusinessIdAndUserId(Integer businessId, Integer userId);

//    List<UserHasBusiness> findByBusinessIdAndUserPhone(Integer businessId, String phone);
//
//    List<UserHasBusiness> findByBusinessIdAndUserFullName(Integer businessId, String phone);
}
