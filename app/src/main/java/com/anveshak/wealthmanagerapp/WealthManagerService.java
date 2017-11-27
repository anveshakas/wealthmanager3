package com.anveshak.wealthmanagerapp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tamajeet on 27-11-2017.
 */

public class WealthManagerService {
    public Map<String,Double> getDetailsTillCurrentMonth(String month,String customerId, DBHelper helper){
        Map<String,ScoreDetailsDto> scoreMap = helper.fetchScoreDetails(month,customerId);
        Map<String,Double> percentageMap = new HashMap<String,Double>();
        Double income = 0.0;
        Double expence = 0.0;
        Double emi = 0.0;
        Double investment = 0.0;
        Double savings = 0.0;
        if(scoreMap.get("Income") != null){
            income = scoreMap.get("Income").getAmount();
        }
        if(scoreMap.get("Expence") != null && income != 0.0){
            expence = scoreMap.get("Expence").getAmount();
            percentageMap.put("Expence", (expence*100)/income);
            helper.deleteScoreDetails(scoreMap.get("Expence").getCustomerId(),scoreMap.get("Expence").getMonth(),scoreMap.get("Expence").getMerchantType());
            scoreMap.get("Expence").setScore(helper.fetchScore(scoreMap.get("Expence").getMerchantType(),(expence*100)/income));
            helper.insertIntoScoreTable(scoreMap.get("Expence"));
        }
        if(scoreMap.get("EMI") != null && income != 0.0){
            emi = scoreMap.get("EMI").getAmount();
            percentageMap.put("EMI", (emi*100)/income);
            helper.deleteScoreDetails(scoreMap.get("EMI").getCustomerId(),scoreMap.get("EMI").getMonth(),scoreMap.get("EMI").getMerchantType());
            scoreMap.get("EMI").setScore(helper.fetchScore(scoreMap.get("EMI").getMerchantType(),(emi*100)/income));
            helper.insertIntoScoreTable(scoreMap.get("EMI"));
        }
        if(scoreMap.get("Investment") != null && income != 0.0){
            investment = scoreMap.get("Investment").getAmount();
            percentageMap.put("Investment", (investment*100)/income);
            helper.deleteScoreDetails(scoreMap.get("Investment").getCustomerId(),scoreMap.get("Investment").getMonth(),scoreMap.get("Investment").getMerchantType());
            scoreMap.get("Investment").setScore(helper.fetchScore(scoreMap.get("Investment").getMerchantType(),(investment*100)/income));
            helper.insertIntoScoreTable(scoreMap.get("Investment"));
        }
        savings = income - (expence + emi + investment);
        if(savings >= 0.0 && income != 0.0) {
            percentageMap.put("Savings", (savings * 100) / income);
            if(scoreMap.get("Income") != null) {
                ScoreDetailsDto scoreDetailsDto = new ScoreDetailsDto();
                scoreDetailsDto.setCustomerId(customerId);
                scoreDetailsDto.setMonth(month);
                scoreDetailsDto.setMerchantType("Savings");
                helper.deleteScoreDetails(scoreDetailsDto.getCustomerId(), scoreDetailsDto.getMonth(), scoreDetailsDto.getMerchantType());
                scoreDetailsDto.setScore(helper.fetchScore(scoreDetailsDto.getMerchantType(),(savings*100)/income));
                helper.insertIntoScoreTable(scoreDetailsDto);
            }
        }
        return percentageMap;
    }
    public Map<String,Double> fetchFutureDetails(final String customerId, final DBHelper helper){
        Map<String,Double> meanScoreMap = helper.fetchFutureMeanScores(customerId);
        Map<String,Double> meanPercentageMap = new HashMap<String,Double>();
        Double income = 0.0;
        Double expence = 0.0;
        Double emi = 0.0;
        Double investment = 0.0;
        Double savings = 0.0;
        if(meanScoreMap.get("Income") != null){
            income = meanScoreMap.get("Income");
        }
        if(meanScoreMap.get("Expense") != null && income != 0.0){
            expence = meanScoreMap.get("Expense");
            meanPercentageMap.put("Expense",(expence*100)/income);
        }
        if(meanScoreMap.get("Investment") != null && income != 0.0){
            investment = meanScoreMap.get("Investment");
            meanPercentageMap.put("Investment",(investment*100)/income);
        }
        if(meanScoreMap.get("EMI") != null && income != 0.0){
            emi = meanScoreMap.get("EMI");
            meanPercentageMap.put("EMI",(emi*100)/income);
        }
        savings = income - (expence + emi + investment);
        if(savings >= 0.0 && income != 0.0) {
            meanPercentageMap.put("Savings", (savings * 100) / income);
        }
        return meanPercentageMap;
    }
}
