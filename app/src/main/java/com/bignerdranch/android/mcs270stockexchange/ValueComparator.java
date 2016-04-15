package com.bignerdranch.android.mcs270stockexchange;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by nbens_000 on 4/14/2016.
 */
public class ValueComparator implements Comparator{

    Map map;
    private String over;
    private String under;
    private Double score;


    public ValueComparator(String over, String under, Double score){
        this.over = over;
        this.under = under;
        this.score = score;
    }

    public Double getScore(){
        return score;
    }

    public int compare(Object KeyA, Object KeyB){

        Comparable valueA = (Comparable) map.get(KeyA);
        Comparable valueB = (Comparable) map.get(KeyB);

        return valueA.compareTo(valueB);
    }
}
