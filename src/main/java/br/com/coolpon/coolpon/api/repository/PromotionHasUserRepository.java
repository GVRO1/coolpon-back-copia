package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.PromotionHasUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionHasUserRepository extends JpaRepository<PromotionHasUser, Integer> {

    List<PromotionHasUser> findByPromotionId(Integer promotionId);

    List<PromotionHasUser> findByUserId(Integer userId);

    Boolean existsByPromotionIdAndUserId(Integer promotionId, Integer userId);

    PromotionHasUser findByPromotionIdAndUserId(Integer promotionId, Integer userId);

    List<PromotionHasUser> findByPromotionBusinessId(Integer businessId);

    List<PromotionHasUser> findByUserIdAndActive(Integer userId, Boolean active);

    List<PromotionHasUser> findByPromotionBusinessIdAndUserIdAndActive(Integer businessId,
                                                                       Integer userId,
                                                                       Boolean active);

    List<PromotionHasUser> findByPromotionIdAndUserIdAndActive(Integer promotionId, Integer userId, Boolean active);
}
