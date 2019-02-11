package com.baccus.rewards.rewardsoptimizer.FormResponse;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CalculationFormResponse {


    @Autowired
    List<CategoriesFormResponseObject> moneySpentCategories;

    @Autowired
    List<CardFormResponseObject> cardFormResponseObjectsList;

}
