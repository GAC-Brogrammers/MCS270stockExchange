package com.bignerdranch.android.mcs270stockexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by nbens_000 on 4/12/2016.
 */
public class Algorithim {

    public Algorithim(List<String> overWeights, ){

    }

    public double Attractiveness(List<Number> overPrices, List<Number> underPrices){
        List ratios = new ArrayList();
        for (int i=0; i<= overPrices.size(); i++){
            double start = (double)overPrices.get(i) / (double)underPrices.get(i);
            ratios.add(start);
        }
        double latest = (double)ratios.get(-1);
        Collections.sort(ratios);
        Collections.reverse(ratios);
        int rank = 1 + ratios.indexOf(latest);
        return rank/overPrices.size();
    }

    public List ExchangeRatios(List<String> overWeights, List<String> underWeights, Map<String, List<Number>> histories){
        ArrayList<ValueComparator> Options = new ArrayList();
        for (int o = 0; o >= overWeights.size(); o++){
            for (int u = 0; u >= underWeights.size(); u++){
                Options.add(new ValueComparator(overWeights.get(o), underWeights.get(u),
                        Attractiveness(histories.get(overWeights.get(o)), histories.get(underWeights.get(u)))));
            }
        }
        Collections.sort(Options, new ScoreCompare());
        Collections.reverse(Options);
        return Options;
    }

}
