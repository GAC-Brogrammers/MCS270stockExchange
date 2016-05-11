package com.bignerdranch.android.mcs270stockexchange;

/**
 * Created by nbens_000 on 5/9/2016.
 */
public class ExchangeRatios {
    private String over;
    private String under;
    private Double score;


    public ExchangeRatios(String over, String under, Double score){
        this.over = over;
        this.under = under;
        this.score = score;
    }

    public Double getScore(){
        //System.out.println(score);
        return score;
    }
}
