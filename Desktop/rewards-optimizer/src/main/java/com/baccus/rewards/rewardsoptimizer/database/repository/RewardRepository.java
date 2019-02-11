package com.baccus.rewards.rewardsoptimizer.database.repository;


import com.baccus.rewards.rewardsoptimizer.database.dao.RewardType;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RewardRepository extends Repository<RewardType,Long> {

    List<RewardType> findAll();

    RewardType findByRewardName(String rewardName);
}
