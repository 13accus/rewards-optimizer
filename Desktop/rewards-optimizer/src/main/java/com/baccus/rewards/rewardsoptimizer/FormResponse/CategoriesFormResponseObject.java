package com.baccus.rewards.rewardsoptimizer.FormResponse;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CategoriesFormResponseObject {

    String categoryName;
    long amountSpent;

}
