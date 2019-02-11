package com.baccus.rewards.rewardsoptimizer.FormResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CardFormResponseObject {


    private String cardName;
    private boolean cardInWallet;
    private long bonus;
    private String rewardTypeName;

}
