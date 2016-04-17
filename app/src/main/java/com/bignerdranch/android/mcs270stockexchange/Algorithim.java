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
    ArrayList<ValueComparator> Options = new ArrayList();

    Map histories = new HashMap<ArrayList<String>, ArrayList<Double>>();


    public Algorithim(ArrayList<String> overWeights, ArrayList<String> underWeights, Map<String, ArrayList<Double>> histories){
        this.underWeights = underWeights;
        this.overWeights = overWeights;
        this.histories = histories;

        //System.out.println("Butts");
        //System.out.println(Options);

        for (int o = 0; o < overWeights.size(); o++){
            for (int u = 0; u < underWeights.size(); u++){
                Options.add(new ValueComparator(overWeights.get(o), underWeights.get(u),
                        Attractiveness(histories.get(overWeights.get(o)), histories.get(underWeights.get(u)))));
                //System.out.println(Options);
            }
        }
        Collections.sort(Options, new ScoreCompare());
        Collections.reverse(Options);

        //System.out.println(Options.get(0));

    }
    public double Attractiveness(ArrayList<Double> sourcePrices, ArrayList<Double> destPrices){
        ArrayList ratios = new ArrayList();
        for (int i=0; i< sourcePrices.size(); i++){
            double start = (double)sourcePrices.get(i) / (double)destPrices.get(i);
            ratios.add(start);
        }
        double latest = (double)ratios.get(0);
        Collections.sort(ratios);
        Collections.reverse(ratios);
        int rank = 1 + ratios.indexOf(latest);

        System.out.println(rank+"/"+sourcePrices.size());

        return rank/sourcePrices.size();
    }

   /* public List ExchangeRatios(List<String> overWeights, List<String> underWeights, Map<String, List<Number>> histories){
        ArrayList<ValueComparator> Options = new ArrayList();
        for (int o = 0; o >= overWeights.size(); o++){
            for (int u = 0; u >= underWeights.size(); u++){
                Options.add(new ValueComparator(overWeights.get(o), underWeights.get(u),
                        Attractiveness(histories.get(overWeights.get(o)), histories.get(underWeights.get(u)))));
            }
        }
        Collections.sort(Options, new ScoreCompare());
        Collections.reverse(Options);
        
        System.out.println(Options);
        return Options;
    }
    */

}