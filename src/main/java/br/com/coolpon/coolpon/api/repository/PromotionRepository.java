package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.Business;
import br.com.coolpon.coolpon.api.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    List<Promotion> findAllByBusinessId(Integer businessId);

}
