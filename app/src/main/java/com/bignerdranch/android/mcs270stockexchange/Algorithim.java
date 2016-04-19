package com.bignerdranch.android.mcs270stockexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by nbens_000 on 4/12/2016.
 */
public class Algorithim {

    private ArrayList<String> overWeights;
    private ArrayList<String> underWeights;
    private ArrayList<Double> exchangeRatio = new ArrayList<Double>();

    private ArrayList<ExchangeRatios> options = new ArrayList<ExchangeRatios>();

    private String over;
    private String under;

    public Algorithim(ArrayList<String> overWeights, ArrayList<String> underWeights, Map<String, ArrayList<Double>> histories){
        this.underWeights = underWeights;
        this.overWeights = overWeights;
        for (int o = 0; o < overWeights.size(); o++){
            for (int u = 0; u < underWeights.size(); u++){
                //If wanting to get ArrayList Exchange Ratio for two particular stocks. Put a counter here. :D

                over = overWeights.get(o);
                under = underWeights.get(u);

                options.add(new ExchangeRatios(overWeights.get(o), underWeights.get(u),
                        Attractiveness(histories.get(overWeights.get(o)), histories.get(underWeights.get(u)))));
                //System.out.println(Options.get(0));
            }
        }
        Collections.sort(options, new ScoreCompare());
        Collections.reverse(options);

        //System.out.println(exchangeRatio);

    }
    public double Attractiveness(ArrayList<Double> sourcePrices, ArrayList<Double> destPrices){
        ArrayList<Double> ratios = new ArrayList<Double>();
        for (int i=0; i< sourcePrices.size(); i++){
            double start = (double)sourcePrices.get(i) / (double)destPrices.get(i);
            ratios.add(start);
        }
        double latest = (double)ratios.get(0);
        Collections.sort(ratios);
        Collections.reverse(ratios);
        int rank = 1 + ratios.indexOf(latest);


        System.out.println(over);
        System.out.println(under);

        System.out.println(rank+ "/"+sourcePrices.size());

        System.out.println();

        double temp = (double) rank /sourcePrices.size();

        exchangeRatio.add(temp);
        //System.out.println(exchangeRatio);

        return temp;
    }


}