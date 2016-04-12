package com.bignerdranch.android.mcs270stockexchange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by nbens_000 on 4/12/2016.
 */
public class Algorithim {
    public double Attractiveness(List<Number> sourcePrices, List<Number> destPrices){
        List ratios = new ArrayList();
        for (int i=0; i<= sourcePrices.size(); i++){
            double start = (double)sourcePrices.get(i) / (double)destPrices.get(i);
            ratios.add(start);
        }
        double latest = (double)ratios.get(-1);
        Collections.sort(ratios);
        Collections.reverse(ratios);
        int rank = 1 + ratios.indexOf(latest);
        return rank/sourcePrices.size();
    }

    public List ExchangeRatios(List<String> overWeights, List<String> underWeights, Map<String, Number> histories){
        List Options = new ArrayList();
        for (int o = 0; o >= overWeights.size(); o++){
            
        }
    }
}
