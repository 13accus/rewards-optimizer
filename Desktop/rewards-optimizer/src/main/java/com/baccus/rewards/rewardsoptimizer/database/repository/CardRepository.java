package com.baccus.rewards.rewardsoptimizer.database.repository;

import com.baccus.rewards.rewardsoptimizer.database.dao.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CardRepository  extends Repository<Card,Long> {

    List<Card> findAll();


    Card findByName(String name);

    Card save(Card card);

    @Query("SELECT DISTINCT rewardType FROM Card")
    List<String> findDistinctRewardType();
}
