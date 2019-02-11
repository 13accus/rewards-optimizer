package com.baccus.rewards.rewardsoptimizer.service;

import com.baccus.rewards.rewardsoptimizer.FormResponse.CalculationFormResponse;
import com.baccus.rewards.rewardsoptimizer.FormResponse.CardFormResponseObject;
import com.baccus.rewards.rewardsoptimizer.FormResponse.CategoriesFormResponseObject;
import com.baccus.rewards.rewardsoptimizer.database.dao.Multiplier;
import com.baccus.rewards.rewardsoptimizer.database.repository.CardRepository;
import com.baccus.rewards.rewardsoptimizer.database.repository.MultiplierRepository;
import com.baccus.rewards.rewardsoptimizer.database.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.ManyToOne;
import java.util.*;

@Component
public class CalculateService {

    @Autowired
    MultiplierRepository multiplierRepository;



    public Map<String,Long> rewardAmount(CalculationFormResponse calculationFormResponse){

        List<CategoriesFormResponseObject> categoriesFormResponseObjectList = calculationFormResponse.getMoneySpentCategories();
        List<CardFormResponseObject> cardFormResponseObjectList = calculationFormResponse.getCardFormResponseObjectsList();


        List<Multiplier> filteredByCategories = filterByCategories(cardFormResponseObjectList);

        if(filteredByCategories.isEmpty()) return new HashMap<>();

        List<Multiplier> foundMaxMultipliers = findMaxMultipliers(filteredByCategories);
        Map<String,Multiplier> totalByRewardType = totalByRewardType(foundMaxMultipliers);

        Map<String,Long> mapOfCalculatedValues = mapOfCalculatedValues(totalByRewardType,categoriesFormResponseObjectList);
        mapOfCalculatedValues = addBonus(mapOfCalculatedValues,cardFormResponseObjectList);

        return mapOfCalculatedValues;
    }


    private List<Multiplier> filterByCategories(List<CardFormResponseObject> cardFormResponseObjectList){
        List<Multiplier> ret = new ArrayList<>();

        for(CardFormResponseObject card : cardFormResponseObjectList){

            if(card.isCardInWallet()) {
                for (Multiplier multiplier : multiplierRepository.findAll()) {
                    if (card.getCardName().equals(multiplier.getCard().getName()))
                        ret.add(multiplier);
                }
            }
        }

        return ret;
    }

    private List<Multiplier> findMaxMultipliers(List<Multiplier> filteredByCategories){
        List<Multiplier> ret = new ArrayList<>();



        for(Multiplier multiplier : filteredByCategories){
            Multiplier multiplierToBeUpdated = null;
            for(Multiplier multiplierFromReturnValue : ret){

                String categoryNameFromMultiplier = multiplier.getCategory().getName();
                String categoryNameFromMultiplierFromReturnValue = multiplierFromReturnValue.getCategory().getName();

                double categoryValueFromMultiplier = multiplier.getMulti() * multiplier.getCard().getRewardType().getRewardValue();
                double categoryValueFromMultiplierFromReturnValue = multiplierFromReturnValue.getMulti() * multiplierFromReturnValue.getCard().getRewardType().getRewardValue();

                if(categoryNameFromMultiplier.equals(categoryNameFromMultiplierFromReturnValue) &&
                      categoryValueFromMultiplier > categoryValueFromMultiplierFromReturnValue) {
                    multiplierToBeUpdated = multiplierFromReturnValue;
                }
            }


            ret.remove(multiplierToBeUpdated);
            ret.add(multiplier);
            multiplierToBeUpdated = null;
        }

        return ret;
    }

    private Map<String,Multiplier> totalByRewardType(List<Multiplier> foundMaxMultipliers){
        Map<String,Multiplier> ret = new HashMap<>();

        for(Multiplier multiplier : foundMaxMultipliers){
            ret.put(multiplier.getCategory().getName(), multiplier);
        }

        return ret;
    }

    private Map<String,Long>  mapOfCalculatedValues(Map<String,Multiplier> totalByRewardType,
                                                   List<CategoriesFormResponseObject> categoriesFormResponseObjectList){

        Map<String,Long> mapOfCalculatedTotal = new HashMap<>();

        for(CategoriesFormResponseObject categoriesFormResponseObject : categoriesFormResponseObjectList){

            if(totalByRewardType.containsKey(categoriesFormResponseObject.getCategoryName())){
                Multiplier multiplier = totalByRewardType.get(categoriesFormResponseObject.getCategoryName());

                if(mapOfCalculatedTotal.containsKey(multiplier.getCard().getRewardType().getRewardName())){
                    mapOfCalculatedTotal.put(multiplier.getCard().getRewardType().getRewardName(),
                            mapOfCalculatedTotal.get(multiplier.getCard().getRewardType().getRewardName())
                            + ((long) (categoriesFormResponseObject.getAmountSpent() * multiplier.getMulti())));
                } else {
                    mapOfCalculatedTotal.put(multiplier.getCard().getRewardType().getRewardName(), (long)
                            (categoriesFormResponseObject.getAmountSpent() * multiplier.getMulti()));
                }


            }else{
                Multiplier multiplier = totalByRewardType.get("Other");

                Long currentValue = mapOfCalculatedTotal.get(multiplier.getCard().getRewardType().getRewardName());

                if(currentValue == null) currentValue = 0L;

                mapOfCalculatedTotal.put(multiplier.getCard().getRewardType().getRewardName(),
                        currentValue
                        + (long) (categoriesFormResponseObject.getAmountSpent() * totalByRewardType.get("Other").getMulti())
                );
            }

        }


        return mapOfCalculatedTotal;
    }

    private Map<String,Long> addBonus(Map<String,Long> mapOfCalculatedValues, List<CardFormResponseObject> cardFormResponseObjectList){
        for(CardFormResponseObject cardFormResponseObject : cardFormResponseObjectList){
            Long bonus = cardFormResponseObject.getBonus();

            if(bonus != null && mapOfCalculatedValues.containsKey(cardFormResponseObject.getRewardTypeName()))
            mapOfCalculatedValues.put(cardFormResponseObject.getRewardTypeName(),
                    mapOfCalculatedValues.get(cardFormResponseObject.getRewardTypeName()) + cardFormResponseObject.getBonus());

        }

        return mapOfCalculatedValues;
    }
}
