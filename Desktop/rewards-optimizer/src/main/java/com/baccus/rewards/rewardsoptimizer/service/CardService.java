package com.baccus.rewards.rewardsoptimizer.service;

import com.baccus.rewards.rewardsoptimizer.database.dao.Card;
import com.baccus.rewards.rewardsoptimizer.database.repository.CardRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@ToString
@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Cacheable("getCards")
    public List<Card> getCards () {

        List<Card> cards = cardRepository.findAll();

        if(cards == null || cards.isEmpty())
            throw new NullPointerException("There are not cards available.");

        return cards;
    }

    @Cacheable("getRewardType")
    public List<String> getRewardtype () {
        return cardRepository.findDistinctRewardType();
    }

}
