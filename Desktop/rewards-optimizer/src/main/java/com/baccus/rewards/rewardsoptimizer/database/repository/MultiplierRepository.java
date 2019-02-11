package com.baccus.rewards.rewardsoptimizer.database.repository;

import com.baccus.rewards.rewardsoptimizer.database.dao.Card;
import com.baccus.rewards.rewardsoptimizer.database.dao.Multiplier;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MultiplierRepository extends Repository<Multiplier,Long> {

    public Multiplier save(Multiplier multiplier);

    public List<Multiplier> findAll();

    public List<Multiplier> findAllMultiplierByCard(Card card);


}
