package br.com.coolpon.coolpon.api.repository;

import br.com.coolpon.coolpon.api.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RewardRepository extends JpaRepository<Reward,Integer> {

    List<Reward> findByBusinessId(Integer businessId);

    Boolean existsByBusinessIdAndType(Integer businessId, String type);

    List<Reward> findAllByBusinessIdAndType(Integer businessId, String type);
}
