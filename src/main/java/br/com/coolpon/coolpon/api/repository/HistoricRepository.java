package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.Historic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoricRepository extends JpaRepository<Historic, Integer> {

    List<Historic> findAllByPromotionHasUserUserId(Integer userId);

    List<Historic> findByPromotionHasUserPromotionBusinessId(Integer businessId);

    List<Historic> findByPromotionHasUserPromotionBusinessIdAndPromotionHasUserUserId(Integer businesId, Integer userId);

    List<Historic> findByPromotionHasUserPromotionBusinessIdAndPurchaseDateIsBetween(Integer businessId,
                                                                                     LocalDateTime initialDate,
                                                                                     LocalDateTime finalDate);

}
