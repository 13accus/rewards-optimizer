package com.baccus.rewards.rewardsoptimizer.controller;


import com.baccus.rewards.rewardsoptimizer.FormResponse.CalculationFormResponse;
import com.baccus.rewards.rewardsoptimizer.FormResponse.CardFormResponseObject;
import com.baccus.rewards.rewardsoptimizer.FormResponse.CategoriesFormResponseObject;
import com.baccus.rewards.rewardsoptimizer.database.dao.Card;
import com.baccus.rewards.rewardsoptimizer.database.dao.Category;
import com.baccus.rewards.rewardsoptimizer.service.CalculateService;
import com.baccus.rewards.rewardsoptimizer.service.CardService;
import com.baccus.rewards.rewardsoptimizer.service.CategoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Log
public class RewardOptimizerController {

    @Autowired
    CardService cardService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CalculateService calculateService;



    @GetMapping("/")
    public String index(Model model){


        try {
            getAttributes(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        model.addAttribute("CalculationFormResponse", new CalculationFormResponse());

        log.info(model.toString());

        return "profile";
    }

    @PostMapping("/")
    public String indexPost(Model model, @ModelAttribute CalculationFormResponse response){

        setCardName(response);
        setCategoryName(response);

        log.info(response.toString());

        long startTime = System.currentTimeMillis();
        Map<String,Long> rewardTypeValue = calculateService.rewardAmount(response);
        log.info("Runtime of calculating reward: " + (System.currentTimeMillis() - startTime));


        log.info(rewardTypeValue.toString());

        try {
            getAttributes(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        model.addAttribute("CalculationFormResponse", new CalculationFormResponse());
        model.addAttribute("pointsObtained", rewardTypeValue);

        return "profile";
    }



    private void getAttributes(Model model) throws InterruptedException {

        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("cards", cardService.getCards());
        model.addAttribute("reward_type", cardService.getRewardtype());

    }

    private void setCategoryName(CalculationFormResponse calculationFormResponse){
        List<CategoriesFormResponseObject> categoriesFormResponseObjectList = calculationFormResponse.getMoneySpentCategories();
        List<Category> categories = categoryService.getCategories();

        for(int i = 0; i < categoriesFormResponseObjectList.size(); i++){
            categoriesFormResponseObjectList.get(i).setCategoryName(categories.get(i).getName());
        }
    }

    private void setCardName(CalculationFormResponse calculationFormResponse){
        List<CardFormResponseObject> cardFormResponseObjectList = calculationFormResponse.getCardFormResponseObjectsList();
        List<Card> cards = cardService.getCards();

        for(int i = 0; i < cardFormResponseObjectList.size(); i++){
            cardFormResponseObjectList.get(i).setCardName(cards.get(i).getName());
            cardFormResponseObjectList.get(i).setRewardTypeName(cards.get(i).getRewardType().getRewardName());
        }
    }


}
